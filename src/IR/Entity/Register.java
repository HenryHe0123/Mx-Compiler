package IR.Entity;

import IR.Type.IRType;

import java.util.ArrayList;

public class Register extends Entity {
    private static long anonymous_cnt = 0;
    //virtual register, add % before name automatically
    public String name;

    public Register(String name, IRType type) {
        super(type);
        this.name = name;
        users = new ArrayList<>();
    }

    @Override
    public String getText() {
        return "%" + name;
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    public static Register anonymous(IRType type) {
        return new Register("_" + anonymous_cnt++, type);
    }

    public static Register retReg(IRType type) {
        if (type.isVoid()) return null;
        return new Register("_ret_val", type.asPtr());
    }

    private static long rename_id = 0;

    public Register renameAndDeconstruct() {
        return new Register(name + ".reachingDef_" + rename_id++, type.deconstruct());
    }
}
