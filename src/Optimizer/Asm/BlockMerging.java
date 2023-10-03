package Optimizer.Asm;

import Assembly.*;
import Assembly.Instruction.AsmJ;

import java.util.HashSet;
import java.util.LinkedList;

public class BlockMerging {
    private static final LinkedList<AsmBlock> order = new LinkedList<>();
    private static final HashSet<AsmBlock> visited = new HashSet<>();

    private static void reset() {
        order.clear();
        visited.clear();
    }

    public static void pass(AsmRoot root) {
        root.functions.forEach(BlockMerging::promote);
    }

    private static void promote(AsmFunction func) {
        reset();
        LinkedList<AsmBlock> newBlocks = new LinkedList<>();
        var entry = func.entryBlock();
        newBlocks.add(entry);
        dfs(entry);

        for (int i = 1; i < order.size(); ++i) {
            AsmBlock block = order.get(i);
            if (block.prev.isEmpty()) {
                for (AsmBlock next : block.next) next.prev.remove(block);
                continue;
            }
            if (block.prev.size() == 1) {
                var prevBlock = block.prev.get(0);
                if (prevBlock.tailInst instanceof AsmJ jump && jump.label.equals(block.label)) {
                    prevBlock.removeLast(); // remove jump
                    for (var inst = block.headInst; inst != null; inst = inst.next)
                        prevBlock.push_back(inst);

                    prevBlock.next.remove(block);
                    prevBlock.next.addAll(block.next);
                    for (AsmBlock next : block.next) {
                        next.prev.remove(block);
                        next.prev.add(prevBlock);
                    }
                    continue;
                }
            }
            //debug: be careful of the if-else branch, don't miss any of them!
            newBlocks.add(block);
        }
        func.blocks = newBlocks;
    }

    private static void dfs(AsmBlock block) {
        if (!visited.contains(block)) {
            visited.add(block);
            order.add(block);
            block.next.forEach(BlockMerging::dfs);
        }
    }
}
