package AST.Stmt;

import AST.ASTVisitor;
import AST.StmtNode;
import Util.Position;

public class CtrlStmtNode extends StmtNode {
    public boolean isBreak;

    public CtrlStmtNode(Position pos, boolean isBreak) {
        super(pos);
        this.isBreak = isBreak;
    }

    public boolean isContinue() {
        return !isBreak;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
