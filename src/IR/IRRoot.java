package IR;

import IR.Instruction.*;

import java.util.ArrayList;


public class IRRoot {
    public ArrayList<Instruction> globals = new ArrayList<>();
    public ArrayList<FunctionDef> functions = new ArrayList<>();

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public String getText() {
        StringBuilder text = new StringBuilder();
        globals.forEach(instruction -> text.append(instruction.getText()));
        functions.forEach(func -> text.append("\n").append(func.getText()));
        return text.toString();
    }
}
