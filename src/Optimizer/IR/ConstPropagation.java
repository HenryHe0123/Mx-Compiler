package Optimizer.IR;

import IR.Entity.Bool;
import IR.IRBlock;
import IR.IRRoot;
import IR.Instruction.Expression.Expression;
import IR.Instruction.Expression.Phi;
import IR.Instruction.IRFunction;
import IR.Instruction.Instruction;
import IR.Instruction.Terminal.Branch;
import IR.Instruction.Terminal.Jump;

import java.util.HashSet;
import java.util.LinkedList;

public class ConstPropagation {
    private static final HashSet<Instruction> workSet = new HashSet<>();
    private static final LinkedList<Instruction> workList = new LinkedList<>();
    private static final HashSet<Instruction> deletedInstSet = new HashSet<>();
    private static IRFunction curFunction;

    private static void init(IRFunction function) {
        curFunction = function;
        workList.clear();
        workSet.clear();
        deletedInstSet.clear();
        for (var block : function.allBlocks()) {
            for (var inst : block.instructions) {
                workList.add(inst);
                workSet.add(inst);
            }
            var inst = block.terminator;
            workList.add(inst);
            workSet.add(inst);
        }
    }

    public static void pass(IRRoot root) {
        root.functions.forEach(ConstPropagation::promote);
    }

    public static void promote(IRFunction function) {
        init(function);
        while (!workList.isEmpty()) {
            Instruction inst = workList.poll();
            workSet.remove(inst);
            if (deletedInstSet.contains(inst)) continue;
            if (inst instanceof Expression expr) {
                var entity = expr.simplify();
                if (entity != null) {
                    var old = expr.dest;
                    for (Instruction user : old.users) {
                        user.replaceUse(old, entity);
                        if (!workSet.contains(user)) {
                            workList.add(user);
                            workSet.add(user);
                        }
                    }
                    deleteInst(expr);
                }
            } else if (inst instanceof Branch br && br.cond instanceof Bool bool) {
                boolean cond = bool.getVal();
                IRBlock curBlock = br.curBlock;
                IRBlock toBlock = cond ? br.branchTrue : br.branchFalse;
                IRBlock otherBlock = cond ? br.branchFalse : br.branchTrue;
                curBlock.terminator = new Jump(toBlock);
                removeLink(curBlock, otherBlock);
            }
        }

        for (IRBlock block : function.allBlocks())
            block.instructions.removeIf(deletedInstSet::contains);
    }

    private static void deleteInst(Instruction inst) {
        deletedInstSet.add(inst);
        for (var use : inst.useList())
            use.removeUser(inst);
    }

    private static void removeLink(IRBlock block, IRBlock success) {
        boolean flag1 = !success.prev.remove(block); //remove prev fail
        boolean flag2 = !block.next.remove(success); //remove next fail
        if (flag1 && flag2) return;
        for (var phiInst : success.instructions) {
            if (!(phiInst instanceof Phi phi)) break;
            boolean found = false;
            for (int i = 0; i < phi.values.size(); ++i)
                if (phi.blocks.get(i) == block) {
                    phi.blocks.remove(i);
                    phi.values.remove(i);
                    found = true;
                    break;
                }
            if (found && !workSet.contains(phiInst)) {
                workList.add(phiInst);
                workSet.add(phiInst);
            }
        }
        if (success.prev.isEmpty()) deleteBlock(success);
    }

    private static void deleteBlock(IRBlock block) {
        if (!curFunction.removeBlock(block)) return;

        for (var inst : block.instructions)
            deleteInst(inst);
        deleteInst(block.terminator);

        for (var success : block.next)
            removeLink(block, success);
    }
}
