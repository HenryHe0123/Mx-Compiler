package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.IRVisitor;
import Util.Error.CodegenError;

public class Icmp extends Expression {
    public enum IcmpOp {
        eq, ne, sgt, sge, slt, sle
    }

    public IcmpOp op;
    public Entity src1, src2;

    public Icmp(Entity dest, IcmpOp op, Entity src1, Entity src2) {
        super(dest);
        this.op = op;
        this.src1 = src1;
        this.src2 = src2;
        src1.addUser(this);
        src2.addUser(this);
    }

    @Override
    public String getText() {
        return dest.getText() + " = icmp " + op.name() + " " + src1.getFullText() + ", " + src2.getText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public static IcmpOp convert(String op) {
        return switch (op) {
            case "==" -> IcmpOp.eq;
            case "!=" -> IcmpOp.ne;
            case ">" -> IcmpOp.sgt;
            case ">=" -> IcmpOp.sge;
            case "<" -> IcmpOp.slt;
            case "<=" -> IcmpOp.sle;
            default -> throw new CodegenError("Unexpected cmp option in IR convert: " + op);
        };
    }

    @Override
    public void replaceUse(Entity old, Entity latest) {
        if (src1 == old) src1 = latest;
        if (src2 == old) src2 = latest;
    }
}
