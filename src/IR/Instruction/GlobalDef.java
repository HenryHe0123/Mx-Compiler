package IR.Instruction;

import IR.Entity.Entity;
import IR.IRVisitor;

public class GlobalDef extends Instruction {
    public boolean isStringLiteral = false;
    public Entity dest, init; //dest instanceof globalVar

    public GlobalDef(Entity dest, Entity init) {
        this.dest = dest;
        this.init = init;
    }

    public GlobalDef(Entity dest, Entity init, boolean isStringLiteral) {
        this.dest = dest;
        this.init = init;
        this.isStringLiteral = isStringLiteral;
    }

    @Override
    public String getText() {
        String rhs = (isStringLiteral ? "" : "global ") + init.getFullText();
        return dest.getText() + " = " + rhs + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
