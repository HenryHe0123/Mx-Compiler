package IR.Instruction;

import IR.Entity.Entity;
import IR.IRBlock;
import IR.IRVisitor;
import IR.Type.IRType;
import IR.Type.VoidType;

import java.util.ArrayList;
import java.util.List;

public class FunctionDef extends Instruction {
    public String name; //add _func_ as prefix to avoid conflict automatically
    public IRType returnType;
    //public Entity returnVal;

    public IRBlock returnBlock; //maybe same with entry
    public IRBlock entry;
    public ArrayList<Entity> parameters = new ArrayList<>();
    public ArrayList<IRBlock> blocks = new ArrayList<>(); //including all blocks after entry

    public FunctionDef(String name, IRType returnType, Entity... para) {
        this.name = name;
        this.returnType = returnType;
        parameters.addAll(List.of(para));
        entry = new IRBlock("entry", this);
    }

    @Override
    public String getText() {
        StringBuilder text = new StringBuilder("define " + returnType.getText() + " @_func_" + name + "(");
        for (int i = 0; i < parameters.size(); i++) {
            if (i != 0) text.append(", ");
            text.append(parameters.get(i).getFullText());
        }
        text.append(") {\n");
        text.append(entry.getText());
        for (IRBlock block : blocks) {
            text.append("\n").append(block.getText());
        }
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

    public static FunctionDef globalVarInit() {
        return new FunctionDef("_mx_global_var_Init", VoidType.IRVoid);
    }
}
