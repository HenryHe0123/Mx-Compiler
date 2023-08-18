package FrontEnd;

import AST.*;
import AST.Def.*;
import AST.Expr.*;
import AST.Expr.primary.*;
import AST.Stmt.*;
import AST.Util.*;
import IR.*;
import IR.Entity.*;
import IR.Instruction.*;
import IR.Instruction.Expression.*;
import IR.Instruction.Terminal.*;
import IR.Type.*;
import Util.Error.CodegenError;
import Util.Scope.*;
import Util.Type;

import java.util.ArrayList;
import java.util.Stack;

public class IRBuilder implements ASTVisitor {
    private final GlobalScope gScope;
    private Scope curScope;
    private Type curType = null;
    private IRBlock curBlock = null;
    private IRFunction curFunction = null;
    private ClassType curClass = null; //not null only when visiting class
    private final Stack<IRBlock> loopContinueBlock = new Stack<>();
    private final Stack<IRBlock> loopEndBlock = new Stack<>();
    private final IRFunction globalVarInit = IRFunction.globalVarInit();
    private IRBlock lastGlobalVarInitBlock = globalVarInit.entry;
    private final IRRoot root;

    public IRBuilder(GlobalScope gScope, IRRoot root) {
        this.gScope = gScope;
        curScope = gScope;
        this.root = root;
    }

    // ------------------------------ utility ------------------------------

    private void setCurBlock(IRBlock block) {
        curBlock = block;
        if (curFunction == globalVarInit) lastGlobalVarInitBlock = block;
    }

    private void resetCurBlock() {
        curBlock = null;
    }

    private void addEmptyBlock(IRBlock emptyBlock) {
        if (curFunction == null) return;
        emptyBlock.trySetTerminator(new Jump(curFunction.returnBlock));
        curFunction.addBlock(emptyBlock);
    }

    private void tryTerminateLastGlobalVarInitBlock() {
        //debug: last globalVarInit block won't be terminated normally
        assert lastGlobalVarInitBlock != null : "last globalVarInit block shouldn't be null";
        lastGlobalVarInitBlock.trySetTerminator(new Jump(globalVarInit.returnBlock));
        globalVarInit.addBlock(lastGlobalVarInitBlock);
    }

    private boolean isGlobalFunction(String funcName) {
        return gScope.funcMembers.containsKey(funcName);
    }

    private void assign(Entity value, ExprNode destExpr) {
        curBlock.addInstruct(new Store(value, destExpr.getAssignDest()));
    }

    private boolean isReturned() { //whenever entering a function, isReturned is initially false
        return curBlock == null;
    }

    private void terminateBlock(Terminator terminator) {
        if (isReturned()) return;
        curBlock.setTerminator(terminator);
        curFunction.addBlock(curBlock);
        resetCurBlock();
    }

    private void tryTerminateBlock(Terminator terminator) { //recommend: safer version
        if (isReturned()) return;
        curBlock.trySetTerminator(terminator);
        curFunction.addBlock(curBlock);
        resetCurBlock();
    }

    private void addParameter(String name, Register reg) {
        //add parameter to function, and allocate entity to stack
        curFunction.addParameter(reg);
        Register addr = new Register(reg.name + ".addr", reg.type.asPtr());
        curBlock.addInstruct(new Alloca(addr, reg.type));
        curBlock.addInstruct(new Store(reg, addr));
        curScope.putVarEntity(name, addr);
    }

    private void addThisParameter() {
        addParameter("this", new Register("this", curClass.asPtr()));
    }

    private Entity getThisParameter() {
        Entity entity = curScope.getVarEntity("this");
        IRType baseType = entity.type.deconstruct(); //class*
        Entity base = Register.anonymous(baseType);
        curBlock.addInstruct(new Load(base, entity));
        return base;
    }

