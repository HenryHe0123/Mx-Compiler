package AST.Stmt;

import AST.*;
import Util.Position;

public class ReturnStmtNode extends StmtNode {
    public ExprNode returnExpr = null;

    public ReturnStmtNode(Position pos) {
        super(pos);
    }

    public ReturnStmtNode(Position pos, ExprNode expression) {
        super(pos);
        returnExpr = expression;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
