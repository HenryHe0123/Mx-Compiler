package AST.Expr;

import AST.ASTVisitor;
import AST.ExprNode;
import Util.Position;

import java.util.ArrayList;

public class ArrayExprNode extends ExprNode {
    public ExprNode array;
    public ArrayList<ExprNode> indexes = new ArrayList<>();

    public ArrayExprNode(Position pos, ExprNode array) {
        super(pos);
        this.array = array;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
