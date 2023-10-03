package BackEnd;

import Assembly.*;
import Assembly.Instruction.*;
import Assembly.Operand.*;
import IR.Entity.*;
import IR.*;
import IR.Entity.Void;
import IR.Instruction.*;
import IR.Instruction.Expression.*;
import IR.Instruction.Terminal.*;

import java.util.ArrayList;
import java.util.HashMap;

import static Assembly.Operand.PhyReg.*;

public class InstSelector implements IRVisitor {
    public final AsmRoot root;
    private AsmFunction curFunction;
    private AsmBlock curBlock;
    private final HashMap<Entity, Reg> regMap = new HashMap<>();
    private final HashMap<IRBlock, AsmBlock> blockMap = new HashMap<>();

    public InstSelector(AsmRoot root) {
        this.root = root;
    }

    //-------------------------------------- utility ----------------------------------------------

    private void addInst(Inst ins) {
        curBlock.push_back(ins);
    }

    private Reg getReg(Entity entity) {
        if (entity instanceof Register) {
            Reg rg = regMap.get(entity);
            if (rg == null) rg = createVirReg(entity);
            return rg;
        } else if (entity instanceof Int i) {
            int val = i.getVal();
            if (val == 0) return zero;
            Reg rg = new VirReg();
            addInst(new AsmLi(rg, new Imm(val)));
            return rg;
        } else if (entity instanceof Bool b) {
            boolean val = b.getVal();
            if (!val) return zero;
            Reg rg = new VirReg();
            addInst(new AsmLi(rg, Imm.one));
            return rg;
        } else if (entity instanceof GlobalVar g) {
            Reg rg = new VirReg();
            //debug: when visiting globalVar for a phi inst, we should not add load to curBlock
            curFunction.entryBlock().add_front(new AsmLa(rg, g.name));
            //addInst(new AsmLa(rg, g.name));
            return rg; //return address of global variable
        }
        return zero;
    }

    private int getConstantVal(Entity entity) {
        if (entity instanceof Int i) {
            return i.getVal();
        } else if (entity instanceof Bool b) {
            return b.getVal() ? 1 : 0;
        } else if (entity instanceof Null) {
            return 0;
        }
        return -19260817;
    }

    private VirReg createVirReg(Entity entity) {
        VirReg rg = new VirReg();
        regMap.put(entity, rg);
        return rg;
    }

    private void collectBlock(IRBlock block) {
        String label = block.label.equals("entry") ? curFunction.name : block.label + "." + curFunction.id;
        var asmBlock = new AsmBlock(label);
        blockMap.put(block, asmBlock);
        curFunction.addBlock(asmBlock);
    }

