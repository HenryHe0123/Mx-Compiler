package AST.Expr;

import AST.ASTVisitor;
import AST.ExprNode;
import Util.Position;

public class UnaryExprNode extends ExprNode {
    public ExprNode expression;
    public String operator;

    public UnaryExprNode(Position pos) {
        super(pos);
    }

    public UnaryExprNode(Position pos, String op, ExprNode expr) {
        super(pos);
        expression = expr;
        operator = op;
    }

    public boolean isBool() {
        return (operator.equals("!"));
    }

    public boolean isInt() {
        return (operator.equals("++") || operator.equals("--") || operator.equals("-") || operator.equals("~"));
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
