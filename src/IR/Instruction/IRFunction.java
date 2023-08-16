package IR.Instruction;

import IR.Entity.Entity;
import IR.Entity.Register;
import IR.IRBlock;
import IR.IRVisitor;
import IR.Instruction.Terminal.Jump;
import IR.Type.IRType;
import IR.Type.VoidType;

import java.util.ArrayList;
import java.util.List;

public class IRFunction extends Instruction {
    public String name; //add _func_ as prefix to avoid conflict automatically
    public IRType returnType;
    public Register returnReg;

    public IRBlock returnBlock;
    public IRBlock entry;
    public ArrayList<Entity> parameters = new ArrayList<>();
    public ArrayList<IRBlock> blocks = new ArrayList<>(); //blocks between entry and returnBlock

    public IRFunction(String name, IRType returnType, Entity... para) {
        this.name = functionReNaming(name);
        this.returnType = returnType;
        this.returnReg = Register.retReg(returnType); //if returnType is void, returnReg is null
        parameters.addAll(List.of(para));
        entry = IRBlock.newEntry(this);
        returnBlock = IRBlock.newReturn(this);
    }

    public void addParameter(Entity para) {
        parameters.add(para);
    }

    public void addBlock(IRBlock block) {
        if (!haveBlock(block)) blocks.add(block);
    }

    @Override
    public String getText() {
        StringBuilder text = new StringBuilder("define " + returnType.getText() + " @" + name + "(");
        for (int i = 0; i < parameters.size(); i++) {
            if (i != 0) text.append(", ");
            text.append(parameters.get(i).getFullText());
        }
        text.append(") {\n");
        text.append(entry.getText());
        for (IRBlock block : blocks) {
            text.append("\n").append(block.getText());
        }
        if (returnBlock != null) text.append("\n").append(returnBlock.getText());
        text.append("}\n");
        return text.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public boolean haveBlock(IRBlock block) {
        if (entry == block) return true;
        if (returnBlock == block) return true;
        for (var blocks : blocks) {
            if (blocks == block) return true;
        }
        return false;
    }

    public static IRFunction globalVarInit() {
        IRFunction func = new IRFunction("_mx_global_var_init", VoidType.IRVoid);
        func.entry.setTerminator(new Jump(func.returnBlock));
        return func;
    }

    public boolean isVoid() {
        return returnType.isVoid();
    }

    public boolean isMain() {
        return name.equals("main");
    }

    private int labelPostfix = 0;

    public String getLabelPostfix() {
        return String.valueOf(++labelPostfix);
    }

    public static String functionReNaming(String name) {
        return (name.equals("main") || name.startsWith("__") ? "" : "_func_") + name;
    }
}
