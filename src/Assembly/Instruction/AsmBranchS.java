package Assembly.Instruction;

import Assembly.Operand.*;

public class AsmBranchS extends Inst {
    public String op; // bnez, beqz
    public Operand cond;
    public String toLabel;

    public AsmBranchS(String op, Operand cond, String toLabel) {
        this.op = op;
        this.cond = cond;
        this.toLabel = toLabel;
    }

    @Override
    public String getText() {
        return op + "\t" + cond.getText() + ", " + toLabel;
    }

    @Override
    public void getDefUse() {
        if (cond instanceof Reg r) use.add(r);
    }
}
