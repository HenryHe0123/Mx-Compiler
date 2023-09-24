package IR.Instruction.Terminal;

import IR.Entity.Entity;
import IR.IRVisitor;

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
}
