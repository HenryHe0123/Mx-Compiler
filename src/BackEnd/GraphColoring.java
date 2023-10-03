package BackEnd;

import Assembly.*;
import Assembly.Operand.*;
import Assembly.Instruction.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import static Assembly.Operand.PhyReg.*;

public class GraphColoring {
    private AsmFunction curFunction;

    private final HashSet<Reg> initial = new HashSet<>();

    //build interference graph
    public static class Edge {
        public Reg u, v;

        public Edge(Reg u, Reg v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Edge e)) return false;
            return (u == e.u && v == e.v) || (u == e.v && v == e.u);
        }

        @Override
        public int hashCode() {
            return u.hashCode() ^ v.hashCode();
        }
    }

    private final HashSet<Edge> adjSet = new HashSet<>();  //unchanged
    private final HashMap<Reg, HashSet<Reg>> adjList = new HashMap<>();  //unchanged
    private final HashMap<Reg, Integer> degree = new HashMap<>();

    //simplify & spill
    private final HashSet<Reg> simplifyWorkList = new HashSet<>();
    private final HashSet<Reg> spillWorkList = new HashSet<>();
    private final Stack<Reg> selectStack = new Stack<>();

    //assign color
    private final HashMap<Reg, Integer> color = new HashMap<>();

    private void Initialize() {
        adjSet.clear();
        adjList.clear();
        degree.clear();
        simplifyWorkList.clear();
        spillWorkList.clear();
        selectStack.clear();
        color.clear();
    }

    private void Build() {
        for (var block : curFunction.blocks) {
            HashSet<Reg> live = new HashSet<>(block.out);
            for (var inst = block.tailInst; inst != null; inst = inst.prev) {
                live.addAll(inst.def);
                for (var d : inst.def) {
                    for (var l : live) {
                        addEdge(l, d);
                    }
                    if (d instanceof VirReg) initial.add(d);
                }
                inst.def.forEach(live::remove);
                live.addAll(inst.use);
            }
        }
    }

    private void addEdge(Reg u, Reg v) {
        if (u instanceof PhyReg || v instanceof PhyReg) return;
        if (u == v) return;
        Edge e = new Edge(u, v);
        if (!adjSet.contains(e)) {
            adjSet.add(e);

            adjList.computeIfAbsent(u, k -> new HashSet<>()).add(v);
            degree.put(u, degree.computeIfAbsent(u, k -> 0) + 1);

            adjList.computeIfAbsent(v, k -> new HashSet<>()).add(u);
            degree.put(v, degree.computeIfAbsent(v, k -> 0) + 1);
        }
    }

    public void visit(AsmRoot it) {
        LivenessAnalyzer.visit(it);
        it.functions.forEach(this::visit);
    }

    private void visit(AsmFunction it) {
        curFunction = it;
        Initialize();
        Build();
        MakeWorkList();
        while (true) {
            if (!simplifyWorkList.isEmpty()) Simplify();
            else if (!spillWorkList.isEmpty()) SelectSpill();
            else break;
        }
        AssignColors();
        allocateByColor(it);
    }

    public void allocateByColor(AsmFunction it) {
        for (var block : it.blocks) {
            for (var inst = block.headInst; inst != null; inst = inst.next) {
                if (inst instanceof AsmBinaryS i) {
                    if (i.rs1 instanceof VirReg r && color.containsKey(r)) i.rs1 = freeRegs.get(color.get(r));
                    if (i.rs2 instanceof VirReg r && color.containsKey(r)) i.rs2 = freeRegs.get(color.get(r));
                    if (i.rd instanceof VirReg r && color.containsKey(r)) i.rd = freeRegs.get(color.get(r));
                } else if (inst instanceof AsmSetCmpS i) {
                    if (i.rs instanceof VirReg r && color.containsKey(r)) i.rs = freeRegs.get(color.get(r));
                    if (i.rd instanceof VirReg r && color.containsKey(r)) i.rd = freeRegs.get(color.get(r));
                } else if (inst instanceof AsmLa i) {
                    if (color.containsKey(i.rd) && i.rd instanceof VirReg) i.rd = freeRegs.get(color.get(i.rd));
                } else if (inst instanceof AsmLi i) {
                    if (color.containsKey(i.rd) && i.rd instanceof VirReg) i.rd = freeRegs.get(color.get(i.rd));
                } else if (inst instanceof AsmMemoryS i) {
                    if (color.containsKey(i.rs) && i.rs instanceof VirReg) i.rs = freeRegs.get(color.get(i.rs));
                    if (color.containsKey(i.rd) && i.rd instanceof VirReg) i.rd = freeRegs.get(color.get(i.rd));
                } else if (inst instanceof AsmMv i) {
                    if (color.containsKey(i.rs) && i.rs instanceof VirReg) i.rs = freeRegs.get(color.get(i.rs));
                    if (color.containsKey(i.rd) && i.rd instanceof VirReg) i.rd = freeRegs.get(color.get(i.rd));
                } else if (inst instanceof AsmBranchS i) {
                    if (i.cond instanceof VirReg r && color.containsKey(r)) i.cond = freeRegs.get(color.get(r));
                }
            }
        }
    }

    private void MakeWorkList() {
        for (var n : initial) {
            if (degree.computeIfAbsent(n, k -> 0) >= K) spillWorkList.add(n);
            else simplifyWorkList.add(n);
        }
    }

    private void Simplify() {
        Reg n = simplifyWorkList.iterator().next();
        //System.err.println("simplify " + n.getText());
        simplifyWorkList.remove(n);
        //initial.remove(n);
        selectStack.push(n);
        for (var m : Adjacent(n)) DecrementDegree(m);
    }

    //adjacent nodes except those in selectStack
    private HashSet<Reg> Adjacent(Reg reg) {
        var list = adjList.get(reg);
        if (list == null) return new HashSet<>();
        HashSet<Reg> tmp = new HashSet<>(list);
        selectStack.forEach(tmp::remove);
        return tmp;
    }

    private void DecrementDegree(Reg reg) {
        int d = degree.get(reg);
        degree.replace(reg, d - 1);
    }

    //spill the max degree node
    private void SelectSpill() {
        Reg m = null;
        int maxDegree = -1;
        for (var reg : spillWorkList) {
            int d = degree.get(reg);
            if (d > maxDegree) {
                m = reg;
                maxDegree = d;
            }
        }
        spillWorkList.remove(m);
        simplifyWorkList.add(m);
    }

    private void AssignColors() {
        while (!selectStack.isEmpty()) {
            Reg n = selectStack.pop();
            if (color.containsKey(n)) continue;
            HashSet<Integer> okColors = new HashSet<>(colors);
            for (var w : adjList.computeIfAbsent(n, k -> new HashSet<>())) {
                Integer i = color.get(w);
                if (i != null) okColors.remove(i);
            }
            if (!okColors.isEmpty()) {
                int c = okColors.iterator().next();
                color.put(n, c);
                if (c < 11) curFunction.usedCalleeRegs.add(freeRegs.get(c));
                else usedCallerRegs.add(freeRegs.get(c));
            } //else spill
        }
    }
}
