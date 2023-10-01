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

    public static int K = 11;
    public static ArrayList<PhyReg> freeRegs = freeRegs();
    public static ArrayList<Integer> colors = colors();

    private static ArrayList<Integer> colors() {
        var colors = new ArrayList<Integer>();
        for (int i = 0; i < K; ++i) colors.add(i);
        return colors;
    }

    private static ArrayList<PhyReg> freeRegs() {
        var regs = new ArrayList<PhyReg>();
        //for (int i = 4; i <= 6; ++i) regs.add(t(i));
        for (int i = 1; i <= 11; ++i) regs.add(s(i));
        return regs;
    }
}