    private void collectClassTypes(RootNode node) {
        node.classDefs.forEach(classDef -> IRType.addIRClassType(classDef.identifier));
        node.classDefs.forEach(classDef -> {
            ClassType classType = IRType.getIRClassType(classDef.identifier);
            classDef.varDefs.forEach(varDef -> {
                IRType type = IRType.from(varDef.type);
                for (VarDeclareUnitNode var : varDef.varDeclareUnits) {
                    classType.addMember(var.identifier, type);
                }
            });
        });
    }

    private GlobalVar initStringLiteral(StringLiteral literal) {
        GlobalVar srcStr = GlobalVar.anonymousSrcStr();
        root.globals.add(new GlobalDef(srcStr, literal, true));
        return srcStr;
    }

    private void visitClassMemberExpr(VarExprNode member, Entity caller, ExprNode node) {
        ClassType classType = (ClassType) caller.type.deconstruct(); //caller is class*
        int index = classType.getMemberIndex(member.identifier);
        IRType memberType = classType.getMemberType(index);
        Entity destPtr = Register.anonymous(memberType.asPtr());
        curBlock.addInstruct(new GetElementPtr(destPtr, caller, Int.zero, Entity.from(index)));
        Entity entity = Register.anonymous(memberType);
        curBlock.addInstruct(new Load(entity, destPtr));
        node.entity = entity;
        if (node instanceof MemberExprNode) ((MemberExprNode) node).member.entity = entity;
        node.setAssignDest(destPtr);
    }

    private Call malloc(Entity destPtr, Entity bytes) {
        Call malloc = new Call(destPtr, "__malloc", destPtr.type);
        malloc.addArg(bytes);
        return malloc;
    }

    private Register offsetPtr(Entity ptr, Entity offset) {
        IRType ptrType = ptr.type;
        Register offsetPtr = Register.anonymous(ptrType);
        GetElementPtr getOffsetPtr = new GetElementPtr(offsetPtr, ptr, offset);
        curBlock.addInstruct(getOffsetPtr);
        return offsetPtr;
    }

    private Entity getArrayBytes(Entity size) {
        if (size instanceof Int) {
            int bytes = ((Int) size).value * 4 + 4;
            return Entity.from(bytes);
        }

        Register tmp = Register.anonymous(INType.IRInt);
        Binary multi4 = new Binary(tmp, Binary.BinaryOp.mul, INType.IRInt, size, Int.four);
        curBlock.addInstruct(multi4);

        Register bytes = Register.anonymous(INType.IRInt);
        Binary add4 = new Binary(bytes, Binary.BinaryOp.add, INType.IRInt, tmp, Int.four);
        curBlock.addInstruct(add4);
        return bytes;
    }

    private Entity createNewArray(IRType ptrType, int layer, ArrayList<ExprNode> dimSizes) {
        if (dimSizes.get(layer) == null) return Null.instance;
        dimSizes.get(layer).accept(this);
        Entity size = dimSizes.get(layer).entity; //i32
        Entity bytes = getArrayBytes(size); //i32

        //malloc
        Register mallocPtr = Register.anonymous(ptrType);
        curBlock.addInstruct(malloc(mallocPtr, bytes));

        //store size at the beginning of the array
        curBlock.addInstruct(new Store(size, mallocPtr));

        //move pointer to offset 1 (real array ptr)
        Register realArrayPtr = offsetPtr(mallocPtr, Int.one);

        //return immediately if last layer
        if (layer < dimSizes.size() - 1) {
            //create array by while loop
            String postfix = curFunction.getLabelPostfix();
            IRBlock condBlock = new IRBlock("array.while.cond" + postfix, curFunction);
            IRBlock bodyBlock = new IRBlock("array.while.body" + postfix, curFunction);
            IRBlock endBlock = new IRBlock("array.while.end" + postfix, curFunction);

            //int i = 0
            Register IPtr = new Register("_array.i." + layer + "." + postfix, INType.IRInt.asPtr());
            curBlock.addInstruct(new Alloca(IPtr, INType.IRInt));
            curBlock.addInstruct(new Store(Int.zero, IPtr));
            tryTerminateBlock(new Jump(condBlock));

            //cond: i < size
            setCurBlock(condBlock);
            Register IVal = Register.anonymous(INType.IRInt);
            curBlock.addInstruct(new Load(IVal, IPtr));
            Register cond = Register.anonymous(INType.IRBool);
            curBlock.addInstruct(new Icmp(cond, Icmp.IcmpOp.slt, IVal, size));
            tryTerminateBlock(new Branch(cond, bodyBlock, endBlock));

            //body: create next layer array
            setCurBlock(bodyBlock);
            Entity nextLayerPtr = createNewArray(ptrType.deconstruct(), layer + 1, dimSizes);
            Register curPtr = offsetPtr(realArrayPtr, IVal);
            curBlock.addInstruct(new Store(nextLayerPtr, curPtr));
            //i++
            Register IValPlus = Register.anonymous(INType.IRInt);
            curBlock.addInstruct(new Binary(IValPlus, Binary.BinaryOp.add, INType.IRInt, IVal, Int.one));
            curBlock.addInstruct(new Store(IValPlus, IPtr));
            tryTerminateBlock(new Jump(condBlock));

            setCurBlock(endBlock);
        }
        return realArrayPtr;
    }

