package IR.Type;

public class INType extends IRType {
    public int bits;

    public INType(int n) {
        bits = n;
    }

    @Override
    public String getText() {
        return "i" + bits;
    }

    @Override
    public int getBytes() {
        return (bits - 1) / 8 + 1;
    }

    public static final INType IRInt = new INType(32);
    public static final INType IRBool = new INType(1);
}
