package AST;

import IR.Entity.Entity;
import Util.*;

public abstract class ExprNode extends ASTNode {
    public Type type;

    public boolean isAssignable() {
        return false;
    }

    public ExprNode(Position pos) {
        super(pos);
    }

    //-------------------------------- IR ---------------------------------

    public Entity entity;
    private Entity destPtr = null; // for assignable expression

    public void setAssignDest(Entity ptr) {
        if (isAssignable()) this.destPtr = ptr;
    }

    public Entity getAssignDest() {
        return destPtr;
    }
}
