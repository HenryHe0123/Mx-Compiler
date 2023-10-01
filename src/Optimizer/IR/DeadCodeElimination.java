package Optimizer.IR;

import IR.*;
import IR.Entity.*;
import IR.Instruction.*;
import IR.Instruction.Expression.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class DeadCodeElimination {
    //global
    private static final HashSet<GlobalVar> globalVarUsed = new HashSet<>();
    private static final HashSet<String> functionCalled = new HashSet<>();
    private static final HashMap<String, IRFunction> functionMap = new HashMap<>();
    //local
    private static final LinkedList<Register> workList = new LinkedList<>(); //def register
    private static final HashSet<Register> workSet = new HashSet<>();
    private static final HashMap<Register, Expression> defMap = new HashMap<>();
    private static final HashMap<Register, HashSet<Instruction>> useSetMap = new HashMap<>();
    private static final HashSet<Instruction> deletedInstSet = new HashSet<>();

    public static void pass(IRRoot root) {
        globalReset();
        root.functions.forEach(func -> functionMap.put(func.name, func));
        root.functions.forEach(DeadCodeElimination::eliminate);
        eliminateGlobals(root);
        eliminateUselessFunc(root);
    }

    private static void eliminateUselessFunc(IRRoot root) {
        var newFuncs = new ArrayList<IRFunction>();
        for (IRFunction func : root.functions) {
            if (functionCalled.contains(func.name) || func.isMain()) newFuncs.add(func);
        }
        root.functions = newFuncs;
    }

    private static void eliminateGlobals(IRRoot root) {
        var newGlobals = new ArrayList<Instruction>();
        for (Instruction inst : root.globals) {
            if (inst instanceof GlobalDef global) {
                GlobalVar gVar = global.dest;
                if (globalVarUsed.contains(gVar) || gVar.isStringLiteral()) newGlobals.add(inst);
            } else newGlobals.add(inst);
        }
        root.globals = newGlobals;
    }

    private static void eliminate(IRFunction function) { //SSA format already
        localReset();
        for (IRBlock block : function.allBlocks()) { //collect
            for (Instruction inst : block.instructions) {
                if (inst instanceof Call call) {
                    IRFunction callee = functionMap.get(call.funcName);
                    if (callee != null && callee.isUseless()) {
                        deletedInstSet.add(call);
                        continue;
                    }
                    functionCalled.add(call.funcName);
                }
                //debug: store dest should be viewed as use rather than def
                if (inst instanceof Expression expr && expr.dest instanceof Register reg && !(expr instanceof Store)) {
                    defMap.put(reg, expr);
                    workList.add(reg);
                    workSet.add(reg);
                }
                collectUse(inst);
            }
            collectUse(block.terminator);
        }

        while (!workList.isEmpty()) {
            Register reg = workList.removeFirst();
            workSet.remove(reg);
            if (useSetMap.get(reg) == null || useSetMap.get(reg).isEmpty()) {
                Expression def = defMap.get(reg);
                if (def == null || def instanceof Call) continue;
                deletedInstSet.add(def);
                for (Entity entity : def.useList())
                    if (entity instanceof Register use) {
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

    private static void collectUse(Instruction inst) {
        for (Entity entity : inst.useList()) {
            if (entity instanceof Register use)
                useSetMap.computeIfAbsent(use, k -> new HashSet<>()).add(inst);
            else if (entity instanceof GlobalVar globalVar)
                globalVarUsed.add(globalVar);
        }
    }

    private static void localReset() {
        workList.clear();
        workSet.clear();
        defMap.clear();
        useSetMap.clear();
        deletedInstSet.clear();
    }

    private static void globalReset() {
        globalVarUsed.clear();
        functionCalled.clear();
        functionMap.clear();
    }
}
