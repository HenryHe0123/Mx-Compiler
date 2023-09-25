package Optimizer.IR;

import IR.*;
import IR.Entity.Register;
import IR.Instruction.*;
import IR.Instruction.Expression.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class DeadCodeElimination {
    public static void pass(IRRoot root) {
        root.functions.forEach(DeadCodeElimination::eliminate);
    }

    public static void eliminate(IRFunction function) { //SSA format already
        LinkedList<Register> workList = new LinkedList<>(); //def register
        HashSet<Register> workSet = new HashSet<>();
        HashMap<Register, Expression> defMap = new HashMap<>();
        HashMap<Register, HashSet<Instruction>> useSetMap = new HashMap<>();
        HashSet<Instruction> deletedInstSet = new HashSet<>();

        for (IRBlock block : function.allBlocks()) {
            for (Instruction inst : block.instructions) {
                //debug: store dest should be viewed as use rather than def
                if (inst instanceof Expression expr && expr.dest instanceof Register reg && !(expr instanceof Store)) {
                    defMap.put(reg, expr);
                    workList.add(reg);
                    workSet.add(reg);
                }

                for (Register use : inst.useList())
                    useSetMap.computeIfAbsent(use, k -> new HashSet<>()).add(inst);
            }

            var inst = block.terminator;
            for (Register use : inst.useList())
                useSetMap.computeIfAbsent(use, k -> new HashSet<>()).add(inst);
        }

        while (!workList.isEmpty()) {
            Register reg = workList.removeFirst();
            workSet.remove(reg);
            if (useSetMap.get(reg) == null || useSetMap.get(reg).isEmpty()) {
                Expression def = defMap.get(reg);
                if (def == null || def instanceof Call) continue;
                deletedInstSet.add(def);
                for (Register use : def.useList()) {
                    useSetMap.get(use).remove(def);
                    if (!workSet.contains(use) && defMap.containsKey(use)) { //for those def reg removed previously
                        workList.add(use);
                        workSet.add(use);
                    }
                }
            }
        }

        for (IRBlock block : function.allBlocks())
            block.instructions.removeIf(deletedInstSet::contains);
    }
}
