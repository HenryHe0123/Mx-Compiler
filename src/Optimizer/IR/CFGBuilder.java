package Optimizer.IR;

import IR.IRRoot;
import IR.Instruction.IRFunction;

public class CFGBuilder {
    public static void build(IRRoot root) {
        root.functions.forEach(IRFunction::buildCFG);
    }
}
