package Assembly;

import Assembly.Instruction.*;
import Assembly.Operand.Reg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AsmBlock {
    public Inst headInst = null, tailInst = null;
    public String label;
    public HashMap<String, ArrayList<Inst>> phi = new HashMap<>(); //phi inst from label

    public AsmBlock(String label) {
        this.label = label;
        blockMap.put(label, this);
    }

    private static final HashMap<String, AsmBlock> blockMap = new HashMap<>();

    public static AsmBlock get(String label) {
        return blockMap.get(label);
    }

    public void push_back(Inst i) {
        if (headInst == null) headInst = tailInst = i;
        else {
            tailInst.next = i;
            i.prev = tailInst;
            tailInst = i;
        }
    }

    public void add_front(Inst i) {
        if (headInst == null) headInst = tailInst = i;
        else {
            headInst.prev = i;
            i.next = headInst;
            headInst = i;
        }
    }

    public void insert_before(Inst i, Inst inserted) {
        if (i.prev == null) headInst = inserted;
        else i.prev.next = inserted;
        inserted.prev = i.prev;
        inserted.next = i;
        i.prev = inserted;
    }

    public void insert_after(Inst i, Inst inserted) {
        if (i.next == null) tailInst = inserted;
        else i.next.prev = inserted;
        inserted.prev = i;
        inserted.next = i.next;
        i.next = inserted;
    }

    public void remove(Inst i) {
        if (i == null) return;
        if (i == headInst) removeFirst();
        else if (i == tailInst) removeLast();
        else {
            i.prev.next = i.next;
            i.next.prev = i.prev;
        } //won't change i.next/prev
    }

    public void replace(Inst i, Inst newI) {
        var p = i.prev;
        var n = i.next;
        newI.prev = p;
        newI.next = n;
        if (p != null) p.next = newI;
        if (n != null) n.prev = newI;
        if (headInst == i) headInst = newI;
        if (tailInst == i) tailInst = newI;
    }

    public void removeLast() {
        if (tailInst == null) return;
        tailInst = tailInst.prev;
        if (tailInst != null) tailInst.next = null;
        else headInst = null;
    }

    public void removeFirst() {
        if (headInst == null) return;
        headInst = headInst.next;
        if (headInst != null) headInst.prev = null;
        else tailInst = null;
    }

    public void addPhiInst(Inst i, String label) {
        phi.computeIfAbsent(label, k -> new ArrayList<>()).add(i);
    }

    private static int anonymousBlockCnt = 0;

    public static AsmBlock newEmptyBlockForPhi() {
        return new AsmBlock("..phi." + anonymousBlockCnt++);
    }

    public String getText() {
        StringBuilder text = new StringBuilder();
        if (label != null) text.append(label).append(":\n");
        for (Inst i = headInst; i != null; i = i.next)
            text.append("\t").append(i.getText()).append("\n");
        return text.toString();
    }

    //--------------------------------------------------

    public ArrayList<AsmBlock> prev = new ArrayList<>();
    public ArrayList<AsmBlock> next = new ArrayList<>();

    public void addCFGEdge(AsmBlock block) { //this -> block
        this.next.add(block);
        block.prev.add(this);
    }

    public void linkCFG() {
        if (tailInst instanceof AsmJ jump)
            addCFGEdge(AsmBlock.get(jump.label));
        if (tailInst.prev instanceof AsmBranchS br)
            addCFGEdge(AsmBlock.get(br.toLabel));
    }

    public final HashSet<Reg> def = new HashSet<>();
    public final HashSet<Reg> use = new HashSet<>();
    public final HashSet<Reg> in = new HashSet<>(); //live in
    public final HashSet<Reg> out = new HashSet<>(); //live out

    public void getDefUse() {
        for (var inst = headInst; inst != null; inst = inst.next) {
            inst.getDefUse();
            for (Reg used : inst.use) {
                if (!def.contains(used)) use.add(used);
            }
            def.addAll(inst.def);
        }
        in.addAll(use);
    }
}
