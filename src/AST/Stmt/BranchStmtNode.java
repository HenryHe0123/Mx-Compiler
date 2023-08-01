package AST.Stmt;

import AST.*;
import Util.Position;

public class BranchStmtNode extends StmtNode {
    public ExprNode condition;
    public StmtNode ifStmt, elseStmt;

    public BranchStmtNode(Position pos) {
        super(pos);
    }

    public BranchStmtNode(Position pos, ExprNode condition, StmtNode ifStmt, StmtNode elseStmt) {
        super(pos);
        this.condition = condition;
        this.ifStmt = ifStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
