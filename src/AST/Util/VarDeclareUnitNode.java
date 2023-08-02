package AST.Util;

import AST.*;
import Util.Position;

public class VarDeclareUnitNode extends ASTNode {
    public String identifier;
    public ExprNode expression = null;

    public VarDeclareUnitNode(Position pos) {
        super(pos);
    }

    public VarDeclareUnitNode(Position pos, String id) {
        super(pos);
        identifier = id;
    }

    public VarDeclareUnitNode(Position pos, String id, ExprNode expr) {
        super(pos);
        identifier = id;
        expression = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
