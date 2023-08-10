package IR.Entity;

import IR.Type.INType;

public class Int extends Entity {
    public int value;

    public Int(int i) {
        super(INType.IRInt);
        value = i;
    }

    @Override
    public String getText() {
        return String.valueOf(value);
    }

}
