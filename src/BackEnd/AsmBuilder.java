package BackEnd;

import Assembly.*;
import Assembly.Instruction.*;
import Assembly.Operand.*;
import IR.Entity.*;
import IR.*;
import IR.Instruction.*;
import IR.Instruction.Expression.*;
import IR.Instruction.Terminal.*;

import java.util.HashMap;

public class AsmBuilder implements IRVisitor {
    public final AsmModule module;
    private AsmFunction curFunction;
    private AsmBlock curBlock;
    private final HashMap<Entity, Reg> regMap = new HashMap<>();
    private final HashMap<IRBlock, AsmBlock> blockMap = new HashMap<>();

    public AsmBuilder(AsmModule asmModule) {
        module = asmModule;
    }

    //-------------------------------------- utility ----------------------------------------------

    private void addInst(Inst ins) {
        curBlock.push_back(ins);
    }

    private Reg getReg(Entity entity) {
        if (entity instanceof Int) {
            int val = ((Int) entity).toInt();
            if (val == 0) return PhyReg.zero;
            Reg rg = new VirReg();
            addInst(new AsmLi(rg, new Imm(val)));
            return rg;
        } else if (entity instanceof Bool) {
            boolean val = ((Bool) entity).toBool();
            if (!val) return PhyReg.zero;
            Reg rg = new VirReg();
            addInst(new AsmLi(rg, Imm.one));
            return rg;
        }
        return regMap.get(entity);
    }

    private VirReg createVirReg(Entity entity) {
        VirReg rg = new VirReg();
        regMap.put(entity, rg);
        return rg;
    }

    private void collectBlock(IRBlock block) {
        String label = block.label.equals("entry") ? curFunction.name : block.label + curFunction.id;
        var asmBlock = new AsmBlock(label);
        blockMap.put(block, asmBlock);
        curFunction.addBlock(asmBlock);
    }

    //--------------------------------------- visit ------------------------------------------------

    @Override
    public void visit(IRRoot it) {
        it.globals.forEach(global -> global.accept(this));
        it.functions.forEach(func -> func.accept(this));
    }

    @Override
    public void visit(IRFunction it) {
        curFunction = new AsmFunction(it.name);
        module.addFunction(curFunction);

        // map para Entity to Reg
        for (int i = 0; i < Integer.min(8, it.parameters.size()); ++i) {
            Entity para = it.parameters.get(i);
            var rd = createVirReg(para);
            addInst(new AsmMv(rd, PhyReg.a(i))); //move ai to rd
        }

        for (int i = 8; i < it.parameters.size(); ++i) {
            Entity para = it.parameters.get(i);
            var rd = createVirReg(para);
            addInst(new AsmMemoryS("lw", rd, PhyReg.s(0), (i - 8) << 2));
        }

        // collect blocks
        blockMap.clear();
        collectBlock(it.entry);
        it.blocks.forEach(this::collectBlock);
        collectBlock(it.returnBlock);

        // visit blocks
        it.entry.accept(this);
        it.blocks.forEach(block -> block.accept(this));
        it.returnBlock.accept(this);
    }

    @Override
    public void visit(IRBlock it) {
        curBlock = blockMap.get(it);
        it.instructions.forEach(ins -> ins.accept(this));
        it.terminator.accept(this);
    }

    @Override
    public void visit(Binary it) {
        VirReg rd = createVirReg(it.dest);
        var inst = switch (it.op) {
            case add -> new AsmBinaryS("add", rd, getReg(it.src1), getReg(it.src2));
            case sub -> new AsmBinaryS("sub", rd, getReg(it.src1), getReg(it.src2));
            case mul -> new AsmBinaryS("mul", rd, getReg(it.src1), getReg(it.src2));
            case sdiv -> new AsmBinaryS("div", rd, getReg(it.src1), getReg(it.src2));
            case srem -> new AsmBinaryS("rem", rd, getReg(it.src1), getReg(it.src2));
            case and -> new AsmBinaryS("and", rd, getReg(it.src1), getReg(it.src2));
            case or -> new AsmBinaryS("or", rd, getReg(it.src1), getReg(it.src2));
            case xor -> new AsmBinaryS("xor", rd, getReg(it.src1), getReg(it.src2));
            case shl -> new AsmBinaryS("sll", rd, getReg(it.src1), getReg(it.src2));
            case ashr -> new AsmBinaryS("sra", rd, getReg(it.src1), getReg(it.src2));
        };
        addInst(inst);
    }

    @Override
    public void visit(Icmp it) {
        var rd = createVirReg(it.dest);
        var rs1 = getReg(it.src1);
        var rs2 = getReg(it.src2);
        switch (it.op) {
            case eq -> {
                var tmp = new VirReg();
                addInst(new AsmBinaryS("xor", tmp, rs1, rs2));
                //if rs1 == rs2, tmp == rs1 ^ rs2 == 0
                addInst(new AsmSetCmpS("seqz", rd, tmp));
            }
            case ne -> {
                var tmp = new VirReg();
                addInst(new AsmBinaryS("xor", tmp, rs1, rs2));
                addInst(new AsmSetCmpS("snez", rd, tmp));
            }
            case slt -> addInst(new AsmBinaryS("slt", rd, rs1, rs2));
            case sgt -> addInst(new AsmBinaryS("slt", rd, rs2, rs1));
            //no sgt in risc-v assembly
            case sle -> {
                addInst(new AsmBinaryS("slt", rd, rs2, rs1));
                addInst(new AsmBinaryS("xori", rd, rd, new Imm(1)));
                //rd = rd ^ 1 (rd: 0 -> 1 / 1 -> 0)
            }
            case sge -> {
                addInst(new AsmBinaryS("slt", rd, rs1, rs2));
                addInst(new AsmBinaryS("xori", rd, rd, new Imm(1)));
            }
        }
    }

    @Override
    public void visit(Branch it) {
        // br cond bTrue bFalse -> beqz cond bFalse; j bTrue
        addInst(new AsmBranchS("beqz", getReg(it.cond), blockMap.get(it.branchFalse).label));
        addInst(new AsmJ(blockMap.get(it.branchTrue).label));
    }

}
