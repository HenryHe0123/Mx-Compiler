package IR.Type;

import Util.Type;

import java.util.HashMap;

public abstract class IRType {
    public abstract String getText();

    public abstract int getBytes();

    private static final HashMap<String, ClassType> classTypes = new HashMap<>();

    public static void addIRClassType(String className) {
        classTypes.put(className, new ClassType(className));
    }

    public static ClassType getIRClassType(String className) {
        return classTypes.get(className);
    }

    public static IRType from(Type type) {
        IRType t = switch (type.typename) {
            case "int" -> INType.IRInt;
            case "bool" -> INType.IRBool;
            case "string" -> PtrType.IRString;
            case "void" -> VoidType.IRVoid;
            case "null" -> PtrType.IRNull;
            default -> new PtrType(getIRClassType(type.typename));
        };
        return t.dimension(type.dim);
    }

    public IRType dimension(int dim) {
        IRType t = this;
        for (int i = 0; i < dim; ++i) {
            t = new PtrType(t);
        }
        return t;
    }

    public IRType deconstruct() {
        return null;
    }

    public boolean isVoid() {
        return false;
    }

    public PtrType asPtr() {
        return new PtrType(this);
    }

    public String asPrefix() {
        return "";
    }
}