    // ------------------------------ visit ------------------------------

    @Override
    public void visit(RootNode node) {
        root.functions.add(globalVarInit);
        collectClassTypes(node); //collect all class types first
        node.varDefs.forEach(varDef -> varDef.accept(this));
        node.classDefs.forEach(classDef -> classDef.accept(this));
        node.funcDefs.forEach(funcDef -> funcDef.accept(this));
        tryTerminateLastGlobalVarInitBlock();
    }

    // ------------------------------ definition ------------------------------

    @Override
    public void visit(VarDefNode node) {
        curType = node.type;
        node.varDeclareUnits.forEach(unit -> unit.accept(this));
        curType = null;
    }

    @Override
    public void visit(VarDeclareUnitNode node) {
        if (curScope instanceof GlobalScope) { //global
            Entity reg = new GlobalVar(node.identifier + curScope.asPostfix(), IRType.from(curType).asPtr());
            Entity init = Entity.init(curType);
            if (node.expression != null) { //if with an init expression
                curFunction = globalVarInit;
                setCurBlock(lastGlobalVarInitBlock);
                node.expression.accept(this);
                var entity = node.expression.entity;
                if (entity.isConstant()) init = entity;
                else curBlock.addInstruct(new Store(entity, reg));
                resetCurBlock(); //prevent misuse
                curFunction = null;
            }
            root.globals.add(new GlobalDef(reg, init));
            curScope.putVarEntity(node.identifier, reg);
        } else {
            if (isReturned()) return;
            IRType type = IRType.from(curType);
            //debug: register would not use ptr automatically
            Entity reg = new Register(node.identifier + curScope.asPostfix(), type.asPtr());
            curBlock.addInstruct(new Alloca(reg, type));
            Entity init = Entity.init(curType);
            if (node.expression != null) {
                node.expression.accept(this);
                init = node.expression.entity;
            }
            curBlock.addInstruct(new Store(init, reg));
            curScope.putVarEntity(node.identifier, reg);
        }
    }

    @Override
    public void visit(FuncDefNode node) {
        curScope = new Scope(curScope, node.type);
        curFunction = new IRFunction(node.identifier, IRType.from(node.type));
        setCurBlock(curFunction.entry);
        root.functions.add(curFunction);
        if (curFunction.isMain()) curBlock.addInstruct(Call.callGlobalVarInit());
        if (curClass != null) addThisParameter();
        if (node.parameter != null) node.parameter.args.forEach(arg -> arg.accept(this));
        node.stmts.forEach(stmt -> stmt.accept(this));
        curScope = curScope.getParent();
        curFunction = null;
        resetCurBlock();
    }

    @Override
    public void visit(ParameterUnitNode node) {
        String rename = node.identifier + curScope.asPostfix();
        addParameter(node.identifier, new Register(rename, IRType.from(node.type)));
    }

