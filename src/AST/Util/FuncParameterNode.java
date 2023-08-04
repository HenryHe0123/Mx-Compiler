package AST.Util;

import AST.ASTNode;
import AST.ASTVisitor;
import Util.Position;
import Util.Type;

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

    static private FuncParameterNode singleStrParaNodeBuild() {
        FuncParameterNode node = new FuncParameterNode(null);
        node.args.add(new ParameterUnitNode(null, Type.String(), "str"));
        return node;
    }

    static private FuncParameterNode singleIntParaNodeBuild() {
        FuncParameterNode node = new FuncParameterNode(null);
        node.args.add(new ParameterUnitNode(null, Type.Int(), "n"));
        return node;
    }

    static private FuncParameterNode doubleIntParaNodeBuild() {
        FuncParameterNode node = new FuncParameterNode(null);
        node.args.add(new ParameterUnitNode(null, Type.Int(), "left"));
        node.args.add(new ParameterUnitNode(null, Type.Int(), "right"));
        return node;
    }

    static public FuncParameterNode singleStrParaNode = singleStrParaNodeBuild();
    static public FuncParameterNode singleIntParaNode = singleIntParaNodeBuild();
    static public FuncParameterNode doubleIntParaNode = doubleIntParaNodeBuild();
    static public FuncParameterNode emptyParaNode = new FuncParameterNode(null);
}
