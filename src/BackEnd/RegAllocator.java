package BackEnd;

import Assembly.*;
import Assembly.Instruction.*;
import Assembly.Operand.*;

import static Assembly.Operand.PhyReg.*;

public class RegAllocator {
    private AsmFunction curFunction;
    private AsmBlock curBlock;
    private final GraphColoring graphColoring = new GraphColoring();

    private Operand pushOnStack(Inst ins, Operand operand, PhyReg tmp, boolean isLoad) {
        if (operand instanceof VirReg v) {
            if (!curFunction.containsReg(v)) curFunction.allocate(v);
            int offset = curFunction.getVarRegOffset(v);
            if (-2048 <= offset && offset < 2048) {
                if (isLoad) curBlock.insert_before(ins, new AsmMemoryS("lw", tmp, fp, offset));
                else curBlock.insert_after(ins, new AsmMemoryS("sw", tmp, fp, offset));
            } else {
                PhyReg tfp = ra; //tmp fp (use ra temporarily)
                if (isLoad) {
                    curBlock.insert_before(ins, new AsmLi(tfp, new Imm(offset)));
                    curBlock.insert_before(ins, new AsmBinaryS("add", tfp, fp, tfp));
                    curBlock.insert_before(ins, new AsmMemoryS("lw", tmp, tfp, 0));
                } else {
                    curBlock.insert_after(ins, new AsmMemoryS("sw", tmp, tfp, 0));
                    curBlock.insert_after(ins, new AsmBinaryS("add", tfp, fp, tfp));
                    curBlock.insert_after(ins, new AsmLi(tfp, new Imm(offset)));
                }
            }
            return tmp;
        } else return operand;
    }

    public void visit(AsmRoot it) {
        graphColoring.visit(it);
        it.functions.forEach(this::visit);
    }

    private void visit(AsmFunction it) {
        curFunction = it;
        it.blocks.forEach(this::visit);
        it.saveCallee();
        it.finish();
    }

    private void visit(AsmBlock it) {
        curBlock = it;
        for (Inst ins = it.headInst; ins != it.tailInst; ins = ins.next) visit(ins);
    }

    private void visit(Inst it) {
        if (it instanceof AsmBinaryS ins) visit(ins);
        else if (it instanceof AsmBranchS ins) visit(ins);
        else if (it instanceof AsmLi ins) visit(ins);
        else if (it instanceof AsmLa ins) visit(ins);
        else if (it instanceof AsmMemoryS ins) visit(ins);
        else if (it instanceof AsmMv ins) visit(ins);
        else if (it instanceof AsmSetCmpS ins) visit(ins);
    }

    private void visit(AsmBinaryS it) {
        it.rs1 = pushOnStack(it, it.rs1, t(0), true);
        it.rs2 = pushOnStack(it, it.rs2, a(1), true);
        it.rd = pushOnStack(it, it.rd, a(2), false);
        //we can use a1 & a2 temporarily for those call-free instructions
    }

    private void visit(AsmBranchS it) {
        it.cond = pushOnStack(it, it.cond, t(0), true);
    }

    private void visit(AsmLi it) {
        it.rd = (Reg) pushOnStack(it, it.rd, t(0), false);
    }

    private void visit(AsmLa it) {
        it.rd = (Reg) pushOnStack(it, it.rd, t(0), false);
    }

    private void visit(AsmMemoryS it) {
        if (it.op.equals("sw")) { //store rd to rs + offset
            if (it.rs instanceof VirReg) {
                it.rs = (Reg) pushOnStack(it, it.rs, t(0), true);
                it.rd = (Reg) pushOnStack(it, it.rd, a(1), true);
            } else { //limit the use of t-reg
                it.rd = (Reg) pushOnStack(it, it.rd, t(0), true);
            }
        } else { //load rd from rs + offset
            if (it.rs instanceof VirReg) {
                it.rs = (Reg) pushOnStack(it, it.rs, t(0), true);
                it.rd = (Reg) pushOnStack(it, it.rd, a(1), false);
            } else { //limit the use of t-reg
                it.rd = (Reg) pushOnStack(it, it.rd, t(0), false);
            }
        }
    }

    private void visit(AsmMv it) {
        //move rs to rd
        if (it.rd instanceof VirReg) {
            it.rd = (Reg) pushOnStack(it, it.rd, t(0), false);
            it.rs = (Reg) pushOnStack(it, it.rs, a(1), true);
        } else { //limit the use of t-reg
            it.rs = (Reg) pushOnStack(it, it.rs, t(0), true);
        }
    }

    private void visit(AsmSetCmpS it) {
        it.rd = pushOnStack(it, it.rd, t(0), false);
        it.rs = pushOnStack(it, it.rs, a(1), true);
    }
}
