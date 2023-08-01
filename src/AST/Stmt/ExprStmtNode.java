package AST.Stmt;

import AST.*;
import Util.Position;

public class ExprStmtNode extends StmtNode {
    public ExprNode expression = null;

    public ExprStmtNode(Position pos) {
        super(pos);
    }

    public ExprStmtNode(Position pos, ExprNode expr) {
        super(pos);
        expression = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
