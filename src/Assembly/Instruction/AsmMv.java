package Assembly.Instruction;

import Assembly.Operand.Reg;

public class AsmMv extends Inst {
    public Reg rd, rs1;

    public AsmMv(Reg rd, Reg rs1) {
        this.rd = rd;
        this.rs1 = rs1;
    }

    @Override
    public String getText() {
        return "mv\t" + rd.getText() + ", " + rs1.getText();
    }
}
