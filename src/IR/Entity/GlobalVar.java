package IR.Entity;

import IR.Type.IRType;
import IR.Type.PtrType;

import java.util.LinkedList;

public class GlobalVar extends Entity {
    //global variable, add @ before name automatically
    public String name;
    //int or bool or null and no need to call global_var_init, for global to local
    public boolean isSimple = false;

    public GlobalVar(String name, IRType type) {
        super(type); //notice: all global variables should be pointers
        this.name = name;
        users = new LinkedList<>();
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
