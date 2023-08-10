package IR.Type;

public class VoidType extends IRType {
    @Override
    public String getText() {
        return "void";
    }

    @Override
    public int getBytes() {
        return 0;
    }

    public static final VoidType IRVoid = new VoidType();
}
