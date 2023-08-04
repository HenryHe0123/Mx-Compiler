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

    public boolean isLogic() { //bool operation
        return operator.equals("&&") || operator.equals("||");
    }

    public boolean isCmp() {
        return operator.equals("<") || operator.equals(">") || operator.equals("<=") || operator.equals(">=") || operator.equals("==") || operator.equals("!=");
    }

    public boolean isBitOperation() {
        return operator.equals("<<") || operator.equals(">>") || operator.equals("&") || operator.equals("^") || operator.equals("|");
    }

    public boolean isArithmetic() {
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/") || operator.equals("%");
    }

    public boolean isAdd() {
        return operator.equals("+");
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
