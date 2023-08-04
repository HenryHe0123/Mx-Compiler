package AST.Expr;

import AST.ASTVisitor;
import AST.Expr.primary.*;
import AST.ExprNode;
import Util.Position;

public class MemberExprNode extends ExprNode {
    public ExprNode caller;
    public ExprNode member;

    public MemberExprNode(Position pos, ExprNode caller, ExprNode member) {
        super(pos);
        this.caller = caller;
        this.member = member;
    }

    public boolean dotFunc() {
        return member instanceof FuncExprNode;
    }

    public boolean dotVar() {
        return member instanceof VarExprNode;
    }

    @Override
    public boolean isAssignable() {
        return member.isAssignable();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
