package Assembly.Instruction;

public class AsmCall extends Inst {
    public String name;

    public AsmCall(String name) {
        this.name = name;
    }

    @Override
    public String getText() {
        return "call\t" + name;
    }
}
