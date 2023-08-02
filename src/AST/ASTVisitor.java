package AST;

import AST.Def.*;
import AST.Expr.*;
import AST.Expr.primary.*;
import AST.Stmt.*;
import AST.Util.*;

public interface ASTVisitor {
    void visit(RootNode node);

    void visit(VarDefNode node);

    void visit(VarDeclareUnitNode node);

    void visit(FuncDefNode node);

    void visit(FuncParameterNode node);

    void visit(ParameterUnitNode node);

    void visit(BlockStmtNode node);

    void visit(ClassDefNode node);

    void visit(ClassConstructorNode node);

    void visit(BranchStmtNode node);

    void visit(ExprStmtNode node);

    void visit(WhileStmtNode node);

    void visit(ForStmtNode node);

    void visit(ReturnStmtNode node);

    void visit(CtrlStmtNode node);

    void visit(BinaryExprNode node);

    void visit(VarExprNode node);

    void visit(LiteralExprNode node);

    void visit(FuncExprNode node);

    void visit(MemberExprNode node);

    void visit(ArrayExprNode node);

    void visit(PostfixUpdateExprNode node);

    void visit(UnaryExprNode node);

    void visit(NewExprNode node);

    void visit(AssignExprNode node);

    void visit(TernaryExprNode node);
}
