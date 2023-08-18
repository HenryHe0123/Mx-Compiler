package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.IRVisitor;

public class Load extends Expression {
    public Entity src; //ptr

    public Load(Entity dest, Entity src) {
        super(dest);
        this.src = src;
    }

    @Override
    public String getText() {
        return dest.getText() + " = load " + dest.type.getText() + ", ptr " + src.getText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
