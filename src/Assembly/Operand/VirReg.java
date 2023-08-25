package Assembly.Operand;

public class VirReg extends Reg {
    private static long cnt = 0;
    public final long id;

    public VirReg() {
        id = cnt++;
    }

    @Override
    public String getText() {
        return "%" + id;
    }
}
