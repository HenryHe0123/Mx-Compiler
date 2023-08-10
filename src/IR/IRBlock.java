package IR;

import IR.Instruction.*;
import IR.Instruction.Terminal.Terminator;

import java.util.LinkedList;

public class IRBlock { //Basic Block
    public String label;
    public LinkedList<Instruction> instructions = new LinkedList<>();
    public Terminator terminator;
    public FunctionDef inFunction;

    public IRBlock(String label) {
        this.label = label;
    }

    public IRBlock(String label, FunctionDef inFunction) {
        this.label = label;
        this.inFunction = inFunction;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public void addInstruct(Instruction instruction) {
        instructions.add(instruction);
    }

    public String getText() {
        StringBuilder text = new StringBuilder(label + ":\n");
        for (Instruction instruction : instructions) {
            text.append("  ");
            text.append(instruction.getText());
        }
        if (terminator != null) {
            text.append("  ");
            text.append(terminator.getText());
        }
        return text.toString();
    }
}
