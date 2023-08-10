package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.IRVisitor;
import IR.Type.IRType;

public class Load extends Expression {
    public IRType type;
    public Entity src; //ptr

    public Load(Entity dest, IRType type, Entity src) {
        super(dest);
        this.type = type;
        this.src = src;
    }

    @Override
    public String getText() {
        return dest.getText() + " = load " + type.getText() + ", ptr " + src.getText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
