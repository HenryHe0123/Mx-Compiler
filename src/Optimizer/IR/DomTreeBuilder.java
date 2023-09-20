package Optimizer.IR;

import IR.*;
import IR.Instruction.IRFunction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class DomTreeBuilder {
    private static final HashSet<IRBlock> visited = new HashSet<>();
    private static final HashMap<IRBlock, Integer> order = new HashMap<>(); //post order
    private static final LinkedList<IRBlock> sequence = new LinkedList<>(); //blocks in reverse post order

    private static void reset() {
        visited.clear();
        order.clear();
        sequence.clear();
    }

    public static void build(IRRoot root) {
        root.functions.forEach(DomTreeBuilder::build);
    }

    //A Simple, Fast Dominance Algorithm by Keith D. Cooper
    public static void build(IRFunction func) {
        reset();
        getReversePostOrder(func.entry);

        //get idom
        func.entry.idom = func.entry;
        sequence.removeFirst(); //except entry
        boolean changed = true;
        while (changed) {
            changed = false;
            for (var block : sequence) {
                IRBlock newIdom = null;
                for (var prev : block.prev) {
                    if (newIdom == null) newIdom = prev;
                    else if (prev.idom != null) //debug: only if prev.idom already calculated
                        newIdom = intersect(prev, newIdom);
                }
                if (block.idom != newIdom) {
                    block.idom = newIdom;
                    changed = true;
                }
            }
        }

        //get dominator tree
        sequence.forEach(block -> block.idom.addDomChildren(block));

        //get dominance frontier
        for (var block : sequence) {
            if (block.prev.size() < 2) continue;
            for (var p : block.prev) {
                IRBlock runner = p;
                while (runner != block.idom) {
                    runner.addDomFrontier(block);
                    runner = runner.idom;
                }
            }
        }
    }

    private static int order(IRBlock block) {
        return order.get(block);
    }

    private static IRBlock intersect(IRBlock a, IRBlock b) {
        if (a == null) return b;
        if (b == null) return a;
        while (a != b) { //find the closet common dominator
            while (order(a) < order(b)) a = a.idom;
            while (order(b) < order(a)) b = b.idom;
        }
        return a;
    }

    private static void getReversePostOrder(IRBlock block) {
        visited.add(block);
        for (var next : block.next)
            if (!visited.contains(next)) getReversePostOrder(next);
        order.put(block, sequence.size()); //post order (not reverse)
        sequence.addFirst(block);
    }
}
