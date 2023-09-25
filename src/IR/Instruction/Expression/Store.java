package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.Entity.Register;
import IR.IRVisitor;

import java.util.LinkedList;

public class Store extends Expression {
    public Entity src;

    public Store(Entity src, Entity dest) {
        super(dest);
        this.src = src;
        src.addUser(this);
    }

    @Override
    public String getText() {
        return "store " + src.getFullText() + ", ptr " + dest.getText() + "\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void replaceUse(Entity old, Entity latest) {
        if (src == old) {
            src = latest;
            Entity.addUser(latest, this);
        }
    }

    @Override
    public LinkedList<Register> useList() {
        LinkedList<Register> list = new LinkedList<>();
        if (src instanceof Register reg) list.add(reg);
        //unlike other expression, store dest is more like a use rather than def
        if (dest instanceof Register reg) list.add(reg);
        return list;
    }

    public Register getDestReg() {
        return (dest instanceof Register reg) ? reg : null;
    }

}
