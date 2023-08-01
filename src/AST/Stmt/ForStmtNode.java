package AST.Stmt;

import AST.*;
import Util.Position;

public class ForStmtNode extends StmtNode {
    public StmtNode init;
    public ExprNode condition, step;
    public StmtNode body;

    public ForStmtNode(Position pos) {
        super(pos);
    }

    public ForStmtNode(Position pos, StmtNode init, ExprNode condition, ExprNode step, StmtNode body) {
        super(pos);
        this.init = init;
        this.condition = condition;
        this.step = step;
        this.body = body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
