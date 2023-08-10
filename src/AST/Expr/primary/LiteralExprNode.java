package AST.Expr.primary;

import AST.ASTVisitor;
import AST.ExprNode;
import Util.Basic;
import Util.Position;

public class LiteralExprNode extends ExprNode {
    public Basic value;

    public LiteralExprNode(Position pos, Basic value) {
        super(pos);
        this.value = value;
        type = value.type();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
