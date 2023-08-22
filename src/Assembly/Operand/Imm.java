package Assembly.Operand;

public class Imm extends Operand {
    public int val;

    public Imm(int val) {
        this.val = val;
    }

    @Override
    public String getText() {
        return String.valueOf(val);
    }

    static public final Imm one = new Imm(1);
}

