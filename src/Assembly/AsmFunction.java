package Assembly;

import Assembly.Instruction.*;
import Assembly.Operand.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import static Assembly.Operand.PhyReg.*;

public class AsmFunction {
    public String name;
    public LinkedList<AsmBlock> blocks = new LinkedList<>();
    public AsmBlock retBlock = null;
    public int offset = 8, paraOffset = 0; //reserve offset for sp & fp
    public HashMap<Reg, Integer> stack = new HashMap<>(); //stack (offset) for tmp variables

    public AsmFunction(String funcName) {
        name = funcName;
    }

    public void addBlock(AsmBlock block) {
        blocks.add(block);
    }

    public AsmBlock entryBlock() {
        return blocks.getFirst();
    }

    public void finish() {
        int totalOffset = offset + paraOffset;
        int spOffset = (totalOffset % 16 != 0) ? (((totalOffset >> 4) + 1) << 4) : totalOffset; //positive
        //debug: return block may not be the last block
        AsmBlock entry = entryBlock();
        Inst terminal = retBlock.tailInst;

        if (spOffset > 2048) {
            var t0 = t(0); //t0 = imm(spOffset)
            var t1 = t(1); //t1 = previous sp
            entry.add_front(new AsmBinaryS("add", fp, sp, t0));
            entry.add_front(new AsmMemoryS("sw", fp, t1, -8));
            entry.add_front(new AsmMemoryS("sw", ra, t1, -4));
            entry.add_front(new AsmBinaryS("add", t1, sp, t0));
            entry.add_front(new AsmBinaryS("sub", sp, sp, t0));
            entry.add_front(new AsmLi(t0, new Imm(spOffset)));

            retBlock.insert_before(terminal, new AsmLi(t0, new Imm(spOffset)));
            retBlock.insert_before(terminal, new AsmBinaryS("add", t1, sp, t0));
            retBlock.insert_before(terminal, new AsmMemoryS("lw", fp, t1, -8));
            retBlock.insert_before(terminal, new AsmMemoryS("lw", ra, t1, -4));
            retBlock.insert_before(terminal, new AsmBinaryS("add", sp, sp, t0));
        } else {
            //use imm
            entry.add_front(new AsmBinaryS("addi", fp, sp, new Imm(spOffset))); //fp = previous sp
            entry.add_front(new AsmMemoryS("sw", fp, sp, spOffset - 8)); //store previous fp
            entry.add_front(new AsmMemoryS("sw", ra, sp, spOffset - 4)); //store previous ra
            entry.add_front(new AsmBinaryS("addi", sp, sp, new Imm(-spOffset))); //move sp

            retBlock.insert_before(terminal, new AsmMemoryS("lw", fp, sp, spOffset - 8));
            retBlock.insert_before(terminal, new AsmMemoryS("lw", ra, sp, spOffset - 4));
            retBlock.insert_before(terminal, new AsmBinaryS("addi", sp, sp, new Imm(spOffset)));
        }
    }

    public String getText() {
        StringBuilder text = new StringBuilder("\t.globl  " + name + "\n");
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

    //----------------------------------------------------------------------

    public void getDefUse() {
        blocks.forEach(AsmBlock::getDefUse);
    }

    public HashSet<PhyReg> usedCalleeRegs = new HashSet<>();

    public void saveCallee() {
        if (name.equals("main")) {
            if (useTp && usedCalleeRegs.contains(tp)) {
                //tp should be handled specifically, though useless for ravel
                entryBlock().add_front(new AsmMemoryS("sw", tp, fp, -(offset += 4)));
                var inst = retBlock.tailInst;
                retBlock.insert_before(inst, new AsmMemoryS("lw", tp, fp, -offset));
            }
            return;
        }
        var entry = entryBlock();
        var map = new HashMap<PhyReg, Integer>();
        for (var reg : usedCalleeRegs) {
            entry.add_front(new AsmMemoryS("sw", reg, fp, -(offset += 4)));
            map.put(reg, offset);
        }
        var inst = retBlock.tailInst;
        for (var reg : usedCalleeRegs) {
            retBlock.insert_before(inst, new AsmMemoryS("lw", reg, fp, -map.get(reg)));
        }
    }
}


