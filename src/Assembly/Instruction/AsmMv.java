package Assembly.Instruction;

import Assembly.Operand.Reg;

public class AsmMv extends Inst {
    public Reg rd, rs;

    public AsmMv(Reg rd, Reg rs) {
        this.rd = rd;
        this.rs = rs;
    }

    @Override
    public String getText() {
        return "mv\t" + rd.getText() + ", " + rs.getText();
    }

    @Override
    public void getDefUse() {
        def.add(rd);
        use.add(rs);
    }

    public boolean useless() {
        return rd == rs;
    }
}
