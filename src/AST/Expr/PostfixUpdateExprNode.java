package AST.Expr;

import AST.ASTVisitor;
import AST.ExprNode;
import Util.Position;

public class PostfixUpdateExprNode extends ExprNode {
    public ExprNode expression;
    public boolean add; //++ or --

    public PostfixUpdateExprNode(Position pos, ExprNode expr, boolean isAdd) {
        super(pos);
        expression = expr;
        add = isAdd;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
