package Assembly.Instruction;

import Assembly.Operand.Operand;
import Assembly.Operand.Reg;

public class AsmSetCmpS extends Inst {
    public String op; // seqz, snez (if rs != 0, rd = 1; else rd = 0)
    public Operand rd, rs;

    public AsmSetCmpS(String op, Operand rd, Operand rs) {
        this.op = op;
        this.rd = rd;
        this.rs = rs;
    }

    @Override
    public String getText() {
        return op + "\t" + rd.getText() + ", " + rs.getText();
    }

    @Override
    public void getDefUse() {
        if (rd instanceof Reg r) def.add(r);
        if (rs instanceof Reg r) use.add(r);
    }
}
