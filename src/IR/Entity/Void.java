package IR.Entity;

import IR.Type.VoidType;

public class Void extends Entity {
    public Void() {
        super(VoidType.IRVoid);
    }

    @Override
    public String getText() {
        return "void";
    }

    @Override
    public String getFullText() {
        return "void";
    }

    public static final Void instance = new Void();
}
