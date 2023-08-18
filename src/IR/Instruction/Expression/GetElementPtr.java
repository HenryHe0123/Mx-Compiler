package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.IRVisitor;
import IR.Type.IRType;

import java.util.ArrayList;
import java.util.List;

public class GetElementPtr extends Expression {
    public Entity ptr;
    public ArrayList<Entity> indexList = new ArrayList<>();
    private final IRType baseType;

    public GetElementPtr(Entity dest, Entity ptr, Entity... indexes) {
        super(dest);
        this.ptr = ptr;
        indexList.addAll(List.of(indexes));
        baseType = ptr.type.deconstruct();
    }

    public void addIndex(Entity index) {
        indexList.add(index);
    }

    @Override
    public String getText() {
        StringBuilder s = new StringBuilder(dest.getText() + " = getelementptr " + baseType.getText() + ", " + ptr.getFullText());
        for (Entity index : indexList) {
            s.append(", ").append(index.getFullText());
        }
        return s.append("\n").toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}