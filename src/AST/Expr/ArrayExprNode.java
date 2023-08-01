package AST.Expr;

import AST.ASTVisitor;
import AST.ExprNode;
import Util.Position;

public class ArrayExprNode extends ExprNode {
    public ExprNode array;
    public ExprNode index;

    public ArrayExprNode(Position pos, ExprNode array, ExprNode index) {
        super(pos);
        this.array = array;
        this.index = index;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
