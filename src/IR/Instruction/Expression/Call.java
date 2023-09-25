package IR.Instruction.Expression;

import IR.Entity.Entity;
import IR.Entity.Register;
import IR.IRVisitor;
import IR.Type.IRType;
import IR.Type.VoidType;

import static IR.Instruction.IRFunction.functionReNaming;

import java.util.ArrayList;
import java.util.LinkedList;

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
        arg.addUser(this);
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

    @Override
    public void replaceUse(Entity old, Entity latest) {
        boolean flag = false;
        for (int i = 0; i < args.size(); ++i) {
            if (args.get(i) == old) {
                args.set(i, latest);
                flag = true;
            }
        }
        if (flag) Entity.addUser(latest, this);
    }

    @Override
    public LinkedList<Register> useList() {
        LinkedList<Register> list = new LinkedList<>();
        for (Entity arg : args)
            if (arg instanceof Register reg) list.add(reg);
        return list;
    }
}
