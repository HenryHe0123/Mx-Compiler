package Assembly.Instruction;

import Assembly.Operand.*;

public class AsmLi extends Inst {
    public Reg rd;
    public Imm imm;

    public AsmLi(Reg rd, Imm imm) {
        this.rd = rd;
        this.imm = imm;
    }

    @Override
    public String getText() {
        return "li\t" + rd.getText() + ", " + imm.getText();
    }

    @Override
    public void getDefUse() {
        def.add(rd);
    }
}
