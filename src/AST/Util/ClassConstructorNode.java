package AST.Util;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.StmtNode;
import Util.Position;

import java.util.ArrayList;

public class ClassConstructorNode extends ASTNode {
    public String identifier;
    public ArrayList<StmtNode> stmts = new ArrayList<>();

    public ClassConstructorNode(Position pos) {
        super(pos);
    }

    public ClassConstructorNode(Position pos, String id) {
        super(pos);
        identifier = id;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
