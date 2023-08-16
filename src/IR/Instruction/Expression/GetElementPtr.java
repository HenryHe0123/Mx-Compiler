package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.IRVisitor;
import IR.Type.IRType;

import java.util.ArrayList;
import java.util.List;

public class GetElementPtr extends Expression {
    public IRType baseType;
    public Entity ptrVal;
    public ArrayList<Entity> indexList = new ArrayList<>();

    public GetElementPtr(Entity dest, IRType baseType, Entity ptrVal, Entity... indexes) {
        super(dest);
        this.baseType = baseType;
        this.ptrVal = ptrVal;
        indexList.addAll(List.of(indexes));
    }

    public void addIndex(Entity index) {
        indexList.add(index);
    }

    @Override
    public String getText() {
        StringBuilder s = new StringBuilder(dest.getText() + " = getelementptr " + baseType.getText() + ", " + ptrVal.getFullText());
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
