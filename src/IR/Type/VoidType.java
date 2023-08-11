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

    @Override
    public boolean isVoid() {
        return true;
    }

    public static final VoidType IRVoid = new VoidType();
}
