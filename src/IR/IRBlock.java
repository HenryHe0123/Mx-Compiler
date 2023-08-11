package IR;

import IR.Entity.*;
import IR.Entity.Void;
import IR.Instruction.*;
import IR.Instruction.Expression.*;
import IR.Instruction.Terminal.*;
import IR.Type.IRType;
import Util.Error.CodegenError;

import java.util.LinkedList;

public class IRBlock { //Basic Block
    public String label;
    public LinkedList<Instruction> instructions = new LinkedList<>();
    public Terminator terminator;
    public IRFunction inFunction;

    public IRBlock(String label) {
        this.label = label;
    }

    public IRBlock(String label, IRFunction inFunction) {
        this.label = label;
        this.inFunction = inFunction;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public void addInstruct(Instruction instruction) { //safer
        if (instruction instanceof Terminator) {
            if (terminator != null) throw new CodegenError("terminator already exists in block");
            terminator = (Terminator) instruction;
        } else instructions.add(instruction);
    }

    public void setTerminator(Terminator terminator) {
        this.terminator = terminator;
    }

    public String getText() {
        StringBuilder text = new StringBuilder(label + ":\n");
        for (Instruction instruction : instructions) {
            text.append("  ");
            text.append(instruction.getText());
        }
        if (terminator != null) {
            text.append("  ");
            text.append(terminator.getText());
        }
        return text.toString();
    }

    public static IRBlock newEntry(IRFunction function) {
        IRBlock entry = new IRBlock("entry", function);
        IRType type = function.returnType;
        if (!type.isVoid()) {
            Register ret = function.returnReg;
            entry.addInstruct(new Alloca(ret, type));
            entry.addInstruct(new Store(Entity.init(type), ret));
        }
        return entry;
    }

    public static IRBlock newReturn(IRFunction function) {
        IRBlock ret = new IRBlock("return", function);
        IRType type = function.returnType;
        if (type.isVoid()) {
            ret.setTerminator(new Ret(Void.instance));
        } else {
            Register tmpReg = Register.anonymous(type);
            ret.addInstruct(new Load(tmpReg, type, function.returnReg));
            ret.setTerminator(new Ret(tmpReg));
        }
        return ret;
    }
}
