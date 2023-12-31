package Optimizer.Asm;

import Assembly.AsmBlock;
import Assembly.AsmRoot;
import Assembly.Instruction.*;
import Assembly.Operand.Imm;
import Assembly.Operand.PhyReg;

import java.util.Stack;

import static Assembly.Operand.PhyReg.*;

public class PeepholeOptimizer {
    public static void pass(AsmRoot root) {
        root.functions.forEach(function ->
                function.blocks.forEach(PeepholeOptimizer::promote)
        );
    }

    private static void promote(AsmBlock block) {
        Stack<AsmMemoryS> callerLw = new Stack<>();

        Inst inst = block.headInst;
        while (inst != null) {
            if (inst instanceof AsmMv mv) { //remove useless move
                if (mv.useless()) block.remove(mv);
                else {
                    if (mv.next instanceof AsmMv mv2 && mv.rd == mv2.rs) {
                        if (mv2.rd == mv.rs)
                            block.remove(mv2); //b<-a, a<-b
                        else if (mv2.next instanceof AsmMv mv3 && mv2.rd == mv3.rs && mv3.rd == mv.rs)
                            block.remove(mv3); //b<-a, c<-b, a<-c
                    }
                    if (inst.usedCallerRegs()) callerLw.clear();
                }
            } else if (inst instanceof AsmBinaryS calc) {
                if (calc.op.equals("addi") && calc.rd == calc.rs1 && calc.rs2 instanceof Imm imm && imm.val == 0) {
                    block.remove(calc);
                } else {
                    if (calc.op.equals("addi") && calc.next instanceof AsmBinaryS calc2 && calc2.op.equals("addi") &&
                            calc.rd == calc.rs1 && calc2.rd == calc.rd && calc2.rd == calc2.rs1 &&
                            calc.rs2 instanceof Imm imm1 && calc2.rs2 instanceof Imm imm2) {
                        int i = imm1.val + imm2.val;
                        if (i < 2048 && i >= -2048) {
                            calc.rs2 = new Imm(imm1.val + imm2.val);
                            block.remove(calc2);
                            continue;
                        }
                    }
                    if (inst.usedCallerRegs()) callerLw.clear();
                }
            } else if (inst instanceof AsmMemoryS mem) { //important
                if (mem.isCallerSave && mem.rd instanceof PhyReg reg && mem.rs == fp) { //for caller-save
                    //remove redundant sw/lw caused by continuous call
                    if (!usedCallerRegs.contains(reg)) block.remove(mem);
                    else if (mem.isLoad()) {
                        callerLw.push(mem);
                    } else if (!callerLw.empty()) { //mem is caller-save "sw"
                        AsmMemoryS load = callerLw.pop();
                        if (mem.rd == load.rd && mem.offset == load.offset) {
                            block.remove(load);
                            block.remove(mem);
                        } else { //not paired
                            callerLw.clear();
                        }
                    }
                } else { //other memory instructions
                    if (mem.next instanceof AsmMemoryS mem2 && mem.pairedWith(mem2)) {
                        block.remove(mem2);
                        continue;
                    }
                    if (inst.usedCallerRegs()) callerLw.clear();
                }
            }
            if (inst.usedCallerRegs()) callerLw.clear();
            inst = inst.next;
        }
    }
}
