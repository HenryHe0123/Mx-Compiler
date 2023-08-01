package AST.Def;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.Util.VarDeclareUnitNode;
import Util.Position;

import java.util.ArrayList;

public class VarDefNode extends ASTNode {
    public ArrayList<VarDeclareUnitNode> varDeclareUnits = new ArrayList<>();

    public VarDefNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
