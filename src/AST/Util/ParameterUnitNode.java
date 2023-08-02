package AST.Util;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.ExprNode;
import Util.Position;
import Util.Type;

public class ParameterUnitNode extends ASTNode {
    public Type type;
    public String identifier;

    public ParameterUnitNode(Position pos) {
        super(pos);
    }

    public ParameterUnitNode(Position pos, Type type, String id) {
        super(pos);
        this.type = type;
        identifier = id;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}