    @Override
    public void visit(ClassDefNode node) {
        curScope = new Scope(curScope, node);
        String className = node.identifier;
        curClass = IRType.getIRClassType(className); //class type already collected
        if (node.constructor != null) node.constructor.accept(this);
        for (FuncDefNode func : node.funcDefs) { //class method renaming
            func.identifier = curClass.asPrefix() + func.identifier;
            func.accept(this);
        }
        root.globals.add(new ClassDef(curClass));
        curClass = null;
        curScope = curScope.getParent();
    }

    @Override
    public void visit(ClassConstructorNode node) { //view like a void function
        curScope = new Scope(curScope, Type.Void());
        String rename = curClass.asPrefix() + node.identifier;
        curFunction = new IRFunction(rename, VoidType.IRVoid);
        setCurBlock(curFunction.entry);
        root.functions.add(curFunction);
        addThisParameter();
        node.stmts.forEach(stmt -> stmt.accept(this));
        resetCurBlock();
        curFunction = null;
        curScope = curScope.getParent();
    }

    // ------------------------------ statement ------------------------------

    @Override
    public void visit(ReturnStmtNode node) {
        if (isReturned()) return;
        if (node.returnExpr != null) {
            node.returnExpr.accept(this);
            Entity retVal = node.returnExpr.entity;
            curBlock.addInstruct(new Store(retVal, curFunction.returnReg));
        }
        terminateBlock(new Jump(curFunction.returnBlock));
    }

    @Override
    public void visit(BlockStmtNode node) {
        if (isReturned()) return;
        curScope = new Scope(curScope);
        node.stmts.forEach(stmt -> stmt.accept(this));
        curScope = curScope.getParent();
    }

    @Override
    public void visit(BranchStmtNode node) {
        if (isReturned()) return;
        node.condition.accept(this);
        Entity cond = node.condition.entity;
        boolean ret = false;

        String postfix = curFunction.getLabelPostfix();
        IRBlock thenBlock = new IRBlock("if.then" + postfix, curFunction);
        IRBlock endBlock = new IRBlock("if.end" + postfix, curFunction);

        if (node.elseStmt == null) {
            tryTerminateBlock(new Branch(cond, thenBlock, endBlock));

            setCurBlock(thenBlock);
            curScope = new Scope(curScope);
            if (node.ifStmt != null) node.ifStmt.accept(this); //curBlock maybe terminated already
            tryTerminateBlock(new Jump(endBlock));
        } else {
            IRBlock elseBlock = new IRBlock("if.else" + postfix, curFunction);
            tryTerminateBlock(new Branch(cond, thenBlock, elseBlock));

            setCurBlock(thenBlock);
            curScope = new Scope(curScope);
            if (node.ifStmt != null) node.ifStmt.accept(this);
            boolean ifReturned = isReturned();
            tryTerminateBlock(new Jump(endBlock));
            curScope = curScope.getParent();

            setCurBlock(elseBlock);
            curScope = new Scope(curScope);
            node.elseStmt.accept(this);
            ret = ifReturned && isReturned(); //if both branches are returned, we can return
            tryTerminateBlock(new Jump(endBlock));
        }
        curScope = curScope.getParent();
        if (ret) addEmptyBlock(endBlock);
        else setCurBlock(endBlock);
    }

    @Override
    public void visit(ExprStmtNode node) {
        if (isReturned() || node.expression == null) return;
        node.expression.accept(this);
    }

