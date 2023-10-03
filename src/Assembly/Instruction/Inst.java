package Assembly.Instruction;

import Assembly.Operand.PhyReg;
import Assembly.Operand.Reg;

import java.util.LinkedList;

import static Assembly.Operand.PhyReg.usedCallerRegs;

public abstract class Inst {
    public Inst prev = null, next = null;
    public LinkedList<Reg> def = new LinkedList<>(), use = new LinkedList<>();

    public String getText() {
        return null;
    }

    public void getDefUse() {
    }

    public boolean usedCallerRegs() {
        def.clear();
        use.clear();
        getDefUse();
        for (var r : def)
            if (r instanceof PhyReg reg && usedCallerRegs.contains(reg)) return true;
        for (var r : use)
            if (r instanceof PhyReg reg && usedCallerRegs.contains(reg)) return true;
        return false;
    }
}
