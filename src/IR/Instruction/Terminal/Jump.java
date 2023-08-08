package IR.Instruction.Terminal;

import IR.*;

public class Jump extends Terminator {
    public IRBlock target;

    public Jump(IRBlock target) {
        this.target = target;
    }

    @Override
    public String getText() {
        return "br label %" + target.label;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
