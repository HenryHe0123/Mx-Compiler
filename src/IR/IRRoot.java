package IR;

import IR.Instruction.*;

import java.util.ArrayList;


public class IRRoot {
    public ArrayList<Instruction> globals = new ArrayList<>();
    public ArrayList<IRFunction> functions = new ArrayList<>();

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public String getText() {
        String prefix = """
                declare void @_func_print(ptr)
                declare void @_func_println(ptr)
                declare void @_func_printInt(i32)
                declare void @_func_printlnInt(i32)
                declare ptr @_func_getString()
                declare i32 @_func_getInt()
                declare ptr @_func_toString(i32)
                declare i32 @__string.length(ptr)
                declare ptr @__string.substring(ptr, i32, i32)
                declare i32 @__string.parseInt(ptr)
                declare i32 @__string.ord(ptr, i32)
                declare ptr @__string.add(ptr, ptr)
                declare i1 @__string.lt(ptr, ptr)
                declare i1 @__string.le(ptr, ptr)
                declare i1 @__string.gt(ptr, ptr)
                declare i1 @__string.ge(ptr, ptr)
                declare i1 @__string.eq(ptr, ptr)
                declare i1 @__string.ne(ptr, ptr)
                declare ptr @__malloc(i32)
                                
                """;

        StringBuilder text = new StringBuilder();
        globals.forEach(instruction -> text.append(instruction.getText()));
        functions.forEach(func -> text.append("\n").append(func.getText()));
        return prefix + text;
    }
}
