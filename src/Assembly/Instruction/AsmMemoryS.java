package Assembly.Instruction;

import Assembly.Operand.Reg;
import Assembly.Operand.PhyReg;

public class AsmMemoryS extends Inst {
    public String op; // lw, sw
    public Reg rd, rs;
    public int offset;
    public boolean isCallerSave = false;

    public AsmMemoryS(String op, Reg rd, Reg rs, int offset) {
        this.op = op;
        this.rd = rd;
        this.rs = rs;
        this.offset = offset;
    }

    public AsmMemoryS(String op, PhyReg rd, PhyReg rs, int offset, boolean isCallerSave) {
        this.op = op;
        this.rd = rd; //reg
        this.rs = rs; //fp
        this.offset = offset;
        this.isCallerSave = isCallerSave;
    }

    @Override
    public String getText() {
        return op + "\t" + rd.getText() + ", " + offset + "(" + rs.getText() + ")";
    }

    @Override
    public void getDefUse() {
        use.add(rs);
        if (isStore()) use.add(rd);
        else def.add(rd);
    }

    public boolean isLoad() {
        return op.equals("lw");
    }

    public boolean isStore() {
        return op.equals("sw");
    }

    public boolean pairedWith(AsmMemoryS mem) {
        if (mem == null) return false;
        return mem.rd == rd && mem.rs == rs && mem.offset == offset && !mem.op.equals(op);
    }
}
