package FrontEnd;

import AST.*;
import AST.Def.*;
import AST.Expr.*;
import AST.Expr.primary.*;
import AST.Stmt.*;
import AST.Util.ClassConstructorNode;
import AST.Util.FuncParameterNode;
import AST.Util.ParameterUnitNode;
import AST.Util.VarDeclareUnitNode;
import Util.*;
import Util.Error.SyntaxError;
import Parser.MxBaseVisitor;
import Parser.MxParser;

public class ASTBuilder extends MxBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProgram(MxParser.ProgramContext ctx) {
        RootNode root = new RootNode(new Position(ctx));
        ctx.varDef().forEach(vd -> root.varDefs.add((VarDefNode) visit(vd)));
        ctx.funcDef().forEach(fd -> root.funcDefs.add((FuncDefNode) visit(fd)));
        ctx.classDef().forEach(cd -> root.classDefs.add((ClassDefNode) visit(cd)));
        return root;
    }

    //--------------------------------------definition--------------------------------------------

    @Override
    public ASTNode visitClassDef(MxParser.ClassDefContext ctx) {
        String className = ctx.Identifier().getText();
        ClassDefNode node = new ClassDefNode(new Position(ctx), className);
        if (ctx.classBody().classConstructor().size() > 1)
            throw new SyntaxError("Class constructor number > 1", new Position(ctx));
        if (ctx.classBody().classConstructor().size() == 1) {
            var constructor = ctx.classBody().classConstructor(0);
            if (!constructor.Identifier().getText().equals(className))
                throw new SyntaxError("Class constructor name wrong", new Position(constructor));
            node.constructor = (ClassConstructorNode) visit(constructor);
        }
        ctx.classBody().varDef().forEach(vd -> node.varDefs.add((VarDefNode) visit(vd)));
        ctx.classBody().funcDef().forEach(fd -> node.funcDefs.add((FuncDefNode) visit(fd)));
        return node;
    }

    @Override
    public ASTNode visitClassConstructor(MxParser.ClassConstructorContext ctx) {
        ClassConstructorNode node = new ClassConstructorNode(new Position(ctx), ctx.Identifier().getText());
        BlockStmtNode suite = (BlockStmtNode) visit(ctx.suite());
        node.stmts = suite.stmts;
        return node;
    }

    @Override
    public ASTNode visitVarDeclareUnit(MxParser.VarDeclareUnitContext ctx) {
        VarDeclareUnitNode node = new VarDeclareUnitNode(new Position(ctx), ctx.Identifier().getText());
        if (ctx.expression() != null) node.expression = (ExprNode) visit(ctx.expression());
        return node;
    }

    @Override
    public ASTNode visitVarDef(MxParser.VarDefContext ctx) {
        VarDefNode node = new VarDefNode(new Position(ctx), new Type(ctx.typename()));
        ctx.varDeclareUnit().forEach(vdu -> node.varDeclareUnits.add((VarDeclareUnitNode) visit(vdu)));
        return node;
    }

    @Override
    public ASTNode visitFuncDef(MxParser.FuncDefContext ctx) {
        FuncParameterNode parameter = (FuncParameterNode) visit(ctx.funcParameter());
        FuncDefNode node = new FuncDefNode(new Position(ctx), new Type(ctx.returnType()), ctx.Identifier().getText(), parameter);
        BlockStmtNode block = (BlockStmtNode) visit(ctx.body);
        node.stmts = block.stmts;
        return node;
    }

    @Override
    public ASTNode visitFuncParameter(MxParser.FuncParameterContext ctx) {
        FuncParameterNode node = new FuncParameterNode(new Position(ctx));
        if (ctx.funcParameterList() != null) {
            for (int i = 0; i < ctx.funcParameterList().typename().size(); ++i) {
                Position pos = new Position(ctx.funcParameterList().typename(i));
                Type type = new Type(ctx.funcParameterList().typename(i));
                String identifier = ctx.funcParameterList().Identifier(i).getText();
                node.args.add(new ParameterUnitNode(pos, type, identifier));
            }
        }
        return node;
    }

    //--------------------------------------statement--------------------------------------------

    @Override
    public ASTNode visitSuite(MxParser.SuiteContext ctx) {
        if (ctx.statement() == null) return null; //no need to build an empty node anymore
        var node = new BlockStmtNode(new Position(ctx));
        ctx.statement().forEach(stmt -> node.stmts.add((StmtNode) visit(stmt)));
        return node;
    }

    @Override
    public ASTNode visitExprStatement(MxParser.ExprStatementContext ctx) {
        if (ctx.expression() == null) return null;
        return new ExprStmtNode(new Position(ctx), (ExprNode) visit(ctx.expression()));
    }

    @Override
    public ASTNode visitBranchStatement(MxParser.BranchStatementContext ctx) {
        var condition = (ExprNode) visit(ctx.condition());
        var ifStmt = (StmtNode) visit(ctx.ifStatement);
        var node = new BranchStmtNode(new Position(ctx), condition, ifStmt);
        if (ctx.elseStatement != null) {
            node.elseStmt = (StmtNode) visit(ctx.elseStatement);
        }
        return node;
    }

    public ASTNode visitForStatement(MxParser.ForStatementContext ctx) {
        var init = (StmtNode) visit(ctx.forInitCondition().forInit());
        var condition = (ExprNode) visit(ctx.forInitCondition().condition());
        var step = (ExprNode) visit(ctx.forInitCondition().step);
        var body = (StmtNode) visit(ctx.body);
        return new ForStmtNode(new Position(ctx), init, condition, step, body);
    }

    @Override
    public ASTNode visitWhileStatement(MxParser.WhileStatementContext ctx) {
        var condition = (ExprNode) visit(ctx.condition());
        var body = (StmtNode) visit(ctx.body);
        return new WhileStmtNode(new Position(ctx), condition, body);
    }

    @Override
    public ASTNode visitCtrlStatement(MxParser.CtrlStatementContext ctx) {
        return new CtrlStmtNode(new Position(ctx), ctx.Break() != null);
    }

    @Override
    public ASTNode visitReturnStatement(MxParser.ReturnStatementContext ctx) {
        var node = new ReturnStmtNode(new Position(ctx));
        if (ctx.expression() != null) {
            node.returnExpr = (ExprNode) visit(ctx.expression());
        }
        return node;
    }

    //--------------------------------------expression------------------------------------------

    @Override
    public ASTNode visitLiteral(MxParser.LiteralContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitVariable(MxParser.VariableContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitCallFunction(MxParser.CallFunctionContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitNewExpr(MxParser.NewExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitUnaryExpr(MxParser.UnaryExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitTernaryExpr(MxParser.TernaryExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitArrayExpr(MxParser.ArrayExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitBracketExpr(MxParser.BracketExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitMemberExpr(MxParser.MemberExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitAtomExpr(MxParser.AtomExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitPostfixUpdateExpr(MxParser.PostfixUpdateExprContext ctx) {
        return visitChildren(ctx);
    }

}