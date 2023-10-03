package Optimizer.Asm;

import Assembly.AsmBlock;
import Assembly.AsmRoot;
import Assembly.Instruction.*;

public class PeepholeOptimize {
    public static void pass(AsmRoot root) {
        root.functions.forEach(function ->
                function.blocks.forEach(PeepholeOptimize::promote)
        );
    }

    private static void promote(AsmBlock block) {
        for (Inst inst = block.headInst; inst != null; inst = inst.next) {
            if (inst instanceof AsmMv mv && mv.useless()) {
                block.remove(inst);
            }
        }
    }
}
