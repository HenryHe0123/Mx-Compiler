package IR.Entity;

import IR.Instruction.Instruction;
import IR.Type.*;
import Util.*;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Entity {
    public final IRType type;

    Entity(IRType type) {
        this.type = type;
    }

    public abstract String getText();

    public boolean isConstant() {
        return true; //including string* (globalVar)
    }

    public boolean isStrictlyConstant() {
        return false;
    }

    public String getFullText() {
        return type.getText() + " " + getText();
    }

    public static Entity init(Type type) {
        if (type.dim > 0) return Null.instance;
        return switch (type.typename) {
            case "int" -> Int.zero;
            case "bool" -> Bool.False;
            case "void" -> Void.instance;
            default -> Null.instance;
        };
    }

    public static Entity init(IRType type) {
        if (type.isVoid()) return Void.instance;
        if (type instanceof INType iType) {
            if (iType.bits == 1) return Bool.False;
            else return Int.zero;
        }
        return Null.instance;
    }

    public static Entity from(Basic value) {
        if (value.isNull) return Null.instance;
        if (value.isBool) return new Bool(value.boolVal);
        if (value.isInt) return new Int((int) value.intVal);
        return new StringLiteral(value.stringVal);
    }

    public static Entity from(boolean value) {
        return new Bool(value);
    }

    public static Entity from(int value) {
        return new Int(value);
    }

    //------------------------------------for mem2reg------------------------------------

    public LinkedList<Instruction> users = null;

    public void addUser(Instruction user) {
        if (users != null) users.add(user);
    }

    public static void addUser(Entity entity, Instruction user) {
        if (entity != null) entity.addUser(user);
    }

    public void removeUser(Instruction user) {
        if (users != null) users.remove(user);
    }

    public void updateUse(Entity newUse) {
        if (users != null) users.forEach(user -> user.replaceUse(this, newUse));
    }
}
