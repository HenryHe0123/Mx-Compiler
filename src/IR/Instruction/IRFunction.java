package IR.Instruction;

import IR.Entity.Entity;
import IR.Entity.Register;
import IR.IRBlock;
import IR.IRVisitor;
import IR.Type.IRType;
import IR.Type.VoidType;

import java.util.ArrayList;
import java.util.LinkedList;
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

    public static String varInitFuncName = "_mx_global_var_init";

    public static String renamedVarInitFuncName = functionReNaming(varInitFuncName);

    public static IRFunction globalVarInit() {
        return new IRFunction(varInitFuncName, VoidType.IRVoid);
    }

    public boolean isVoid() {
        return returnType.isVoid();
    }

    public boolean isMain() {
        return name.equals("main");
    }

    public boolean isUseless() {
        return isVoid() && blocks.isEmpty() && entry.instructions.isEmpty()
                && returnBlock.instructions.isEmpty();
    }

    private int labelPostfix = 0;

    public String getLabelPostfix() {
        return String.valueOf(++labelPostfix);
    }

    public static String functionReNaming(String name) {
        return (name.equals("main") || name.startsWith("__") ? "" : "_func_") + name;
    }

    //------------------------- optimize -------------------------

    private LinkedList<IRBlock> allBlocks = null;

    private void calcAllBlocks() {
        allBlocks = new LinkedList<>(blocks);
        allBlocks.addFirst(entry);
        allBlocks.addLast(returnBlock);
    }

    public LinkedList<IRBlock> allBlocks() {
        if (allBlocks == null) calcAllBlocks();
        return allBlocks;
    }

    public boolean removeBlock(IRBlock block) {
        if (blocks.remove(block)) {
            calcAllBlocks();
            return true;
        } else return false;
    }

    public void buildCFG() {
        entry.linkCFG();
        for (IRBlock block : blocks) block.linkCFG();
        returnBlock.linkCFG();
        eliminateBlocks();
    }

    private void eliminateBlocks() {
        ArrayList<IRBlock> newBlocks = new ArrayList<>();
        for (IRBlock block : blocks)
            if (!block.prev.isEmpty()) newBlocks.add(block);
            else for (IRBlock b : block.next) b.prev.remove(block);
        blocks = newBlocks;
    }
}
