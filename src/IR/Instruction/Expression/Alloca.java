package IR.Instruction.Expression;

import IR.IRInstruction;
import IR.IRVisitor;

public class Alloca extends IRInstruction {

    @Override
    public String getText() {
        return null;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
