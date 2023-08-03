package AST.Def;

import AST.*;
import AST.Util.FuncParameterNode;
import Util.*;

import java.util.ArrayList;

public class FuncDefNode extends ASTNode {
    public Type type;
    public String identifier;
    public FuncParameterNode parameter; //use emptyParaNode parameter instead of null
    public ArrayList<StmtNode> stmts = new ArrayList<>();

    public FuncDefNode(Position pos) {
        super(pos);
    }

    public FuncDefNode(Position pos, Type type, String id, FuncParameterNode parameter) {
        super(pos);
        identifier = id;
        this.type = type;
        this.parameter = parameter;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    // built-in functions -----------------------------------------------------------------

    static public FuncDefNode Print =
            new FuncDefNode(null, Type.Void, "print", FuncParameterNode.singleStrParaNode);

    static public FuncDefNode Println =
            new FuncDefNode(null, Type.Void, "println", FuncParameterNode.singleStrParaNode);

    static public FuncDefNode PrintInt =
            new FuncDefNode(null, Type.Void, "printInt", FuncParameterNode.singleIntParaNode);

    static public FuncDefNode PrintlnInt =
            new FuncDefNode(null, Type.Void, "printlnInt", FuncParameterNode.singleIntParaNode);

    static public FuncDefNode GetString =
            new FuncDefNode(null, Type.String, "getString", FuncParameterNode.emptyParaNode);

    static public FuncDefNode GetInt =
            new FuncDefNode(null, Type.Int, "getInt", FuncParameterNode.emptyParaNode);

    static public FuncDefNode ToString =
            new FuncDefNode(null, Type.String, "toString", FuncParameterNode.singleIntParaNode);

}
