package IR.Entity;

import IR.Type.IRType;
import IR.Type.PtrType;

public class Null extends Entity {
    private Null() {
        super(PtrType.IRNull);
    }

    @Override
    public String getText() {
        return "null";
    }

    @Override
    public boolean isStrictlyConstant() {
        return true;
    }

    public static final Null instance = new Null();
}
