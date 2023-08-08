import java.io.*;

import AST.RootNode;
import FrontEnd.*;
import IR.IRRoot;
import Parser.MxLexer;
import Parser.MxParser;
import Util.Error.Error;
import Util.Scope.GlobalScope;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import Util.MxErrorListener;

public class Compiler {
    public static void main(String[] args) throws Exception {
        InputStream input = System.in;
        boolean online = true;

        if (!online) { //local
            input = new FileInputStream("test.mx");
        }

        try {
            compile(input);
        } catch (Error er) {
            System.err.println(er.getText());
            throw new RuntimeException();
        }
    }

    public static void compile(InputStream input) throws Exception {
        GlobalScope globalScope = new GlobalScope();
        globalScope.initialize();

        MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
        lexer.removeErrorListeners();
        lexer.addErrorListener(new MxErrorListener());

        MxParser parser = new MxParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(new MxErrorListener());
        ParseTree parseTreeRoot = parser.program();

        ASTBuilder astBuilder = new ASTBuilder();
        RootNode ASTRoot = (RootNode) astBuilder.visit(parseTreeRoot);

        new SymbolCollector(globalScope).visit(ASTRoot);
        new FrontEnd.SemanticChecker(globalScope).visit(ASTRoot);

        IRRoot rootIR = new IRRoot();
        //new IRBuilder(globalScope, rootIR).visit(ASTRoot);
    }
}
