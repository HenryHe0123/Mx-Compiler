package Assembly.Instruction;

import Assembly.Operand.Operand;

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
}
