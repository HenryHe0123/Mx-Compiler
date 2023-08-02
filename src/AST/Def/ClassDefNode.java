package AST.Def;

import AST.*;
import AST.Util.ClassConstructorNode;
import Util.Position;

import java.util.ArrayList;

public class ClassDefNode extends ASTNode {
    public String identifier;
    public ArrayList<VarDefNode> varDefs = new ArrayList<>();
    public ArrayList<FuncDefNode> funcDefs = new ArrayList<>();
    public ClassConstructorNode constructor;

    public ClassDefNode(Position pos, String id) {
        super(pos);
        identifier = id;
        constructor = new ClassConstructorNode(pos, id); //default constructor
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
