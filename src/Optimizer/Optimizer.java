package Optimizer;

import IR.IRRoot;
import Optimizer.IR.*;

public class Optimizer {
    private static final boolean on = true;

    public static void optimize(IRRoot root) {
        if (!on) return;
        Global2Local.pass(root);
        CFGBuilder.build(root);
        DomTreeBuilder.build(root);
        Mem2Reg.pass(root);
        DeadCodeElimination.pass(root);
        ConstPropagation.pass(root);
    }
}