    @Override
    public void visit(ForStmtNode node) {
        if (isReturned()) return;
        curScope = new Scope(curScope);
        String postfix = curFunction.getLabelPostfix();
        IRBlock condBlock = new IRBlock("for.cond" + postfix, curFunction);
        IRBlock bodyBlock = new IRBlock("for.body" + postfix, curFunction);
        IRBlock stepBlock = new IRBlock("for.step" + postfix, curFunction);
        IRBlock endBlock = new IRBlock("for.end" + postfix, curFunction);
        loopEndBlock.add(endBlock);
        loopContinueBlock.add(stepBlock);

        if (node.init != null) node.init.accept(this); //add to curBlock
        tryTerminateBlock(new Jump(condBlock));

        setCurBlock(condBlock);
        if (node.condition != null) node.condition.accept(this);
        Entity cond = (node.condition != null) ? node.condition.entity : Entity.from(true);
        tryTerminateBlock(new Branch(cond, bodyBlock, endBlock));

        setCurBlock(bodyBlock);
        if (node.body != null) node.body.accept(this);
        tryTerminateBlock(new Jump(stepBlock));

        setCurBlock(stepBlock);
        if (node.step != null) node.step.accept(this);
        tryTerminateBlock(new Jump(condBlock));

        setCurBlock(endBlock);
        curScope = curScope.getParent();
        loopEndBlock.pop();
        loopContinueBlock.pop();
    }

    @Override
    public void visit(WhileStmtNode node) {
        if (isReturned()) return;
        curScope = new Scope(curScope);
        String postfix = curFunction.getLabelPostfix();
        IRBlock condBlock = new IRBlock("while.cond" + postfix, curFunction);
        IRBlock bodyBlock = new IRBlock("while.body" + postfix, curFunction);
        IRBlock endBlock = new IRBlock("while.end" + postfix, curFunction);
        loopEndBlock.add(endBlock);
        loopContinueBlock.add(condBlock);

        tryTerminateBlock(new Jump(condBlock));

        setCurBlock(condBlock);
        node.condition.accept(this);
        Entity cond = node.condition.entity;
        tryTerminateBlock(new Branch(cond, bodyBlock, endBlock));

        setCurBlock(bodyBlock);
        if (node.body != null) node.body.accept(this);
        tryTerminateBlock(new Jump(condBlock));

        setCurBlock(endBlock);
        curScope = curScope.getParent();

        loopEndBlock.pop();
        loopContinueBlock.pop();
    }

    @Override
    public void visit(CtrlStmtNode node) {
        if (isReturned()) return;
        IRBlock block = node.isBreak ? loopEndBlock.peek() : loopContinueBlock.peek();
        tryTerminateBlock(new Jump(block));
    }

    // ------------------------------ expression ------------------------------

    @Override
    public void visit(LiteralExprNode node) {
        if (node.entity == null) {
            Entity literal = Entity.from(node.value);
            node.entity = (literal instanceof StringLiteral) ?
                    initStringLiteral((StringLiteral) literal) : literal;
        }
    }

    @Override
    public void visit(VarExprNode node) {
        if (isReturned()) return;
        if (node.entity == null) {
            //"this" is handled just like normal variable, as we have already added it to the map
            Entity entity = curScope.getVarEntity(node.identifier);
            if (entity == null) { //entity belongs to this
                visitClassMemberExpr(node, getThisParameter(), node);
                return;
            }
            node.setAssignDest(entity);
            IRType baseType = entity.type.deconstruct();
            node.entity = Register.anonymous(baseType);
            curBlock.addInstruct(new Load(node.entity, entity));
        }
    }

    @Override
    public void visit(FuncExprNode node) {
        if (isReturned()) return;
        IRType returnType = IRType.from(node.type);
        node.entity = returnType.isVoid() ? null : Register.anonymous(returnType);
        Call call;
        if (isGlobalFunction(node.funcName)) { //global function
            call = new Call(node.entity, node.funcName, returnType);
        } else { //class method in class environment
            assert curClass != null : "class method should be in class environment";
            call = new Call(node.entity, curClass.asPrefix() + node.funcName, returnType);
            call.addArg(getThisParameter());
        }
        node.args.forEach(arg -> {
            arg.accept(this);
            call.addArg(arg.entity);
        });
        curBlock.addInstruct(call);
    }

