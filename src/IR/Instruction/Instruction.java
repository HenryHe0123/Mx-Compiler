package IR.Instruction;

import IR.IRVisitor;

public abstract class Instruction {
    public abstract String getText(); //include '\n'

    public abstract void accept(IRVisitor visitor);
}