    private void addPhiInst(AsmFunction func) {
        ArrayList<AsmBlock> addedBlocks = new ArrayList<>();
        for (AsmBlock block : func.blocks) {
            var phi = block.phi;
            var tailInst = block.tailInst;
            if (phi.isEmpty()) continue;
            if (phi.size() == 1) { //debug: still maybe br and jump
                assert tailInst instanceof AsmJ;
                AsmJ jI = (AsmJ) tailInst;
                ArrayList<Inst> addedList = phi.get(jI.label);
                if (addedList == null) { //debug: still need to add empty block
                    AsmBranchS br = (AsmBranchS) tailInst.prev;
                    addedList = phi.get(br.toLabel);

                    AsmBlock brEmptyBlock = AsmBlock.newEmptyBlockForPhi();
                    addedList.forEach(brEmptyBlock::push_back);
                    brEmptyBlock.push_back(new AsmJ(br.toLabel));
                    br.toLabel = brEmptyBlock.label;
                    addedBlocks.add(brEmptyBlock);

                    //addedList.forEach(added -> block.insert_before(br, added));
                } else {
                    //debug: live block merge later, as it may cause come trouble in linking CFG
                    AsmBlock jEmptyBlock = AsmBlock.newEmptyBlockForPhi();
                    addedList.forEach(jEmptyBlock::push_back);
                    jEmptyBlock.push_back(new AsmJ(jI.label));
                    jI.label = jEmptyBlock.label;
                    addedBlocks.add(jEmptyBlock);

                    //addedList.forEach(added -> block.insert_before(tailInst, added));
                }
            } else {
                //last two inst is br and jump
                assert phi.size() == 2;
                assert tailInst instanceof AsmJ;
                AsmJ jI = (AsmJ) tailInst;

                Inst br = tailInst.prev;
                assert br instanceof AsmBranchS;
                AsmBranchS brI = (AsmBranchS) br;

                String brLabel = brI.toLabel;
                String jLabel = jI.label;
                ArrayList<Inst> brAddedList = phi.get(brLabel);
                ArrayList<Inst> jAddedList = phi.get(jLabel);

                assert brAddedList != null && jAddedList != null;

                AsmBlock brEmptyBlock = AsmBlock.newEmptyBlockForPhi();
                brAddedList.forEach(brEmptyBlock::push_back);
                brEmptyBlock.push_back(new AsmJ(brLabel));

                AsmBlock jEmptyBlock = AsmBlock.newEmptyBlockForPhi();
                jAddedList.forEach(jEmptyBlock::push_back);
                jEmptyBlock.push_back(new AsmJ(jLabel));

                brI.toLabel = brEmptyBlock.label;
                jI.label = jEmptyBlock.label;

                addedBlocks.add(brEmptyBlock);
                addedBlocks.add(jEmptyBlock);
            }
        }

        for (AsmBlock block : addedBlocks) {
            func.addBlock(block);
        }
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
        root.addFunction(curFunction);

        //collect all blocks
        blockMap.clear();
        collectBlock(it.entry);
        it.blocks.forEach(this::collectBlock);
        collectBlock(it.returnBlock);

        curBlock = blockMap.get(it.entry);

        //map parameter entity to virtual register
        for (int i = 0; i < Integer.min(paraRegsNum, it.parameters.size()); ++i) {
            Entity para = it.parameters.get(i);
            var rd = createVirReg(para);
            addInst(new AsmMv(rd, a(i))); //move ai to rd
        }

        for (int i = paraRegsNum; i < it.parameters.size(); ++i) {
            Entity para = it.parameters.get(i);
            var rd = createVirReg(para);
            addInst(new AsmMemoryS("lw", rd, fp, (i - paraRegsNum) << 2));
        }

        //visit all blocks
        it.entry.accept(this);
        it.blocks.forEach(block -> block.accept(this));
        it.returnBlock.accept(this);

        //implement phi instructions
        addPhiInst(curFunction);

        //build CFG for AsmFunction
        curFunction.blocks.forEach(AsmBlock::linkCFG);
    }

    @Override
    public void visit(IRBlock it) {
        curBlock = blockMap.get(it);
        it.instructions.forEach(ins -> ins.accept(this));
        it.terminator.accept(this);
    }

    @Override
    public void visit(GlobalDef it) {
        String name = it.dest.name;
        if (it.isStringLiteral) {
            String str = ((StringLiteral) it.init).str;
            root.addData(new AsmData(name, str));
        } else if (it.init instanceof GlobalVar g) {
            root.addData(new AsmData(name, g.name, false));
        } else {
            int val = getConstantVal(it.init);
            root.addData(new AsmData(name, val));
        }
    }

    //--------------------------------------- expression --------------------------------------------

