package AST.Expr.primary;

import AST.ASTVisitor;
import AST.ExprNode;
import Util.Position;

import java.util.ArrayList;

public class FuncExprNode extends ExprNode {
    public String funcName;
    public ArrayList<ExprNode> args = new ArrayList<>();

    public FuncExprNode(Position pos) {
        super(pos);
    }

    public FuncExprNode(Position pos, String funcName) {
        super(pos);
        this.funcName = funcName;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
