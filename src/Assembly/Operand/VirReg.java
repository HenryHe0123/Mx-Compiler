package Assembly.Operand;

public class VirReg extends Reg {
    private static int cnt = 0;
    public int bytes, id = cnt++;

    public VirReg(int bytes) {
        this.bytes = bytes;
    }

    public VirReg() {
        bytes = 4;
    }

    @Override
    public String getText() {
        return "%" + id;
    }
}
