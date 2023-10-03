package Assembly.Operand;

import java.util.*;

public class PhyReg extends Reg {
    public String name;

    public PhyReg(String name) {
        this.name = name;
    }

    @Override
    public String getText() {
        return name;
    }

    static public final PhyReg zero = new PhyReg("zero");
    static public final PhyReg ra = new PhyReg("ra"); //return address
    static public final PhyReg sp = new PhyReg("sp"); //stack pointer
    static public final ArrayList<PhyReg> t = tRegs(); //temporary register
    static public final ArrayList<PhyReg> s = sRegs(); //saved register
    static public final ArrayList<PhyReg> a = aRegs(); //argument register
    static public final PhyReg fp = s(0); //frame pointer

    static public PhyReg t(int i) {
        return t.get(i);
    }

    static public PhyReg s(int i) {
        return s.get(i);
    }

    static public PhyReg a(int i) {
        return a.get(i);
    }

    static private ArrayList<PhyReg> tRegs() { //7
        ArrayList<PhyReg> tRegs = new ArrayList<>();
        for (int i = 0; i <= 6; ++i)
            tRegs.add(new PhyReg("t" + i));
        return tRegs;
    }

    static private ArrayList<PhyReg> sRegs() { //12
        ArrayList<PhyReg> sRegs = new ArrayList<>();
        for (int i = 0; i <= 11; ++i)
            sRegs.add(new PhyReg("s" + i));
        return sRegs;
    }

    static private ArrayList<PhyReg> aRegs() { //8
        ArrayList<PhyReg> aRegs = new ArrayList<>();
        for (int i = 0; i <= 7; ++i)
            aRegs.add(new PhyReg("a" + i));
        return aRegs;
    }

    //------------------------- Register Allocation -------------------------//

    public static final int paraRegsNum = 3;
    //no less than 3, as build-in function may have 3 parameters at most
    //the other a-regs will be used for graph coloring
    public static final int usedTRegsNum = 2;
    //paraRegs can also be used as tmp reg in some cases
    public static final int K = 26 - usedTRegsNum - paraRegsNum; //21
    public static final ArrayList<PhyReg> freeRegs = freeRegs();
    public static final ArrayList<Integer> colors = colors();
    public static final HashSet<PhyReg> usedCallerRegs = new HashSet<>();

    private static ArrayList<Integer> colors() {
        var colors = new ArrayList<Integer>();
        for (int i = 0; i < K; ++i) colors.add(i);
        return colors;
    }

    private static ArrayList<PhyReg> freeRegs() {
        var regs = new ArrayList<PhyReg>();
        for (int i = 1; i <= 11; ++i) regs.add(s(i));
        for (int i = usedTRegsNum; i <= 6; ++i) regs.add(t(i));
        for (int i = paraRegsNum; i <= 7; ++i) regs.add(a(i));
        return regs;
    }
}
