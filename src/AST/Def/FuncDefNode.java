package AST.Def;

import AST.*;
import AST.Expr.primary.FuncExprNode;
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

    public boolean callFunctionCorrect(FuncExprNode call) {
        //we won't check return type here, as it's going to be initialized after semantic check
        if (call == null) return false;
        if (!identifier.equals(call.funcName)) return false;
        if (parameter == null) {
            System.err.println("unexpected case appear: FuncDefNode's parameter is null when call function check");
            return call.args.size() == 0;
        }
        if (parameter.args.size() != call.args.size()) return false;
        for (int i = 0; i < parameter.args.size(); i++) {
//            //for debug
//            System.err.println("parameter.args.get" + i);
//            System.err.println("type we need: " + parameter.args.get(i).type.typename);
//            System.err.println("type we have: " + call.args.get(i).type.typename);
            if (parameter.args.get(i).type.notEquals(call.args.get(i).type)) return false;
        }
        return true;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    // built-in functions -----------------------------------------------------------------

    static public final FuncDefNode Print =
            new FuncDefNode(null, Type.Void(), "print", FuncParameterNode.singleStrParaNode);

    static public final FuncDefNode Println =
            new FuncDefNode(null, Type.Void(), "println", FuncParameterNode.singleStrParaNode);

    static public final FuncDefNode PrintInt =
            new FuncDefNode(null, Type.Void(), "printInt", FuncParameterNode.singleIntParaNode);

    static public final FuncDefNode PrintlnInt =
            new FuncDefNode(null, Type.Void(), "printlnInt", FuncParameterNode.singleIntParaNode);

    static public final FuncDefNode GetString =
            new FuncDefNode(null, Type.String(), "getString", FuncParameterNode.emptyParaNode);

    static public final FuncDefNode GetInt =
            new FuncDefNode(null, Type.Int(), "getInt", FuncParameterNode.emptyParaNode);

    static public final FuncDefNode ToString =
            new FuncDefNode(null, Type.String(), "toString", FuncParameterNode.singleIntParaNode);

    static public final FuncDefNode Size =    //for array
            new FuncDefNode(null, Type.Int(), "size", FuncParameterNode.emptyParaNode);
}
