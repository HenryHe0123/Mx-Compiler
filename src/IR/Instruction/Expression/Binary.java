package IR.Instruction.Expression;

import IR.Entity.Entity;
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
}
