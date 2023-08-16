package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.IRVisitor;
import IR.Type.IRType;
import IR.Type.VoidType;

import static IR.Instruction.IRFunction.functionReNaming;

import java.util.ArrayList;

public class Call extends Expression {
    public String funcName;
    public IRType returnType;
    public ArrayList<Entity> args = new ArrayList<>();

    public Call(Entity dest, String funcName, IRType returnType) {
        super(dest);
        this.funcName = functionReNaming(funcName);
        this.returnType = returnType;
    }

    public void addArg(Entity arg) {
        args.add(arg);
    }

    @Override
    public String getText() {
        String prefix = (dest == null) ? "" : dest.getText() + " = ";
        String argumentText = args.stream().map(Entity::getFullText).reduce((a, b) -> a + ", " + b).orElse("");
        return prefix + "call " + returnType.getText() + " @" + funcName + "(" + argumentText + ")\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public static Call callGlobalVarInit() {
        return new Call(null, "_mx_global_var_init", VoidType.IRVoid);
    }
}
