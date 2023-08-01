package AST.Expr.primary;

import AST.ASTVisitor;
import AST.ExprNode;
import Util.Position;

public class VarExprNode extends ExprNode {
    String identifier;

    public VarExprNode(Position pos, String id) {
        super(pos);
        identifier = id;
    }

    public boolean isThis() {
        return identifier.equals("this");
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
