package IR.Entity;

import IR.Type.IRType;
import IR.Type.PtrType;

public class GlobalVar extends Entity {
    //global variable, add @ before name automatically
    public String name;

    public GlobalVar(String name, IRType type) {
        super(new PtrType(type)); //all global variables are pointers
        this.name = name;
    }

    @Override
    public String getText() {
        return "@" + name;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
}
