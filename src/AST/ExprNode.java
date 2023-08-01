package AST;

import Util.*;

public abstract class ExprNode extends ASTNode {
    Type type;

    public ExprNode(Position pos) {
        super(pos);
    }
}
