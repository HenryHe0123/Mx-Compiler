package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.IRVisitor;
import IR.Type.IRType;

public class Load extends Expression {
    public Entity src; //ptr
    private final IRType baseType;

    public Load(Entity dest, Entity src) {
        super(dest);
        this.src = src;
        baseType = src.type.deconstruct();
    }

    @Override
    public String getText() {
        return dest.getText() + " = load " + baseType.getText() + ", ptr " + src.getText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
