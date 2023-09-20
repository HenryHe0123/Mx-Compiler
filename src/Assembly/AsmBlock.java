package Assembly;

import Assembly.Instruction.Inst;

import java.util.ArrayList;
import java.util.HashMap;

public class AsmBlock {
    public Inst headInst = null, tailInst = null;
    public String label;
    public HashMap<String, ArrayList<Inst>> phi = new HashMap<>(); //phi inst from label

    public AsmBlock(String label) {
        this.label = label;
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
}
