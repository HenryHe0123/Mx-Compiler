package IR.Instruction.Terminal;

import IR.Entity.Entity;
import IR.Entity.Register;
import IR.IRVisitor;

import java.util.LinkedList;

public class Ret extends Terminator {
    public Entity returnVal;

    public Ret(Entity returnVal) {
        this.returnVal = returnVal;
        returnVal.addUser(this);
    }

    @Override
    public String getText() {
        return "ret " + returnVal.getFullText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void replaceUse(Entity old, Entity latest) {
        if (returnVal == old) {
            returnVal = latest;
            Entity.addUser(latest, this);
        }
    }

    @Override
    public LinkedList<Entity> useList() {
        LinkedList<Entity> list = new LinkedList<>();
        list.add(returnVal);
        return list;
    }
}
