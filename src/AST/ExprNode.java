package AST;

import IR.Entity.Entity;
import Util.*;

public abstract class ExprNode extends ASTNode {
    public Type type;
    public Entity entity;

    public boolean isAssignable() {
        return false;
    }

    public ExprNode(Position pos) {
        super(pos);
    }
}
