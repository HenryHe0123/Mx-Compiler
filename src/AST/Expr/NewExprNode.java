package AST.Expr;

import AST.ASTVisitor;
import AST.ExprNode;
import Util.Position;
import Util.Type;

import java.util.ArrayList;

public class NewExprNode extends ExprNode {
    public ArrayList<ExprNode> dimExpr = new ArrayList<>(); //type message already saved in ExprNode

    public NewExprNode(Position pos) {
        super(pos);
    }

    public NewExprNode(Position pos, Type type) {
        super(pos);
        this.type = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
