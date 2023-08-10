package IR.Entity;

import IR.Type.IRType;

public class Register extends Entity {
    private static long anonymous_cnt = 0;
    //virtual register, add % before name automatically
    public String name;

    public Register(String name, IRType type) {
        super(type);
        this.name = name;
    }

    public Register(String name) {
        super(null);
        this.name = name;
    }

    @Override
    public String getText() {
        return "%" + name;
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    public static Register anonymous() {
        return new Register("_" + anonymous_cnt++);
    }

    public static Register anonymous(IRType type) {
        return new Register("_" + anonymous_cnt++, type);
    }
}
