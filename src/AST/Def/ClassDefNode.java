package AST.Def;

import AST.*;
import AST.Util.ClassConstructorNode;
import AST.Util.FuncParameterNode;
import Util.Position;
import Util.Type;

import java.util.ArrayList;

public class ClassDefNode extends ASTNode {
    public String identifier;
    public ArrayList<VarDefNode> varDefs = new ArrayList<>();
    public ArrayList<FuncDefNode> funcDefs = new ArrayList<>();
    public ClassConstructorNode constructor; //build-in class's constructor is null

    public ClassDefNode(Position pos, String id) {
        super(pos);
        identifier = id;
        if (id.equals("string") || id.equals("int") || id.equals("bool")) constructor = null;
        else constructor = new ClassConstructorNode(pos, id); //default constructor
    }

    public boolean isBuiltIn() {
        return constructor == null;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    // built-in classes -----------------------------------------------------------------

    static private ClassDefNode StringBuild() {
        ClassDefNode stringDef = new ClassDefNode(null, "string");
        FuncDefNode LengthFunc = new FuncDefNode(null, Type.Int, "length", FuncParameterNode.emptyParaNode);
        FuncDefNode SubStringFunc = new FuncDefNode(null, Type.String, "substring", FuncParameterNode.doubleIntParaNode);
        FuncDefNode ParseIntFunc = new FuncDefNode(null, Type.Int, "parseInt", FuncParameterNode.emptyParaNode);
        FuncDefNode OrdFunc = new FuncDefNode(null, Type.Int, "ord", FuncParameterNode.singleIntParaNode);
        stringDef.funcDefs.add(LengthFunc);
        stringDef.funcDefs.add(SubStringFunc);
        stringDef.funcDefs.add(ParseIntFunc);
        stringDef.funcDefs.add(OrdFunc);
        return stringDef;
    }

    static public ClassDefNode String = StringBuild();
    static public ClassDefNode Int = new ClassDefNode(null, "int");
    static public ClassDefNode Bool = new ClassDefNode(null, "bool");
}
