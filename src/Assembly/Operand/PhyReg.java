package Assembly.Operand;

import java.util.ArrayList;

public class PhyReg extends Reg {
    public String name;

    private PhyReg(String name) {
        this.name = name;
    }

    @Override
    public String getText() {
        return name;
    }

    static public final PhyReg zero = new PhyReg("zero");
    static public final PhyReg ra = new PhyReg("ra"); // return address
    static public final PhyReg sp = new PhyReg("sp"); // stack pointer
    static public final PhyReg gp = new PhyReg("gp"); // global pointer
    static public final ArrayList<PhyReg> t = tRegs(); // temporary register
    static public final ArrayList<PhyReg> s = sRegs(); // saved register
    static public final ArrayList<PhyReg> a = aRegs(); // argument register

    static public PhyReg t(int i) {
        return t.get(i);
    }

    static public PhyReg s(int i) {
        return s.get(i);
    }

    static public PhyReg a(int i) {
        return a.get(i);
    }

    static private ArrayList<PhyReg> tRegs() {
        ArrayList<PhyReg> tRegs = new ArrayList<>();
        for (int i = 0; i <= 6; ++i)
            tRegs.add(new PhyReg("t" + i));
        return tRegs;
    }

    static private ArrayList<PhyReg> sRegs() {
        ArrayList<PhyReg> sRegs = new ArrayList<>();
        for (int i = 0; i <= 11; ++i)
            sRegs.add(new PhyReg("s" + i));
        return sRegs;
    }

    static private ArrayList<PhyReg> aRegs() {
        ArrayList<PhyReg> aRegs = new ArrayList<>();
        for (int i = 0; i <= 7; ++i)
            aRegs.add(new PhyReg("a" + i));
        return aRegs;
    }
}
