package Assembly.Instruction;

import Assembly.Operand.Operand;

public class AsmBinaryS extends Inst {
    public String op; // addi, add, sub, mul, div...
    public Operand rd, rs1, rs2;

    public AsmBinaryS(String op, Operand rd, Operand rs1, Operand rs2) {
        this.op = op;
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
    }

    @Override
    public String getText() {
        return op + "\t" + rd.getText() + ", " + rs1.getText() + ", " + rs2.getText();
    }
}
