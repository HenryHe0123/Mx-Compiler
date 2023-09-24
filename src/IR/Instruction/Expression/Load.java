package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.Entity.Register;
import IR.IRVisitor;

public class Load extends Expression {
    public Entity src; //ptr

    public Load(Entity dest, Entity src) {
        super(dest);
        this.src = src;
        src.addUser(this);
    }

    @Override
    public String getText() {
        return dest.getText() + " = load " + dest.type.getText() + ", ptr " + src.getText() + "\n";
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

    public Register getSrcReg() {
        return (src instanceof Register reg) ? reg : null;
    }
}
