package Util.Scope;

import AST.Def.*;
import AST.Expr.primary.FuncExprNode;
import Util.*;
import Util.Error.SemanticError;

import java.util.HashMap;

public class GlobalScope extends Scope {
    public HashMap<String, FuncDefNode> funcMembers = new HashMap<>();
    public HashMap<String, ClassDefNode> classMembers = new HashMap<>();

    public GlobalScope() {
        super(null);
    }

    public void initialize() {
        //add built-in functions and classes
        funcMembers.put("print", FuncDefNode.Print);
        funcMembers.put("println", FuncDefNode.Println);
        funcMembers.put("printInt", FuncDefNode.PrintInt);
        funcMembers.put("printlnInt", FuncDefNode.PrintlnInt);
        funcMembers.put("getString", FuncDefNode.GetString);
        funcMembers.put("getInt", FuncDefNode.GetInt);
        funcMembers.put("toString", FuncDefNode.ToString);
        classMembers.put("string", ClassDefNode.String);
        classMembers.put("int", ClassDefNode.Int);
        classMembers.put("bool", ClassDefNode.Bool);
    }

    public void addFunc(String name, FuncDefNode funcDef) {
        if (hasSymbol(name)) throw new SemanticError("function redefinition of " + name, funcDef.pos);
        funcMembers.put(name, funcDef);
    }

    public FuncDefNode getFuncDefNode(String name, Position pos) {
        if (funcMembers.containsKey(name)) return funcMembers.get(name);
        throw new SemanticError("no such function: " + name, pos);
    }

    public FuncDefNode getFuncDefNode(String name) {
        return funcMembers.get(name);  // may return null
    }

    public void addClass(String name, ClassDefNode classDef) {
        if (hasSymbol(name)) throw new SemanticError("class redefinition of " + name, classDef.pos);
        classMembers.put(name, classDef);
    }

    public ClassDefNode getClassDefNode(String name, Position pos) {
        if (classMembers.containsKey(name)) return classMembers.get(name);
        throw new SemanticError("no such class: " + name, pos);
    }

    public ClassDefNode getClassDefNode(String name) {
        return classMembers.get(name);  // may return null
    }

    public boolean hasFunc(String name) {
        return funcMembers.containsKey(name);
    }

    public boolean hasClass(String name) {
        return classMembers.containsKey(name);
    }

    public boolean hasSymbol(String name) {
        return hasFunc(name) || hasClass(name);
    }

    public boolean callGlobalFuncCorrect(FuncExprNode call) {
        String funcName = call.funcName;
        if (funcMembers.containsKey(funcName)) {
            return funcMembers.get(funcName).callFunctionCorrect(call);
        }
        return false;
    }

    public boolean callMethodCorrect(Type type, FuncExprNode call) {
        if (type.isArray()) {
            return FuncDefNode.Size.callFunctionCorrect(call);
        }
        String className = type.typename;
        if (classMembers.containsKey(className)) {
            return classMembers.get(className).callMethodCorrect(call);
        }
        return false;
    }

    public Type getCallGlobalFuncType(FuncExprNode call) { //return null if failed
        String funcName = call.funcName;
        if (funcMembers.containsKey(funcName)) {
            var funcDef = funcMembers.get(funcName);
            if (funcDef.callFunctionCorrect(call)) return funcDef.type;
            else return null;
        }
        return null;
    }

    public Type getCallMethodType(Type type, FuncExprNode call) { //return null if failed
        if (type.isArray()) {
            if (FuncDefNode.Size.callFunctionCorrect(call)) return Type.Int;
            else return null;
        }
        String className = type.typename;
        if (classMembers.containsKey(className)) {
            return classMembers.get(className).getCallMethodType(call);
        }
        return null;
    }
}
