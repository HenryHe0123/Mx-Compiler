package AST.Expr;

import AST.ASTVisitor;
import AST.ExprNode;
import Util.Position;

public class AssignExprNode extends ExprNode {
    public ExprNode lhs, rhs;

    public AssignExprNode(Position pos, ExprNode lhs, ExprNode rhs) {
        super(pos);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}