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
        for (var Stmt : ctx.children) {
            boolean isClass = Stmt instanceof MxParser.ClassDefContext;
            boolean isFunc = Stmt instanceof MxParser.FuncDefContext;
            boolean isVar = Stmt instanceof MxParser.VarDefContext;
            if (!isClass && !isFunc && !isVar) continue; //debug: maybe EOF
            var node = visit(Stmt);
            root.defs.add(node);
            if (isClass) {
                root.classDefs.add((ClassDefNode) node);
            } else if (isFunc) {
                root.funcDefs.add((FuncDefNode) node);
            } else { //isVar
                root.varDefs.add((VarDefNode) node);
            }
        }
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
        ctx.statement().forEach(stmt -> {
            var stmtNode = (StmtNode) visit(stmt);
            if (stmtNode != null) node.stmts.add(stmtNode);
        });
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
        var condition = (ctx.forInitCondition().condition() == null) ? null :
                (ExprNode) visit(ctx.forInitCondition().condition());
        var step = (ctx.forInitCondition().step == null) ? null :
                (ExprNode) visit(ctx.forInitCondition().step);
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
        Basic value = new Basic(ctx);
        return new LiteralExprNode(new Position(ctx), value);
    }

    @Override
    public ASTNode visitVariable(MxParser.VariableContext ctx) {
        String identifier;
        if (ctx.Identifier() != null) {
            identifier = ctx.Identifier().getText();
        } else {
            identifier = "this";
        }
        return new VarExprNode(new Position(ctx), identifier);
    }

    @Override
    public ASTNode visitCallFunction(MxParser.CallFunctionContext ctx) {
        var node = new FuncExprNode(new Position(ctx), ctx.Identifier().getText());
        if (ctx.funcParameterCall() != null) {
            ctx.funcParameterCall().expression().forEach(expr -> node.args.add((ExprNode) visit(expr)));
        }
        return node;
    }

    @Override
    public ASTNode visitNewArrayExpr(MxParser.NewArrayExprContext ctx) {
        var node = new NewExprNode(new Position(ctx), new Type(ctx.simpleType()));
        node.type.dim = ctx.newArrayIndex().size();
        ctx.newArrayIndex().forEach(index -> {
            var expr = index.expression();
            ExprNode exprNode = (expr == null) ? null : (ExprNode) visit(expr);
            node.dimExpr.add(exprNode);
        });
        return node;
    }

    @Override
    public ASTNode visitNewClassExpr(MxParser.NewClassExprContext ctx) {
        return new NewExprNode(new Position(ctx), new Type(ctx.Identifier().getText()));
    }

    @Override
    public ASTNode visitUnaryExpr(MxParser.UnaryExprContext ctx) {
        String op;
        if (ctx.AddAdd() != null) op = "++";
        else if (ctx.SubSub() != null) op = "--";
        else if (ctx.Not() != null) op = "!";
        else if (ctx.Invert() != null) op = "~";
        else op = "-"; //ctx.Sub() != null
        return new UnaryExprNode(new Position(ctx), op, (ExprNode) visit(ctx.expression()));
    }

    @Override
    public ASTNode visitTernaryExpr(MxParser.TernaryExprContext ctx) {
        return new TernaryExprNode(new Position(ctx), (ExprNode) visit(ctx.expression(0)), (ExprNode) visit(ctx.expression(1)), (ExprNode) visit(ctx.expression(2)));
    }

    @Override
    public ASTNode visitArrayExpr(MxParser.ArrayExprContext ctx) {
        var node = new ArrayExprNode(new Position(ctx), (ExprNode) visit(ctx.expression(0)));
        for (int i = 1; i < ctx.expression().size(); ++i) {
            node.indexes.add((ExprNode) visit(ctx.expression(i)));
        }
        return node;
    }

    @Override
    public ASTNode visitBracketExpr(MxParser.BracketExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public ASTNode visitMemberExpr(MxParser.MemberExprContext ctx) {
        ExprNode member;
        if (ctx.variable() != null) member = (ExprNode) visit(ctx.variable());
        else member = (ExprNode) visit(ctx.callFunction());
        return new MemberExprNode(new Position(ctx), (ExprNode) visit(ctx.expression()), member);
    }

    @Override
    public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
        String op = ctx.op.getText();
        ExprNode lhs = (ExprNode) visit(ctx.expression(0));
        ExprNode rhs = (ExprNode) visit(ctx.expression(1));

        //some strange optimization from TA
        if ((op.equals("==") || op.equals("!=")) && ctx.expression(0).getText().equals(ctx.expression(1).getText())
                && (lhs instanceof ArrayExprNode || lhs instanceof MemberExprNode || lhs instanceof VarExprNode)) {
            return new LiteralExprNode(new Position(ctx), new Basic(op.equals("==")));
        }

        return new BinaryExprNode(new Position(ctx), lhs, op, rhs);
    }

    @Override
    public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {
        ExprNode lhs = (ExprNode) visit(ctx.expression(0));
        ExprNode rhs = (ExprNode) visit(ctx.expression(1));
        return new AssignExprNode(new Position(ctx), lhs, rhs);
    }

    @Override
    public ASTNode visitPostfixUpdateExpr(MxParser.PostfixUpdateExprContext ctx) {
        boolean isAdd = (ctx.AddAdd() != null);
        return new PostfixUpdateExprNode(new Position(ctx), (ExprNode) visit(ctx.expression()), isAdd);
    }

}