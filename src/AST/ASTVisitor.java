package AST;

import AST.Def.*;
import AST.Stmt.*;
import AST.Util.*;

public interface ASTVisitor {
    void visit(RootNode node);

    void visit(VarDefNode node);

    void visit(VarDeclareUnitNode node);

    void visit(FuncDefNode node);

    void visit(BlockStmtNode node);

    void visit(ClassDefNode node);

    void visit(BranchStmtNode node);

    void visit(ExprStmtNode node);

    void visit(WhileStmtNode node);

    void visit(ForStmtNode node);

    void visit(ReturnStmtNode node);

    void visit(CtrlStmtNode node);
}
