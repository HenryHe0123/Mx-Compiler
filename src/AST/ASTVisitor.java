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
}
