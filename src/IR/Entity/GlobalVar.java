package IR.Entity;

import IR.Type.IRType;
import IR.Type.PtrType;

public class GlobalVar extends Entity {
    //global variable, add @ before name automatically
    public String name;

    public GlobalVar(String name, IRType type) {
        super(type); //notice: all global variables should be pointers
        this.name = name;
    }

    @Override
    public String getText() {
        return "@" + name;
    }

    @Override
    public boolean isConstant() {
        return isStringLiteral();
    }

    private static long anonymous_cnt = 0;

    public static GlobalVar anonymousSrcStr() { //source string literal
        return new GlobalVar(".str." + anonymous_cnt++, PtrType.IRStringLiteral);
    }

    public boolean isStringLiteral() {
        return type == PtrType.IRStringLiteral;
    }
}
