package IR.Entity;

import IR.Type.INType;

public class Bool extends Entity {
    private final boolean value;

    public Bool(boolean bool) {
        super(INType.IRBool);
        value = bool;
    }

    public boolean getVal() {
        return value;
    }

    @Override
    public boolean isStrictlyConstant() {
        return true;
    }

    @Override
    public String getText() {
        return String.valueOf(value);
    }

    public static final Bool True = new Bool(true);
    public static final Bool False = new Bool(false);
}
