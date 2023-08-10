package IR.Instruction;

import IR.Entity.Entity;
import IR.IRVisitor;

public class GlobalDef extends Instruction {
    public enum globalDefType {
        global, constant
    }

    public globalDefType dType;
    public Entity dest, init;

    public GlobalDef(Entity dest, Entity init, globalDefType dType) {
        this.dType = dType;
        this.dest = dest;
        this.init = init;
    }

    @Override
    public String getText() {
        return dest.getText() + " = " + dType.name() + " " + init.getFullText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
