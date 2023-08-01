package AST.Def;

import AST.*;
import Util.Position;

import java.util.ArrayList;

public class ClassDefNode extends ASTNode {
    public String identifier;
    public ArrayList<VarDefNode> varDefs = new ArrayList<>();
    public ArrayList<FuncDefNode> funcDefs = new ArrayList<>();
    public StmtNode constructor;

    public ClassDefNode(Position pos, String id) {
        super(pos);
        identifier = id;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
