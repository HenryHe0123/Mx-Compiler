package IR.Instruction.Terminal;

import IR.*;

public class Branch extends Terminator {
    public IRBlock branchTrue, branchFalse;

    public Branch(IRBlock branchTrue, IRBlock branchFalse) {
        this.branchTrue = branchTrue;
        this.branchFalse = branchFalse;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
