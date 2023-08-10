package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.IRVisitor;

public class Store extends Expression {
    public Entity src;

    public Store(Entity src, Entity dest) {
        super(dest);
        this.src = src;
    }

    @Override
    public String getText() {
        return "store " + src.getFullText() + ", ptr " + dest.getText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
