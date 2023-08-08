package IR;

import IR.Instruction.Terminal.Terminator;

import java.util.LinkedList;

public class IRBlock { //Basic Block
    public String label;
    public LinkedList<IRInstruction> instructions = new LinkedList<>();

    public Terminator terminator;

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

}
