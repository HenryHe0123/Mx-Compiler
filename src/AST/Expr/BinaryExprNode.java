package AST.Expr;

import AST.ASTVisitor;
import AST.ExprNode;
import Util.Position;

public class BinaryExprNode extends ExprNode {
    public ExprNode lhs, rhs;
    public String operator;

    public BinaryExprNode(Position pos, ExprNode lhs, String op, ExprNode rhs) {
        super(pos);
        this.lhs = lhs;
        operator = op;
        this.rhs = rhs;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
