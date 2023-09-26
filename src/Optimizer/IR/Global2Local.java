package Optimizer.IR;

import IR.Entity.*;
import IR.IRBlock;
import IR.IRRoot;
import IR.Instruction.*;
import IR.Instruction.Expression.*;

import java.util.ArrayList;
import java.util.HashSet;

public class Global2Local {
    public static void pass(IRRoot root) {
        ArrayList<Instruction> newGlobals = new ArrayList<>();
        for (Instruction inst : root.globals) {
            if (inst instanceof GlobalDef global && global.isSimple()) {
                GlobalVar gVar = global.dest;
                IRFunction func = inSingleFunc(gVar, root);
                if (func != null && (func.name.equals("main") || func.name.equals(IRFunction.renamedVarInitFuncName))) {
                    //debug: only for main and gVarInit function (which will only be executed once)
                    //replace global with local register
                    Register reg = new Register(gVar.name, gVar.type);
                    IRBlock entry = func.entry;
                    entry.addInstructFirst(new Store(global.init, reg));
                    entry.addInstructFirst(new Alloca(reg, gVar.type.deconstruct()));
                    gVar.updateUse(reg);
                } else newGlobals.add(inst);
            } else newGlobals.add(inst);
        }
        root.globals = newGlobals;
    }

    private static IRFunction inSingleFunc(GlobalVar gVar, IRRoot root) {
        IRFunction inFunc = null;
        HashSet<Instruction> userSet = new HashSet<>(gVar.users);
        for (var func : root.functions) {
            for (var block : func.allBlocks()) {
                if (inFunc == func) break;
                for (var inst : block.instructions) {
                    if (userSet.contains(inst)) {
                        if (inFunc == null) {
                            inFunc = func;
                            break;
                        } else return null; //inFunc != func already
                    }
                }
                if (userSet.contains(block.terminator)) {
                    if (inFunc == null) {
                        inFunc = func;
                    } else if (inFunc != func) {
                        return null;
                    }
                }
            }
        }
        return inFunc;
    }
}
