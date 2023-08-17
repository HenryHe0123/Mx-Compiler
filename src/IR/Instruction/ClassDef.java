package IR.Instruction;

import IR.IRVisitor;
import IR.Type.ClassType;
import IR.Type.IRType;

public class ClassDef extends Instruction {
    public ClassType classType;

    public ClassDef(ClassType classType) {
        this.classType = classType;
    }

    public int getBytes() {
        return classType.getBytes();
    }

    @Override
    public String getText() {
        String memberText = classType.memberTypes.stream().map(IRType::getText).reduce((a, b) -> a + ", " + b).orElse("");
        return classType.getText() + " = type { " + memberText + " }\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
