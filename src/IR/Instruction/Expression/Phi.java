package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.Entity.Register;
import IR.IRBlock;
import IR.IRVisitor;

import java.util.ArrayList;
import java.util.LinkedList;

public class Phi extends Expression {
    public ArrayList<Entity> values = new ArrayList<>();
    public ArrayList<IRBlock> blocks = new ArrayList<>();

    public Phi(Entity dest) {
        super(dest);
    }

    public void addBranch(Entity value, IRBlock block) {
        values.add(value == null ? Entity.init(dest.type) : value);
        blocks.add(block);
        if (value != null) value.addUser(this);
    }

    @Override
    public String getText() {
        StringBuilder s = new StringBuilder(dest.getText() + " = phi " + dest.type.getText() + " ");
        for (int i = 0; i < values.size(); ++i) {
            if (i != 0) s.append(", ");
            s.append("[ ").append(values.get(i).getText()).append(", %").append(blocks.get(i).label).append(" ]");
        }
        return s.append("\n").toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void replaceUse(Entity old, Entity latest) {
        boolean flag = false;
        for (int i = 0; i < values.size(); ++i) {
            if (values.get(i) == old) {
                values.set(i, latest);
                flag = true;
            }
        }
        if (flag) Entity.addUser(latest, this);
    }

    @Override
    public LinkedList<Register> useList() {
        LinkedList<Register> list = new LinkedList<>();
        for (Entity arg : values)
            if (arg instanceof Register reg) list.add(reg);
        return list;
    }

    //----------------------for phi newly generated at mem2reg----------------------

    public Register src = null; //source alloca register

    public Phi(Entity dest, Register src) {
        super(dest);
        this.src = src;
    }
}
