package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.IRVisitor;

public class Select extends Expression { //unused
    public Entity cond, src1, src2;

    public Select(Entity dest, Entity condition, Entity src1, Entity src2) {
        super(dest);
        cond = condition;
        this.src1 = src1;
        this.src2 = src2;
    }

    @Override
    public String getText() {
        return dest.getText() + " = select i1 " + cond.getText() + ", " + src1.getFullText() + ", " + src2.getFullText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
