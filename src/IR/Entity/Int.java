package IR.Entity;

import IR.Type.INType;

public class Int extends Entity {
    private final int value;

    public Int(int i) {
        super(INType.IRInt);
        value = i;
    }

    public int toInt() {
        return value;
    }

    @Override
    public String getText() {
        return String.valueOf(value);
    }

    public static final Int four = new Int(4);
    public static final Int zero = new Int(0);
    public static final Int one = new Int(1);
    public static final Int minusOne = new Int(-1);

}
