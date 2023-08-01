package AST.Def;

import AST.*;
import AST.Util.VarDeclareUnitNode;
import Util.Position;

import java.util.ArrayList;

public class VarDefNode extends StmtNode { //also used as varDefStmtNode
    public ArrayList<VarDeclareUnitNode> varDeclareUnits = new ArrayList<>();

    public VarDefNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
