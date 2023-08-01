package AST.Stmt;

import AST.*;
import Util.Position;

public class ReturnStmtNode extends StmtNode {
    public ExprNode returnVal = null;

    public ReturnStmtNode(Position pos) {
        super(pos);
    }

    public ReturnStmtNode(Position pos, ExprNode expression) {
        super(pos);
        returnVal = expression;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
