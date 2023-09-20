package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.Entity.Register;
import IR.IRVisitor;
import IR.Type.IRType;

public class Alloca extends Expression {
    public IRType type;

    public Alloca(Entity dest, IRType type) {
        super(dest);
        this.type = type;
    }

    @Override
    public String getText() {
        return dest.getText() + " = alloca " + type.getText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public Register getRegister() {
        return (dest instanceof Register reg) ? reg : null;
    }
}
