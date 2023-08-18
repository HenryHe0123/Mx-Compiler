package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.IRBlock;
import IR.IRVisitor;

import java.util.ArrayList;

public class Phi extends Expression {
    public ArrayList<Entity> values = new ArrayList<>();
    public ArrayList<IRBlock> blocks = new ArrayList<>();

    public Phi(Entity dest) {
        super(dest);
    }

    public void addBranch(Entity value, IRBlock block) {
        values.add(value == null ? Entity.init(dest.type) : value);
        blocks.add(block);
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
}
