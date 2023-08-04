import java.io.*;

import AST.RootNode;
import FrontEnd.*;
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

        boolean local = false;
        if (local) {
            LocalJudge.testSemantic();
            return;
        }

        //InputStream input = System.in;
        String name = "test.mx";
        InputStream input = new FileInputStream(name);

        try {
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
            System.err.println("Semantic check passed.");

        } catch (Error er) {
            System.err.println(er.toString());
            throw new RuntimeException();
        }
    }
}
