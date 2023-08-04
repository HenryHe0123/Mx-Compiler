package AST;

import Util.*;

public abstract class ExprNode extends ASTNode {
    public Type type;

    public boolean isAssignable() {
        return false;
    }

    public ExprNode(Position pos) {
        super(pos);
    }
}
