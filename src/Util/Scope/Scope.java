package Util.Scope;

import AST.Def.ClassDefNode;
import IR.Entity.Entity;
import Util.*;
import Util.Error.SemanticError;

import java.util.HashMap;

public class Scope {
    private static long cnt = 0;
    public final long id = cnt;
    private final HashMap<String, Type> varMembers = new HashMap<>();
    private final Scope parentScope;
    public ClassDefNode inClass = null;
    public Type returnType = null; //for constructor, returnType = Type.Void
    public boolean isReturned = false;

    public Scope(Scope parentScope) { //for normal suite
        this.parentScope = parentScope;
        ++cnt;
    }

    public Scope(Scope parentScope, ClassDefNode inClass) { //for class
        this.parentScope = parentScope;
        this.inClass = inClass;
        ++cnt;
    }

    public String asPostfix() {
        return "_scope_" + id;
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

    public ClassDefNode insideClass() { //look up the class that this scope is in
        for (Scope scope = this; scope != null; scope = scope.parentScope) {
            if (scope.inClass != null) return scope.inClass;
        }
        return null;
    }

    //--------------------------------IR----------------------------------//

    private final HashMap<String, Entity> varEntities = new HashMap<>(); //global variables or registers

    public void putVarEntity(String name, Entity entity) {
        varEntities.put(name, entity);
    }

    public Entity getVarEntity(String name) {
        if (varEntities.containsKey(name)) return varEntities.get(name);
        else if (parentScope != null) return parentScope.getVarEntity(name);
        else return null;
    }

}

