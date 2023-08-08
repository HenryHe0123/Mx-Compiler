package IR.Instruction.Terminal;

import IR.IRVisitor;

public class Ret extends Terminator {

    @Override
    public String getText() {
        return null;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
