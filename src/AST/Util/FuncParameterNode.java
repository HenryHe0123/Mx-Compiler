package AST.Util;

import AST.ASTNode;
import AST.ASTVisitor;
import Util.Position;

import java.util.ArrayList;

public class FuncParameterNode extends ASTNode {
    public ArrayList<ParameterUnitNode> args = new ArrayList<>();

    public FuncParameterNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
