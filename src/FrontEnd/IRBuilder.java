package FrontEnd;

import AST.*;
import AST.Def.*;
import AST.Expr.primary.*;
import AST.Util.VarDeclareUnitNode;
import IR.*;
import IR.Entity.*;
import IR.Instruction.*;
import IR.Instruction.Expression.*;
import IR.Type.IRType;
import Util.Scope.GlobalScope;
import Util.Scope.Scope;
import Util.Type;

public class IRBuilder implements ASTVisitor {
    GlobalScope gScope;
    Scope curScope;
    Type curType = null;
    IRBlock curBlock = null;
    private final FunctionDef globalVarInit = FunctionDef.globalVarInit();
    IRRoot root;

    public IRBuilder(GlobalScope gScope, IRRoot root) {
        this.gScope = gScope;
        curScope = gScope;
        this.root = root;
    }

    private boolean inGlobal() {
        return curScope instanceof GlobalScope;
    }

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
            Entity reg = new GlobalVar(node.identifier, IRType.from(curType));
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

    // ------------------------------ statement ------------------------------


    // ------------------------------ expression ------------------------------

    @Override
    public void visit(LiteralExprNode node) {
        if (node.entity == null)
            node.entity = Entity.from(node.value);
    }

    @Override
    public void visit(VarExprNode node) {
        if (node.entity == null) {
            Entity entity = curScope.getVarEntity(node.identifier);
            IRType baseType = entity.type.deconstruct();
            //node.entity = Register.anonymous(baseType);
            node.entity = new Register(node.identifier + node.asPostfix(), baseType);
            curBlock.addInstruct(new Load(node.entity, baseType, entity));
        }
    }

}
