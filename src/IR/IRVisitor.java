package IR;

import IR.Instruction.*;
import IR.Instruction.Terminal.*;
import IR.Instruction.Expression.*;

public interface IRVisitor {
    default void visit(IRRoot it) {
    }

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

    default void visit(Store it) {
    }

    default void visit(Load it) {
    }

    default void visit(Binary it) {
    }

    default void visit(Icmp it) {
    }

    default void visit(IRFunction it) {
    }

    default void visit(GlobalDef it) {
    }

    default void visit(Select it) {
    }

    default void visit(Call it) {
    }

    default void visit(ClassDef it) {
    }

}
