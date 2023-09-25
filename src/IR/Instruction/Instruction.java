package IR.Instruction;

import IR.Entity.Entity;
import IR.Entity.Register;
import IR.IRVisitor;

import java.util.LinkedList;

public abstract class Instruction {
    public abstract String getText(); //include '\n'

    public abstract void accept(IRVisitor visitor);

    public void replaceUse(Entity old, Entity latest) {
    }

    public LinkedList<Register> useList() {
        return new LinkedList<>();
    }
}
