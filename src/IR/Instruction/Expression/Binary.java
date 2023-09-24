package IR.Instruction.Expression;

import IR.Entity.*;
import IR.IRVisitor;
import IR.Type.IRType;
import Util.Error.CodegenError;

public class Binary extends Expression {
    public enum BinaryOp {
        add, sub, mul, sdiv, srem, shl, ashr, and, or, xor
    }

    public BinaryOp op;
    public Entity src1, src2;
    public IRType type;

    public Binary(Entity dest, BinaryOp op, IRType type, Entity src1, Entity src2) {
        super(dest);
        this.op = op;
        this.type = type;
        this.src1 = src1;
        this.src2 = src2;
        src1.addUser(this);
        src2.addUser(this);
    }

    @Override
    public String getText() {
        return dest.getText() + " = " +
                op.name() + " " + type.getText() + " " + src1.getText() + ", " + src2.getText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public static BinaryOp convert(String op) {
        return switch (op) {
            case "+" -> BinaryOp.add;
            case "-" -> BinaryOp.sub;
            case "*" -> BinaryOp.mul;
            case "/" -> BinaryOp.sdiv;
            case "%" -> BinaryOp.srem;
            case "<<" -> BinaryOp.shl;
            case ">>" -> BinaryOp.ashr;
            case "&" -> BinaryOp.and;
            case "|" -> BinaryOp.or;
            case "^" -> BinaryOp.xor;
            default -> throw new CodegenError("Unexpected binary option in IR convert: " + op);
        };
    }

    public static Entity calcConstant(String op, Entity src1, Entity src2) {
        if (src1 instanceof Int i1 && src2 instanceof Int i2) {
            int val1 = i1.getVal(), val2 = i2.getVal();
            return switch (op) {
                case "+" -> new Int(val1 + val2);
                case "-" -> new Int(val1 - val2);
                case "*" -> new Int(val1 * val2);
                case "/" -> new Int(val1 / val2);
                case "%" -> new Int(val1 % val2);
                case "<<" -> new Int(val1 << val2);
                case ">>" -> new Int(val1 >> val2);
                case "&" -> new Int(val1 & val2);
                case "|" -> new Int(val1 | val2);
                case "^" -> new Int(val1 ^ val2);
                case "<" -> new Bool(val1 < val2);
                case ">" -> new Bool(val1 > val2);
                case "<=" -> new Bool(val1 <= val2);
                case ">=" -> new Bool(val1 >= val2);
                case "==" -> new Bool(val1 == val2);
                case "!=" -> new Bool(val1 != val2);
                default -> throw new CodegenError("Unexpected binary option in IR int calc: " + op);
            };
        } else if (src1 instanceof Bool b1 && src2 instanceof Bool b2) {
            boolean val1 = b1.getVal(), val2 = b2.getVal();
            return switch (op) {
                case "==" -> new Bool(val1 == val2);
                case "!=" -> new Bool(val1 != val2);
                default -> throw new CodegenError("Unexpected binary option in IR bool calc: " + op);
            };
        } else if (src1 instanceof Null && src2 instanceof Null) {
            return switch (op) {
                case "==" -> Bool.True;
                case "!=" -> Bool.False;
                default -> throw new CodegenError("Unexpected binary option in IR null calc: " + op);
            };
        }
        throw new CodegenError("Unexpected src entity type in IR constant calc for binary");
    }

    @Override
    public void replaceUse(Entity old, Entity latest) {
        if (src1 != old && src2 != old) return;
        if (src1 == old) src1 = latest;
        if (src2 == old) src2 = latest;
        Entity.addUser(latest, this);
    }
}
