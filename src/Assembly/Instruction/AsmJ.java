package Assembly.Instruction;

public class AsmJ extends Inst {
    public String label;

    public AsmJ(String label) {
        this.label = label;
    }

    @Override
    public String getText() {
        return "j\t" + label;
    }
}
