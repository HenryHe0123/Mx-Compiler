package FrontEnd;

import AST.*;
import AST.Def.*;
import AST.Expr.*;
import AST.Expr.primary.*;
import AST.Stmt.*;
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
            node.constructor = (StmtNode) visit(constructor);
        }
        ctx.classBody().varDef().forEach(vd -> node.varDefs.add((VarDefNode) visit(vd)));
        ctx.classBody().funcDef().forEach(fd -> node.funcDefs.add((FuncDefNode) visit(fd)));
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
}