package AST;

import AST.Def.*;
import AST.Expr.*;
import AST.Expr.primary.*;
import AST.Stmt.*;
import AST.Util.*;

public interface ASTVisitor {
    default void visit(RootNode node) {
    }

    default void visit(VarDefNode node) {
    }

    default void visit(VarDeclareUnitNode node) {
    }

    default void visit(FuncDefNode node) {
    }

    default void visit(FuncParameterNode node) {
    }

    default void visit(ParameterUnitNode node) {
    }

    default void visit(BlockStmtNode node) {
    }

    default void visit(ClassDefNode node) {
    }

    default void visit(ClassConstructorNode node) {
    }

    default void visit(BranchStmtNode node) {
    }

    default void visit(ExprStmtNode node) {
    }

    default void visit(WhileStmtNode node) {
    }

    default void visit(ForStmtNode node) {
    }

    default void visit(ReturnStmtNode node) {
    }

    default void visit(CtrlStmtNode node) {
    }

    default void visit(BinaryExprNode node) {
    }

    default void visit(VarExprNode node) {
    }

    default void visit(LiteralExprNode node) {
    }

    default void visit(FuncExprNode node) {
    }

    default void visit(MemberExprNode node) {
    }

    default void visit(ArrayExprNode node) {
    }

    default void visit(PostfixUpdateExprNode node) {
    }

    default void visit(UnaryExprNode node) {
    }

    default void visit(NewExprNode node) {
    }

    default void visit(AssignExprNode node) {
    }

    default void visit(TernaryExprNode node) {
    }
}
