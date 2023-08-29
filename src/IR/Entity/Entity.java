package IR.Entity;

import IR.Type.*;
import Util.*;

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
}
