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

import java.util.HashMap;

import static Assembly.Operand.PhyReg.*;

public class InstSelector implements IRVisitor {
    public final AsmModule module;
    private AsmFunction curFunction;
    private AsmBlock curBlock;
    private final HashMap<Entity, Reg> regMap = new HashMap<>();
    private final HashMap<IRBlock, AsmBlock> blockMap = new HashMap<>();

    public InstSelector(AsmModule asmModule) {
        module = asmModule;
    }

    //-------------------------------------- utility ----------------------------------------------

    private void addInst(Inst ins) {
        curBlock.push_back(ins);
    }

    private Reg getReg(Entity entity) {
        if (entity instanceof Int i) {
            int val = i.toInt();
            if (val == 0) return zero;
            Reg rg = new VirReg();
            addInst(new AsmLi(rg, new Imm(val)));
            return rg;
        } else if (entity instanceof Bool b) {
            boolean val = b.toBool();
            if (!val) return zero;
            Reg rg = new VirReg();
            addInst(new AsmLi(rg, Imm.one));
            return rg;
        } else if (entity instanceof Null || entity instanceof Void) {
            return zero;
        } else if (entity instanceof GlobalVar g) {
            Reg rg = new VirReg();
            addInst(new AsmLa(rg, g.name));
            return rg; //return address of global variable
        }
        Reg rg = regMap.get(entity);
        if (rg == null) rg = createVirReg(entity);
        return rg;
    }

    private int getConstantVal(Entity entity) {
        if (entity instanceof Int i) {
            return i.toInt();
        } else if (entity instanceof Bool b) {
            return b.toBool() ? 1 : 0;
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

        //collect all blocks
        blockMap.clear();
        collectBlock(it.entry);
        it.blocks.forEach(this::collectBlock);
        collectBlock(it.returnBlock);

        curBlock = blockMap.get(it.entry);

        //map parameter entity to virtual register
        for (int i = 0; i < Integer.min(8, it.parameters.size()); ++i) {
            Entity para = it.parameters.get(i);
            var rd = createVirReg(para);
            addInst(new AsmMv(rd, a(i))); //move ai to rd
        }

        for (int i = 8; i < it.parameters.size(); ++i) {
            Entity para = it.parameters.get(i);
            var rd = createVirReg(para);
            addInst(new AsmMemoryS("lw", rd, fp, (i - 8) << 2));
        }

        //visit all blocks
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
    public void visit(GlobalDef it) {
        String name = ((GlobalVar) it.dest).name;
        if (it.isStringLiteral) {
            String str = ((StringLiteral) it.init).str;
            module.addData(new AsmData(name, str));
        } else if (it.init instanceof GlobalVar g) {
            module.addData(new AsmData(name, g.name, false));
        } else {
            int val = getConstantVal(it.init);
            module.addData(new AsmData(name, val));
        }
    }

    //--------------------------------------- expression --------------------------------------------

    @Override
    public void visit(Alloca it) {
        var rd = createVirReg(it.dest);
        curFunction.allocate(rd);
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
    public void visit(Call it) {
        for (int i = 0; i < Integer.min(8, it.args.size()); ++i)
            addInst(new AsmMv(a(i), getReg(it.args.get(i))));
        int paraOffset = 0;
        for (int i = 8; i < it.args.size(); ++i) {
            Entity val = it.args.get(i);
            addInst(new AsmMemoryS("sw", getReg(val), sp, paraOffset));
            paraOffset += 4;
        }
        curFunction.paraOffset = Integer.max(curFunction.paraOffset, paraOffset);
        addInst(new AsmCall(it.funcName));

        if (it.dest == null || it.dest == Void.instance) return;
        VirReg rg = createVirReg(it.dest);
        addInst(new AsmMv(rg, a(0)));
    }

    @Override
    public void visit(GetElementPtr it) {
        Reg rd = createVirReg(it.dest);
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
        Reg rd = createVirReg(it.dest);
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
        //todo
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
    }
}
