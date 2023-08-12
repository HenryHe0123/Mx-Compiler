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
import Util.Error.SemanticError;
import Util.Scope.*;
import Util.Type;

public class IRBuilder implements ASTVisitor {
    GlobalScope gScope;
    private Scope curScope;
    private Type curType = null;
    private IRBlock curBlock = null;
    private IRFunction curFunction = null;
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

    // ------------------------------ visit ------------------------------

    @Override
    public void visit(RootNode node) {
        root.functions.add(globalVarInit);
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
        }
    }

    @Override
    public void visit(FuncDefNode node) {
        curScope = new Scope(curScope, node.type);
        curFunction = new IRFunction(node.identifier, IRType.from(node.type));
        curBlock = curFunction.entry;
        root.functions.add(curFunction);
        if (node.parameter != null) node.parameter.args.forEach(arg -> arg.accept(this));
        node.stmts.forEach(stmt -> stmt.accept(this));
        curScope = curScope.getParent();
        curFunction = null;
        curBlock = null;
    }

    @Override
    public void visit(ParameterUnitNode node) {
        node.entity = new Register(node.identifier + curScope.asPostfix(), IRType.from(node.type));
        curScope.putVarEntity(node.identifier, node.entity);
        curFunction.addParameter(node.entity);
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

    // ------------------------------ expression ------------------------------

    @Override
    public void visit(LiteralExprNode node) {
        if (node.entity == null) node.entity = Entity.from(node.value);
    }

    @Override
    public void visit(VarExprNode node) {
        if (isReturned()) return;
        if (node.entity == null) { //todo: this
            Entity entity = curScope.getVarEntity(node.identifier);
            node.setAssignDest(entity);
            IRType baseType = entity.type.deconstruct();
            node.entity = Register.anonymous(baseType);
            curBlock.addInstruct(new Load(node.entity, baseType, entity));
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

}
