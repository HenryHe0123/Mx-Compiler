package Assembly.Instruction;

import Assembly.Operand.Reg;

public class AsmLa extends Inst {
    public Reg rd;
    public String name;

    public AsmLa(Reg rd, String name) {
        this.rd = rd;
        this.name = name;
    }

    @Override
    public String getText() {
        return "la\t" + rd.getText() + ", " + name;
    }

    @Override
    public void getDefUse() {
        def.add(rd);
    }
}
