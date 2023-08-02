package AST.Expr;

import AST.ASTVisitor;
import AST.ExprNode;
import Util.Position;

public class TernaryExprNode extends ExprNode {
    public ExprNode condition, ifExpr, elseExpr;

    public TernaryExprNode(Position pos) {
        super(pos);
    }

    public TernaryExprNode(Position pos, ExprNode condition, ExprNode ifExpr, ExprNode elseExpr) {
        super(pos);
        this.condition = condition;
        this.ifExpr = ifExpr;
        this.elseExpr = elseExpr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
