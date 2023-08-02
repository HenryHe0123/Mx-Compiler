package AST.Stmt;

import AST.*;
import Util.Position;

public class BranchStmtNode extends StmtNode {
    public ExprNode condition;
    public StmtNode ifStmt, elseStmt = null;

    public BranchStmtNode(Position pos) {
        super(pos);
    }

    public BranchStmtNode(Position pos, ExprNode condition, StmtNode ifStmt) {
        super(pos);
        this.condition = condition;
        this.ifStmt = ifStmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
