package Assembly.Instruction;

import Assembly.Operand.Reg;

public class AsmMemoryS extends Inst {
    public String op; // lw, sw
    public Reg rd, rs;
    public int offset;

    public AsmMemoryS(String op, Reg rd, Reg rs, int offset) {
        this.op = op;
        this.rd = rd;
        this.rs = rs;
        this.offset = offset;
    }

    @Override
    public String getText() {
        return op + "\t" + rd.getText() + ", " + offset + "(" + rs.getText() + ")";
    }

    @Override
    public void getDefUse() {
        use.add(rs);
        if (op.equals("sw")) use.add(rd);
        else def.add(rd);
    }
}