    @Override
    public void visit(MemberExprNode node) {
        if (isReturned()) return;
        node.caller.accept(this);
        Entity caller = node.caller.entity; //class* or IRString or array
        if (node.dotFunc()) {
            FuncExprNode method = (FuncExprNode) node.member;
            IRType returnType = IRType.from(node.type);
            Entity entity = returnType.isVoid() ? null : Register.anonymous(returnType);

            if (node.caller.type.isArray()) { //array.size()
                assert method.funcName.equals("size") : "array member function name should be size";
                Entity sizePtr = offsetPtr(caller, Int.minusOne);
                curBlock.addInstruct(new Load(entity, sizePtr));
                node.entity = entity;
                node.member.entity = entity;
                return;
            }

            IRType baseType = caller.type.deconstruct(); //classType or IRStringLiteral
            Call call = new Call(entity, baseType.asPrefix() + method.funcName, returnType);
            call.addArg(caller); //add this pointer
            method.args.forEach(arg -> {
                arg.accept(this);
                call.addArg(arg.entity);
            });
            curBlock.addInstruct(call);

            node.entity = entity;
            node.member.entity = entity;
        } else { //caller dot variable, caller won't be string
            VarExprNode member = (VarExprNode) node.member;
            visitClassMemberExpr(member, caller, node);
        }
    }

    @Override
    public void visit(NewExprNode node) {
        if (isReturned()) return;
        if (node.isNewClass()) {
            IRType classPtrType = IRType.from(node.type);
            ClassType classType = (ClassType) classPtrType.deconstruct();
            Register mallocPtr = Register.anonymous(classPtrType); //class*
            curBlock.addInstruct(malloc(mallocPtr, Entity.from(classType.getBytes())));
            Call construct = new Call(null, classType.getConstructorName(), VoidType.IRVoid);
            construct.addArg(mallocPtr);
            curBlock.addInstruct(construct);
            node.entity = mallocPtr;
        } else { //new array
            node.entity = createNewArray(IRType.from(node.type), 0, node.dimExpr);
        }
    }

    @Override
    public void visit(UnaryExprNode node) {
        if (isReturned()) return;
        if (node.entity == null) {
            node.expression.accept(this);
            Entity src = node.expression.entity;
            node.entity = Register.anonymous(IRType.from(node.type)); //dest
            Instruction instruction;
            if (node.isBool()) {
                instruction = new Binary(node.entity, Binary.BinaryOp.xor, INType.IRBool, src, Entity.from(true));
            } else {
                instruction = switch (node.operator) {
                    case "++" -> new Binary(node.entity, Binary.BinaryOp.add, INType.IRInt, src, Int.one);
                    case "--" -> new Binary(node.entity, Binary.BinaryOp.sub, INType.IRInt, src, Int.one);
                    case "-" -> new Binary(node.entity, Binary.BinaryOp.sub, INType.IRInt, Int.zero, src);
                    case "~" -> new Binary(node.entity, Binary.BinaryOp.xor, INType.IRInt, src, Int.minusOne);
                    default -> throw new CodegenError("failed to build IR for unary expression");
                };
            }
            curBlock.addInstruct(instruction);
            if (node.isPrefixUpdate()) {
                assign(node.entity, node.expression);
                node.setAssignDest(node.expression.getAssignDest());
            }
        }
    }

    @Override
    public void visit(PostfixUpdateExprNode node) {
        if (isReturned()) return;
        node.expression.accept(this);
        Entity src = node.expression.entity;
        node.entity = src; //store previous i
        Entity reg = Register.anonymous(INType.IRInt); //store latest i
        var op = node.add ? Binary.BinaryOp.add : Binary.BinaryOp.sub;
        curBlock.addInstruct(new Binary(reg, op, INType.IRInt, src, Int.one)); //reg = i +/- 1
        assign(reg, node.expression);
    }

    private void visitAssignDest(ExprNode node) {
        if (node instanceof VarExprNode varNode) {
            Entity entity = curScope.getVarEntity(varNode.identifier);
            if (entity == null) visitClassMemberExpr(varNode, getThisParameter(), node);
            else node.setAssignDest(entity);
            //optimization: we don't need to load the value of the variable for assign dest
        } else node.accept(this);
    }

