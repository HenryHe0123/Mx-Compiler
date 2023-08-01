package AST;

import Util.*;

public abstract class ExprNode extends ASTNode {
    public Type type;

    public ExprNode(Position pos) {
        super(pos);
    }
}
