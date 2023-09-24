package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.Entity.Register;
import IR.IRVisitor;

public class Store extends Expression {
    public Entity src;

    public Store(Entity src, Entity dest) {
        super(dest);
        this.src = src;
        src.addUser(this);
    }

    @Override
    public String getText() {
        return "store " + src.getFullText() + ", ptr " + dest.getText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void replaceUse(Entity old, Entity latest) {
        if (src == old) {
            src = latest;
            Entity.addUser(latest, this);
        }
    }

    public Register getDestReg() {
        return (dest instanceof Register reg) ? reg : null;
    }

}
