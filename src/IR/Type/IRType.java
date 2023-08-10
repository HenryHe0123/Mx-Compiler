package IR.Type;

import Util.Type;

public abstract class IRType {
    public abstract String getText();

    public abstract int getBytes();

    public static IRType from(Type type) {
        IRType t = switch (type.typename) {
            case "int" -> INType.IRInt;
            case "bool" -> INType.IRBool;
            case "string" -> PtrType.IRString;
            case "void" -> VoidType.IRVoid;
            default -> new PtrType(); //void*
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
}
