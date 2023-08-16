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

    @Override
    public String asPrefix() {
        return this == IRStringLiteral ? "__string." : "";
    }

    public static final PtrType IRNull = new PtrType(null);
    public static final PtrType IRStringLiteral = new PtrType(new INType(8));
    public static final PtrType IRString = IRStringLiteral.asPtr();
}
