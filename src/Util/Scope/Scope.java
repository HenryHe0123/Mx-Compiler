package Util.Scope;

import AST.Def.ClassDefNode;
import Util.*;
import Util.Error.SemanticError;

import java.util.HashMap;

public class Scope {
    private final HashMap<String, Type> varMembers = new HashMap<>();
    private final Scope parentScope;
    public ClassDefNode inClass = null;

    public Scope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    public Scope getParentScope() {
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

