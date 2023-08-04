package Util.Scope;

import AST.Def.ClassDefNode;
import Util.*;
import Util.Error.SemanticError;

import java.util.HashMap;

public class Scope {
    private final HashMap<String, Type> varMembers = new HashMap<>();
    private final Scope parentScope;
    public ClassDefNode inClass = null;
    public Type returnType = null; //for constructor, returnType = Type.Void
    public boolean isReturned = false;

    public Scope(Scope parentScope) { //for normal suite
        this.parentScope = parentScope;
    }

    public Scope(Scope parentScope, ClassDefNode inClass) { //for class
        this.parentScope = parentScope;
        this.inClass = inClass;
    }

    public Scope(Scope parentScope, Type returnType) { //for function
        this.parentScope = parentScope;
        this.returnType = returnType;
    }

    public boolean inFunction() {
        return returnType != null;
    }

    public Scope getParent() {
        return parentScope;
    }

    public void defineVariable(String name, Type type, Position pos) {
        if (varMembers.containsKey(name))
            throw new SemanticError("variable redefinition of " + name, pos);
        varMembers.put(name, type);
    }

    public boolean containsVariable(String name, boolean lookUpon) {
        if (varMembers.containsKey(name)) return true;
        else if (parentScope != null && lookUpon)
            return parentScope.containsVariable(name, true);
        else return false;
    }

    public Type getType(String name, boolean lookUpon) {
        if (varMembers.containsKey(name)) return varMembers.get(name);
        else if (parentScope != null && lookUpon)
            return parentScope.getType(name, true);
        return null;
    }

}

