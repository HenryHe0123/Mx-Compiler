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

public class IRBuilder implements ASTVisitor {
    GlobalScope gScope;
    private Scope curScope;
    private Type curType = null;
    private IRBlock curBlock = null;
    private IRFunction curFunction = null;
    private final IRFunction globalVarInit = IRFunction.globalVarInit();
    IRRoot root;

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

    private void jumpRet() {
        curBlock.addInstruct(new Jump(curFunction.returnBlock));
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
        if (node.returnExpr != null) {
            node.returnExpr.accept(this);
            Entity retVal = node.returnExpr.entity;
            curBlock.addInstruct(new Store(retVal, curFunction.returnReg));
        }
        jumpRet();
    }

    @Override
    public void visit(BlockStmtNode node) {
        curScope = new Scope(curScope, curType);
        node.stmts.forEach(stmt -> stmt.accept(this));
        curScope = curScope.getParent();
    }

    @Override
    public void visit(BranchStmtNode node) {
        node.condition.accept(this);
//        Entity cond = node.condition.entity;
//        String postfix = curFunction.getLabelPostfix();
//        IRBlock thenBlock = new IRBlock("if.then" + postfix, curFunction);
//        IRBlock elseBlock = node.elseStmt == null ? null : new IRBlock("if.else" + postfix, curFunction);
//        IRBlock mergeBlock = new IRBlock("if.merge" + postfix, curFunction);
    }

    // ------------------------------ expression ------------------------------

    @Override
    public void visit(LiteralExprNode node) {
        if (node.entity == null) node.entity = Entity.from(node.value);
    }

    @Override
    public void visit(VarExprNode node) {
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

}
