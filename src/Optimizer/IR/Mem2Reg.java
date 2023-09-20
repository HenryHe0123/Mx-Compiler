package Optimizer.IR;

import IR.Entity.*;
import IR.*;
import IR.Instruction.Expression.*;
import IR.Instruction.*;

import java.util.*;

public class Mem2Reg {
    private static IRFunction curFunction;
    private static final HashSet<Register> allocas = new HashSet<>();
    private static final HashMap<Register, HashSet<IRBlock>> allocaDefs = new HashMap<>();
    private static HashMap<Register, Entity> reachingDefMap = new HashMap<>();

    public static void pass(IRRoot root) {
        root.functions.forEach(Mem2Reg::promote);
    }

    private static void reset(IRFunction func) {
        curFunction = func;
        allocas.clear();
        allocaDefs.clear();
    }

    public static void promote(IRFunction func) {
        reset(func);
        collect();
        insertPhi();
        renameVariable(func.entry);
        finish(func.entry);
    }

    private static void collect() {
        collect(curFunction.entry);
        for (var block : curFunction.blocks) collect(block);
        collect(curFunction.returnBlock);
    }

    private static void collect(IRBlock block) { //alloca and its defs
        for (var inst : block.instructions) {
            if (inst instanceof Alloca a) {
                Register reg = a.getRegister();
                assert reg != null;
                allocas.add(reg);
                //allocaDefs.put(reg, new HashSet<>());
            } else if (inst instanceof Store s && s.dest instanceof Register reg) {
                allocaDefs.computeIfAbsent(reg, k -> new HashSet<>()).add(block);
            }
        }
    }

    private static void insertPhi() {
        for (var reg : allocas) {
            var allocaDef = allocaDefs.get(reg);
            if (allocaDef == null) continue;

            HashSet<IRBlock> visited = new HashSet<>();
            HashSet<IRBlock> workSet = new HashSet<>(allocaDef);

            LinkedList<IRBlock> workList = new LinkedList<>(workSet);
            while (!workList.isEmpty()) {
                IRBlock block = workList.poll();
                workSet.remove(block);
                for (IRBlock frontier : block.domFrontier) {
                    if (!visited.contains(frontier)) {
                        Phi phi = new Phi(reg.renameAndDeconstruct(), reg);
                        frontier.addPhi(phi);

                        visited.add(frontier);
                        if (!workSet.contains(frontier)) {
                            workSet.add(frontier);
                            workList.add(frontier);
                        }
                    }
                }

            }
        }
    }

    private static Entity reachingDef(Register reg) {
        if (!reachingDefMap.containsKey(reg))
            reachingDefMap.put(reg, null);
        return reachingDefMap.get(reg);
    }

    private static void updateReachingDef(Register reg, Entity def) {
        reachingDefMap.put(reg, def);
    }

    private static void addPhiArgument(IRBlock toBlock, IRBlock fromBlock) {
        for (Phi phi : toBlock.phis) {
            phi.addBranch(reachingDef(phi.src), fromBlock);
        }
    }

    private static void renameVariable(IRBlock block) {
        var oldReachingDefMap = new HashMap<>(reachingDefMap);

        for (Phi phi : block.phis) {
            assert phi.src != null;
            updateReachingDef(phi.src, phi.dest);
        }

        var iter = block.instructions.iterator();
        while (iter.hasNext()) {
            Instruction inst = iter.next();
            if (inst instanceof Alloca) {
                iter.remove();
            } else if (inst instanceof Load load) {
                Register srcReg = load.getSrcReg();
                if (!allocas.contains(srcReg)) continue;

                load.dest.updateUse(reachingDef(srcReg));
                iter.remove();
            } else if (inst instanceof Store store) {
                Register destReg = store.getDestReg();
                if (!allocas.contains(destReg)) continue;
                updateReachingDef(destReg, store.src);
                iter.remove();
            }
        }

        block.next.forEach(next -> addPhiArgument(next, block));

        block.domChildren.forEach(Mem2Reg::renameVariable);
        reachingDefMap = oldReachingDefMap; //rollback
    }

    private static void finish(IRBlock block) {
        block.collectAndSimplifyPhi();
        block.domChildren.forEach(Mem2Reg::finish);
    }
}
