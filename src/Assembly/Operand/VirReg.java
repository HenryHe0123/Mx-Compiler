package Assembly.Operand;

public class VirReg extends Reg {
    private static int cnt = 0;
    public final int id;

    public VirReg() {
        id = cnt++;
    }

    @Override
    public String getText() {
        return "%" + id;
    }
}
