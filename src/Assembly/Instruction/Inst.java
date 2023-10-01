package Assembly.Instruction;

import Assembly.Operand.Reg;

import java.util.LinkedList;

public abstract class Inst {
    public Inst prev = null, next = null;
    public LinkedList<Reg> def = new LinkedList<>(), use = new LinkedList<>();

    public String getText() {
        return null;
    }

    public void getDefUse() {
    }
}
