package IR.Instruction.Expression;

import IR.Entity.*;
import IR.IRVisitor;
import Util.Error.CodegenError;

import java.util.LinkedList;

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
    public Entity simplify() {
        if (src1 instanceof Int i1 && src2 instanceof Int i2) {
            int val1 = i1.getVal(), val2 = i2.getVal();
            return switch (op) {
                case eq -> new Bool(val1 == val2);
                case ne -> new Bool(val1 != val2);
                case sgt -> new Bool(val1 > val2);
                case sge -> new Bool(val1 >= val2);
                case slt -> new Bool(val1 < val2);
                case sle -> new Bool(val1 <= val2);
            };
        } else if (src1 instanceof Bool b1 && src2 instanceof Bool b2) {
            boolean val1 = b1.getVal(), val2 = b2.getVal();
            return switch (op) {
                case eq -> new Bool(val1 == val2);
                case ne -> new Bool(val1 != val2);
                default -> null;
            };
        } else if (src1 instanceof Null && src2 instanceof Null) {
            return switch (op) {
                case eq -> Bool.True;
                case ne -> Bool.False;
                default -> null;
            };
        }
        return null;
    }

    @Override
    public void replaceUse(Entity old, Entity latest) {
        if (src1 != old && src2 != old) return;
        if (src1 == old) src1 = latest;
        if (src2 == old) src2 = latest;
        Entity.addUser(latest, this);
    }

    @Override
    public LinkedList<Entity> useList() {
        LinkedList<Entity> list = new LinkedList<>();
        list.add(src1);
        list.add(src2);
        return list;
    }
}
