package IR.Instruction.Terminal;

import IR.*;
import IR.Entity.Entity;
import IR.Entity.Register;

import java.util.LinkedList;

public class Branch extends Terminator {
    public Entity cond;
    public IRBlock branchTrue, branchFalse;

    public Branch(Entity condition, IRBlock branchTrue, IRBlock branchFalse) {
        cond = condition;
        this.branchTrue = branchTrue;
        this.branchFalse = branchFalse;
        cond.addUser(this);
    }

    @Override
    public String getText() {
        return "br i1 " + cond.getText() + ", label %" + branchTrue.label + ", label %" + branchFalse.label + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void replaceUse(Entity old, Entity latest) {
        if (cond == old) {
            cond = latest;
            Entity.addUser(latest, this);
        }
    }

    @Override
    public LinkedList<Entity> useList() {
        LinkedList<Entity> list = new LinkedList<>();
        list.add(cond);
        return list;
    }
}
