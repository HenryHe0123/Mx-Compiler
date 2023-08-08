package IR;

public abstract class IRInstruction {
    public abstract String getText();

    public abstract void accept(IRVisitor visitor);
}
