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

import java.util.Stack;

public class IRBuilder implements ASTVisitor {
    GlobalScope gScope;
    private Scope curScope;
    private Type curType = null;
    private IRBlock curBlock = null;
    private IRFunction curFunction = null;
    private ClassType curClass = null;
    private final Stack<IRBlock> loopContinueBlock = new Stack<>();
    private final Stack<IRBlock> loopEndBlock = new Stack<>();
    private final IRFunction globalVarInit = IRFunction.globalVarInit();
    private final IRRoot root;

    public IRBuilder(GlobalScope gScope, IRRoot root) {
        this.gScope = gScope;
        curScope = gScope;
        this.root = root;
    }

    // ------------------------------ utility ------------------------------

    private boolean inGlobal() {
        return curScope instanceof GlobalScope;
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
        curBlock = null;
    }

    private void tryTerminateBlock(Terminator terminator) { //recommend: safer version
        if (isReturned()) return;
        curBlock.trySetTerminator(terminator);
        curFunction.addBlock(curBlock);
        curBlock = null;
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

    private void collectClassTypes(RootNode node) {
        IRType.addIRClassType("string");
        node.classDefs.forEach(classDef -> IRType.addIRClassType(classDef.identifier));
    }

    // ------------------------------ visit ------------------------------

    @Override
    public void visit(RootNode node) {
        root.functions.add(globalVarInit);
        collectClassTypes(node); //collect all class types first
        node.classDefs.forEach(classDef -> classDef.accept(this));
        node.varDefs.forEach(varDef -> varDef.accept(this));
        node.funcDefs.forEach(funcDef -> funcDef.accept(this));
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
        if (inGlobal()) {
            Entity reg = new GlobalVar(node.identifier + curScope.asPostfix(), IRType.from(curType));
            Entity init = Entity.init(curType);
            if (node.expression != null) { //if with an init expression
                curBlock = globalVarInit.entry;
                node.expression.accept(this);
                var entity = node.expression.entity;
                if (entity.isConstant()) init = entity;
                else curBlock.addInstruct(new Store(entity, reg));
                curBlock = null; //prevent misuse
            }
            root.globals.add(new GlobalDef(reg, init, GlobalDef.globalDefType.global));
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
        curBlock = curFunction.entry;
        root.functions.add(curFunction);
        if (curFunction.isMain()) curBlock.addInstruct(Call.callGlobalVarInit());
        if (curClass != null) addThisParameter();
        if (node.parameter != null) node.parameter.args.forEach(arg -> arg.accept(this));
        node.stmts.forEach(stmt -> stmt.accept(this));
        curScope = curScope.getParent();
        curFunction = null;
        curBlock = null;
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
        curClass = IRType.getIRClassType(className); //already collected
        for (VarDefNode vars : node.varDefs) {
            IRType type = IRType.from(vars.type);
            for (VarDeclareUnitNode var : vars.varDeclareUnits) {
                curClass.addMember(var.identifier, type);
            }
        }
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
        curBlock = curFunction.entry;
        root.functions.add(curFunction);
        addThisParameter();
        node.stmts.forEach(stmt -> stmt.accept(this));
        curBlock = null;
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

            curBlock = thenBlock;
            curScope = new Scope(curScope);
            node.ifStmt.accept(this); //curBlock maybe terminated already
            tryTerminateBlock(new Jump(endBlock));
        } else {
            IRBlock elseBlock = new IRBlock("if.else" + postfix, curFunction);
            tryTerminateBlock(new Branch(cond, thenBlock, elseBlock));

            curBlock = thenBlock;
            curScope = new Scope(curScope);
            node.ifStmt.accept(this);
            boolean ifReturned = isReturned();
            tryTerminateBlock(new Jump(endBlock));
            curScope = curScope.getParent();

            curBlock = elseBlock;
            curScope = new Scope(curScope);
            node.elseStmt.accept(this);
            ret = ifReturned && isReturned(); //if both branches are returned, we can return
            tryTerminateBlock(new Jump(endBlock));
        }
        curScope = curScope.getParent();
        if (!ret) curBlock = endBlock;
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

        curBlock = condBlock;
        if (node.condition != null) node.condition.accept(this);
        Entity cond = (node.condition != null) ? node.condition.entity : Entity.from(true);
        tryTerminateBlock(new Branch(cond, bodyBlock, endBlock));

        curBlock = bodyBlock;
        if (node.body != null) node.body.accept(this);
        tryTerminateBlock(new Jump(stepBlock));

        curBlock = stepBlock;
        if (node.step != null) node.step.accept(this);
        tryTerminateBlock(new Jump(condBlock));

        curBlock = endBlock;
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

        curBlock = condBlock;
        node.condition.accept(this);
        Entity cond = node.condition.entity;
        tryTerminateBlock(new Branch(cond, bodyBlock, endBlock));

        curBlock = bodyBlock;
        if (node.body != null) node.body.accept(this);
        tryTerminateBlock(new Jump(condBlock));

        curBlock = endBlock;
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
        if (node.entity == null) node.entity = Entity.from(node.value);
    }

    @Override
    public void visit(VarExprNode node) {
        if (isReturned()) return;
        if (node.entity == null) {
            //"this" is handled just like normal variable, as we have already added it to the map
            Entity entity = curScope.getVarEntity(node.identifier);
            node.setAssignDest(entity);
            IRType baseType = entity.type.deconstruct();
            node.entity = Register.anonymous(baseType);
            curBlock.addInstruct(new Load(node.entity, baseType, entity));
        }
    }

    @Override
    public void visit(FuncExprNode node) {
        if (isReturned()) return;
        //todo: currently only for global function
        IRType returnType = IRType.from(node.type);
        node.entity = returnType.isVoid() ? null : Register.anonymous(returnType);
        Call call = new Call(node.entity, node.funcName, returnType);
        node.args.forEach(arg -> {
            arg.accept(this);
            call.addArg(arg.entity);
        });
        curBlock.addInstruct(call);
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
                    case "++" -> new Binary(node.entity, Binary.BinaryOp.add, INType.IRInt, src, Entity.from(1));
                    case "--" -> new Binary(node.entity, Binary.BinaryOp.sub, INType.IRInt, src, Entity.from(1));
                    case "-" -> new Binary(node.entity, Binary.BinaryOp.sub, INType.IRInt, Entity.from(0), src);
                    case "~" -> new Binary(node.entity, Binary.BinaryOp.xor, INType.IRInt, src, Entity.from(-1));
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
        curBlock.addInstruct(new Binary(reg, op, INType.IRInt, src, Entity.from(1))); //reg = i +/- 1
        assign(reg, node.expression);
    }

    @Override
    public void visit(AssignExprNode node) {
        if (isReturned()) return;
        node.rhs.accept(this);
        node.lhs.accept(this); //we need to set assign dest here
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
            var condition = Register.anonymous(INType.IRBool);
            curBlock.addInstruct(new Icmp(condition, Icmp.IcmpOp.ne, node.lhs.entity, Entity.from(and)));
            tryTerminateBlock(new Branch(condition, endBlock, rhsBlock));

            curBlock = rhsBlock;
            node.rhs.accept(this);
            curBlock.addInstruct(new Store(node.rhs.entity, ptr));
            tryTerminateBlock(new Jump(endBlock));

            curBlock = endBlock;
            curBlock.addInstruct(new Load(node.entity, INType.IRBool, ptr));
            return;
        }

        node.rhs.accept(this);
        if (node.isCmp()) {
            node.entity = Register.anonymous(INType.IRBool);
            var op = Icmp.convert(node.operator);
            curBlock.addInstruct(new Icmp(node.entity, op, node.lhs.entity, node.rhs.entity));
        } else if (node.isAdd()) { //todo: string also support add operation
            node.entity = Register.anonymous(INType.IRInt);
            curBlock.addInstruct(new Binary(node.entity, Binary.BinaryOp.add, INType.IRInt, node.lhs.entity, node.rhs.entity));
        } else { //arithmetic or bits operation
            node.entity = Register.anonymous(INType.IRInt);
            var op = Binary.convert(node.operator);
            curBlock.addInstruct(new Binary(node.entity, op, INType.IRInt, node.lhs.entity, node.rhs.entity));
        }
    }

}
