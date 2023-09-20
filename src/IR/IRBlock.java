package IR;

import IR.Entity.*;
import IR.Entity.Void;
import IR.Instruction.*;
import IR.Instruction.Expression.*;
import IR.Instruction.Terminal.*;
import IR.Type.IRType;

import java.util.ArrayList;
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

    public void addInstruct(Instruction instruction) {
        if (instruction instanceof Terminator) {
            trySetTerminator((Terminator) instruction); //conservative
        } else instructions.add(instruction);
    }

    public void setTerminator(Terminator terminator) { //use for explicit setting
        this.terminator = terminator;
    }

    public void trySetTerminator(Terminator terminator) { //use for uncertain setting
        if (this.terminator == null) this.terminator = terminator;
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
            ret.addInstruct(new Load(tmpReg, function.returnReg));
            ret.setTerminator(new Ret(tmpReg));
        }
        return ret;
    }

    //------------------------- optimize -------------------------

    public LinkedList<IRBlock> prev = new LinkedList<>();
    public LinkedList<IRBlock> next = new LinkedList<>();
    public IRBlock idom = null; //immediate dominator, father in dominator tree
    public ArrayList<IRBlock> domChildren = new ArrayList<>();
    public ArrayList<IRBlock> domFrontier = new ArrayList<>();

    public void addCFGEdge(IRBlock block) { //this -> block
        this.next.add(block);
        block.prev.add(this);
    }

    public void linkCFG() {
        if (terminator instanceof Jump) {
            addCFGEdge(((Jump) terminator).target);
        } else if (terminator instanceof Branch) {
            addCFGEdge(((Branch) terminator).branchTrue);
            addCFGEdge(((Branch) terminator).branchFalse);
        }
    }

    public void addDomChildren(IRBlock block) {
        domChildren.add(block);
    }

    public void addDomFrontier(IRBlock block) {
        domFrontier.add(block);
    }

    public LinkedList<Phi> phis = new LinkedList<>();

    public void addPhi(Phi phi) {
        phis.add(phi);
    }

    public void collectAndSimplifyPhi() {
        //todo: simplify
        for (int i = phis.size() - 1; i >= 0; --i) {
            instructions.addFirst(phis.get(i));
        }
    }
}
