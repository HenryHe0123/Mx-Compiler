package IR.Type;

public class PtrType extends IRType {
    public IRType baseType;

    public PtrType() {
        baseType = VoidType.IRVoid;
    }

    public PtrType(IRType baseType) {
        this.baseType = baseType;
    }

    @Override
    public String getText() {
        return "ptr";
    }

    @Override
    public int getBytes() {
        return 4;
    }

    @Override
    public IRType deconstruct() {
        return baseType;
    }
    public static final PtrType IRNull = new PtrType(null);
}
