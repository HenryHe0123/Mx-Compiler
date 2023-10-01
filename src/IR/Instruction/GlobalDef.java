package IR.Instruction;

import IR.Entity.*;
import IR.IRVisitor;

import java.util.LinkedList;

public class GlobalDef extends Instruction {
    public boolean isStringLiteral = false;
    public GlobalVar dest;
    public Entity init;

    public GlobalDef(GlobalVar dest, Entity init) {
        this.dest = dest;
        this.init = init;
    }

    public GlobalDef(GlobalVar dest, Entity init, boolean isStringLiteral) {
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
    public void replaceUse(Entity old, Entity latest) {
        if (init == old) {
            init = latest;
            Entity.addUser(latest, this);
        }
    }

    @Override
    public LinkedList<Entity> useList() {
        LinkedList<Entity> list = new LinkedList<>();
        list.add(init);
        return list;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public boolean isSimple() {
        return dest.isSimple && init.isStrictlyConstant(); //double check
    }
}