    @Override
    public void visit(Binary it) {
        Reg rd = getReg(it.dest);
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
    public void visit(Call it) {
        for (int i = 0; i < Integer.min(paraRegsNum, it.args.size()); ++i)
            addInst(new AsmMv(a(i), getReg(it.args.get(i))));
        int paraOffset = 0;
        for (int i = paraRegsNum; i < it.args.size(); ++i) {
            Entity val = it.args.get(i);
            addInst(new AsmMemoryS("sw", getReg(val), sp, paraOffset));
            paraOffset += 4;
        }
        curFunction.paraOffset = Integer.max(curFunction.paraOffset, paraOffset);
        //store caller-saved registers
        var map = new HashMap<PhyReg, Integer>();
        for (int i = paraRegsNum; i <= 7; ++i) {
            var reg = a(i);
            addInst(new AsmMemoryS("sw", reg, fp, -(curFunction.offset += 4)));
            map.put(reg, curFunction.offset);
        }
        for (int i = 4; i <= 6; ++i) {
            var reg = t(i);
            addInst(new AsmMemoryS("sw", reg, fp, -(curFunction.offset += 4)));
            map.put(reg, curFunction.offset);
        }
        //
        addInst(new AsmCall(it.funcName));
        //reload caller-saved registers
        for (int i = 4; i <= 6; ++i) {
            var reg = t(i);
            addInst(new AsmMemoryS("lw", reg, fp, -map.get(reg)));
        }
        for (int i = paraRegsNum; i <= 7; ++i) {
            var reg = a(i);
            addInst(new AsmMemoryS("lw", reg, fp, -map.get(reg)));
        }
        //
        if (it.dest == null || it.dest == Void.instance) return;
        Reg rg = getReg(it.dest);
        addInst(new AsmMv(rg, a(0)));
    }

    @Override
    public void visit(GetElementPtr it) {
        Reg rd = getReg(it.dest);
        Entity index = it.indexList.get(it.indexList.size() - 1);
        Reg id = getReg(index);
        if (id == zero) {
            addInst(new AsmMv(rd, getReg(it.ptr)));
        } else {
            Reg tmp = new VirReg();
            addInst(new AsmBinaryS("slli", tmp, id, new Imm(2)));
            addInst(new AsmBinaryS("add", rd, getReg(it.ptr), tmp));
        }
    }


    @Override
    public void visit(Icmp it) {
        var rd = getReg(it.dest);
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
                addInst(new AsmBinaryS("xori", rd, rd, Imm.one));
                //rd = rd ^ 1 (rd: 0 -> 1 / 1 -> 0)
            }
            case sge -> {
                addInst(new AsmBinaryS("slt", rd, rs1, rs2));
                addInst(new AsmBinaryS("xori", rd, rd, Imm.one));
            }
        }
    }

    @Override
    public void visit(Load it) {
        Reg rd = getReg(it.dest);
        Reg rs = getReg(it.src);
        if (it.src instanceof GlobalVar) {
            addInst(new AsmMemoryS("lw", rd, rs, 0));
            return;
        }
        if (curFunction.containsReg(rs)) addInst(new AsmMv(rd, rs));
        else addInst(new AsmMemoryS("lw", rd, rs, 0));
    }

    @Override
    public void visit(Store it) {
        Reg rs = getReg(it.src);
        Reg rd = getReg(it.dest);
        if (it.dest instanceof GlobalVar) {
            //rd is the address of global variable
            addInst(new AsmMemoryS("sw", rs, rd, 0));
            return;
        }
        if (curFunction.containsReg(rd)) addInst(new AsmMv(rd, rs));
        else addInst(new AsmMemoryS("sw", rs, rd, 0));
    }

    @Override
    public void visit(Phi it) {
        VirReg tmp = new VirReg();
        addInst(new AsmMv(getReg(it.dest), tmp));
        for (int i = 0; i < it.values.size(); ++i) {
            Entity value = it.values.get(i);
            int val = getConstantVal(value);
            if (val != -19260817)
                blockMap.get(it.blocks.get(i)).addPhiInst(new AsmLi(tmp, new Imm(val)), curBlock.label);
            else
                blockMap.get(it.blocks.get(i)).addPhiInst(new AsmMv(tmp, getReg(value)), curBlock.label);
        }
    }

    //--------------------------------------- terminal --------------------------------------------

    @Override
    public void visit(Branch it) {
        // br cond bTrue bFalse -> beqz cond bFalse; j bTrue
        addInst(new AsmBranchS("beqz", getReg(it.cond), blockMap.get(it.branchFalse).label));
        addInst(new AsmJ(blockMap.get(it.branchTrue).label));
    }

    @Override
    public void visit(Jump it) {
        addInst(new AsmJ(blockMap.get(it.target).label));
    }

    @Override
    public void visit(Ret it) {
        if (it.returnVal != Void.instance) addInst(new AsmMv(a(0), getReg(it.returnVal)));
        addInst(new AsmRet());
        curFunction.retBlock = curBlock;
    }
}
