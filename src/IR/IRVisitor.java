package IR;

import IR.Instruction.Terminal.*;
import IR.Instruction.Expression.*;

public interface IRVisitor {
    default void visit(IRBlock it) {
    }

    default void visit(Ret it) {
    }

    default void visit(Jump it) {
    }

    default void visit(Branch it) {
    }

    default void visit(Alloca it) {
    }

}