    @Override
    public void visit(AssignExprNode node) {
        if (isReturned()) return;
        node.rhs.accept(this);
        visitAssignDest(node.lhs);
        assign(node.rhs.entity, node.lhs);
        node.entity = node.rhs.entity;
    }

    @Override
    public void visit(TernaryExprNode node) {
        if (isReturned()) return;
        node.condition.accept(this);
        Entity cond = node.condition.entity;
        node.ifExpr.accept(this);
        node.elseExpr.accept(this);

        node.entity = Register.anonymous(IRType.from(node.type));
        curBlock.addInstruct(new Select(node.entity, cond, node.ifExpr.entity, node.elseExpr.entity));
    }

    @Override
    public void visit(BinaryExprNode node) {
        if (isReturned()) return;
        node.lhs.accept(this);

        if (node.isLogic()) { //short circuit assignment: avoid phi version
            node.entity = Register.anonymous(INType.IRBool);
            var ptr = Register.anonymous(INType.IRBool.asPtr());
            curBlock.addInstruct(new Alloca(ptr, INType.IRBool));
            curBlock.addInstruct(new Store(node.lhs.entity, ptr));

            String postfix = curFunction.getLabelPostfix();
            IRBlock rhsBlock = new IRBlock("logic.rhs" + postfix, curFunction);
            IRBlock endBlock = new IRBlock("logic.end" + postfix, curFunction);

            boolean and = node.operator.equals("&&");
            //if and true (&&), check if lhs != true, result = lhs = false, else result = rhs
            //if and false (||), check if lhs != false, result = lhs = true, else result = rhs

            Branch branch = and ? new Branch(node.lhs.entity, rhsBlock, endBlock) :
                    new Branch(node.lhs.entity, endBlock, rhsBlock);
            tryTerminateBlock(branch);

            setCurBlock(rhsBlock);
            node.rhs.accept(this);
            curBlock.addInstruct(new Store(node.rhs.entity, ptr));
            tryTerminateBlock(new Jump(endBlock));

            setCurBlock(endBlock);
            curBlock.addInstruct(new Load(node.entity, ptr));
            return;
        }

        node.rhs.accept(this);
        if (node.isCmp()) {
            node.entity = Register.anonymous(INType.IRBool);
            var op = Icmp.convert(node.operator);
            curBlock.addInstruct(new Icmp(node.entity, op, node.lhs.entity, node.rhs.entity));
        } else if (node.isAdd()) {
            if (node.type.isInt()) {
                node.entity = Register.anonymous(INType.IRInt);
                curBlock.addInstruct(new Binary(node.entity, Binary.BinaryOp.add, INType.IRInt, node.lhs.entity, node.rhs.entity));
            } else { //string
                node.entity = Register.anonymous(PtrType.IRStringLiteral);
                Call call = new Call(node.entity, "__string.add", PtrType.IRStringLiteral);
                call.addArg(node.lhs.entity);
                call.addArg(node.rhs.entity);
                curBlock.addInstruct(call);
            }
        } else { //arithmetic or bits operation
            node.entity = Register.anonymous(INType.IRInt);
            var op = Binary.convert(node.operator);
            curBlock.addInstruct(new Binary(node.entity, op, INType.IRInt, node.lhs.entity, node.rhs.entity));
        }
    }

    @Override
    public void visit(ArrayExprNode node) {
        if (isReturned()) return;
        node.array.accept(this);
        Entity prePtr = null, ptr = node.array.entity;
        for (ExprNode indexNode : node.indexes) {
            prePtr = ptr;
            indexNode.accept(this);
            Entity index = indexNode.entity;
            prePtr = offsetPtr(prePtr, index);
            ptr = Register.anonymous(prePtr.type.deconstruct());
            curBlock.addInstruct(new Load(ptr, prePtr));
        }
        node.entity = ptr;
        node.setAssignDest(prePtr);
    }

}
