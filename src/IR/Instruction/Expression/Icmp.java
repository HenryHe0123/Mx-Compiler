package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.IRVisitor;

public class Icmp extends Expression {
    public enum IcmpOpType {
        eq, ne, sgt, sge, slt, sle
    }

    public IcmpOpType op;
    public Entity src1, src2;

    public Icmp(Entity dest, IcmpOpType op, Entity src1, Entity src2) {
        super(dest);
        this.op = op;
        this.src1 = src1;
        this.src2 = src2;
    }

    @Override
    public String getText() {
        return dest.getText() + " = icmp " + op.name() + " " + src1.getFullText() + ", " + src2.getText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
