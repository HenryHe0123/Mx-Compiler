package AST;

import AST.Def.*;
import Util.Position;

import java.util.ArrayList;

public class RootNode extends ASTNode {
    public ArrayList<FuncDefNode> funcDefs = new ArrayList<>();
    public ArrayList<VarDefNode> varDefs = new ArrayList<>();
    public ArrayList<ClassDefNode> classDefs = new ArrayList<>();
    public ArrayList<ASTNode> defs = new ArrayList<>();  //I need it for stupid reasons

    public RootNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
