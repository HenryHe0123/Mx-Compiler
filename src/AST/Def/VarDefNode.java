package AST.Def;

import AST.*;
import AST.Util.VarDeclareUnitNode;
import Util.Position;
import Util.Type;

import java.util.ArrayList;

public class VarDefNode extends StmtNode { //also used as varDefStmtNode
    public Type type;
    public ArrayList<VarDeclareUnitNode> varDeclareUnits = new ArrayList<>();

    public VarDefNode(Position pos) {
        super(pos);
    }

    public VarDefNode(Position pos, Type type) {
        super(pos);
        this.type = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
