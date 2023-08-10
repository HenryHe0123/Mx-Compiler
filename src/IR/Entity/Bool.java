package IR.Entity;

import IR.Type.INType;

public class Bool extends Entity {
    public boolean value;

    public Bool(boolean bool) {
        super(INType.IRBool);
        value = bool;
    }

    @Override
    public String getText() {
        return String.valueOf(value);
    }
}
