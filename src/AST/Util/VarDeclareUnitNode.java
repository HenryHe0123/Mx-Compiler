package AST.Util;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.ExprNode;
import Util.*;

public class VarDeclareUnitNode extends ASTNode {
    public Type type;
    public String identifier;
    public ExprNode expression = null;

    public VarDeclareUnitNode(Position pos) {
        super(pos);
    }

    public VarDeclareUnitNode(Position pos, String id, Type type) {
        super(pos);
        identifier = id;
        this.type = type;
    }

    public VarDeclareUnitNode(Position pos, String id, Type type, ExprNode expr) {
        super(pos);
        identifier = id;
        this.type = type;
        expression = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
