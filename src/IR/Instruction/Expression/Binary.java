package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.IRVisitor;
import IR.Type.IRType;

public class Binary extends Expression {
    public enum binaryOpType {
        add, sub, mul, sdiv, srem, shl, ashr, and, or, xor
    }

    public binaryOpType op;
    public Entity src1, src2;
    public IRType type;

    public Binary(Entity dest, binaryOpType op, IRType type, Entity src1, Entity src2) {
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
}
