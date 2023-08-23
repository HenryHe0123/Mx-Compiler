package Assembly;

import Assembly.Instruction.*;
import Assembly.Operand.*;

import java.util.HashMap;
import java.util.LinkedList;

import static Assembly.Operand.PhyReg.*;

public class AsmFunction {
    public String name;
    public LinkedList<AsmBlock> blocks = new LinkedList<>();
    public int offset = 0, paraOffset = 0;
    public HashMap<Reg, Integer> stack = new HashMap<>(); //stack (offset) for tmp variables

    public AsmFunction(String funcName) {
        name = funcName;
    }

    public void addBlock(AsmBlock block) {
        blocks.add(block);
    }

    public void insertBlock(AsmBlock block, AsmBlock insertedBlock) {
        blocks.add(blocks.indexOf(block), insertedBlock);
    }

    public void finish() {
        int totalOffset = offset + paraOffset;
        if (totalOffset % 16 != 0) totalOffset = (totalOffset / 16 + 1) * 16;

        AsmBlock lastBlock = blocks.get(blocks.size() - 1);
        AsmBlock entry = blocks.get(0);
        Inst terminal = lastBlock.tailInst;

        var t0 = t(0);
        var t1 = t(1);

        entry.add_front(new AsmLi(a(0), new Imm(0))); //a0 = 0
        entry.add_front(new AsmBinaryS("add", fp, sp, t0)); //fp = previous sp
        entry.add_front(new AsmMemoryS("sw", fp, t1, -8)); //store previous fp
        entry.add_front(new AsmMemoryS("sw", ra, t1, -4));
        entry.add_front(new AsmBinaryS("add", t1, sp, t0)); //t1 = previous sp
        entry.add_front(new AsmBinaryS("sub", sp, sp, t0));
        entry.add_front(new AsmLi(t0, new Imm(totalOffset))); //t0 = totalOffset

        lastBlock.insert_before(terminal, new AsmLi(t0, new Imm(totalOffset)));
        lastBlock.insert_before(terminal, new AsmBinaryS("add", t1, sp, t0)); //t1 = previous sp
        lastBlock.insert_before(terminal, new AsmMemoryS("lw", fp, t1, -8));
        lastBlock.insert_before(terminal, new AsmMemoryS("lw", ra, t1, -4));
        lastBlock.insert_before(terminal, new AsmBinaryS("add", sp, sp, t0));
    }

    public String getText() {
        StringBuilder text = new StringBuilder("\t.globl  " + name + "\n");
        text.append("\t.type   ").append(name).append(",@function\n");
        //text.append(name).append(":\n");
        for (AsmBlock block : blocks) {
            text.append(block.getText());
        }
        return text.toString();
    }

    public void allocate(Reg rg) {
        stack.put(rg, offset += 4);
    }

    public int getVarRegOffset(Reg rg) {
        return -stack.get(rg);
    }

    public boolean containsReg(Reg rg) {
        return stack.containsKey(rg);
    }

    private static int cnt = 0;
    public final int id = cnt++;
}


