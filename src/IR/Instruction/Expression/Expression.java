package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.Instruction.Instruction;

public abstract class Expression extends Instruction {
    public Entity dest; //assign destination, usually register/global variable

    public Expression(Entity dest) {
        this.dest = dest;
    }

    public int getBytes() {
        return dest.type.getBytes();
    }
}
