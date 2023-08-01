package AST.Expr;

import AST.ASTVisitor;
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

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
