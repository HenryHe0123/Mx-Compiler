import java.io.*;

import AST.RootNode;
import Assembly.AsmModule;
import BackEnd.*;
import FrontEnd.*;
import IR.*;
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
        PrintStream IROutput = null;
        PrintStream AsmOutput = System.out;
        boolean online = false;

        if (!online) { //local
            input = new FileInputStream("test.mx");
            IROutput = new PrintStream(new FileOutputStream("test.ll"));
            AsmOutput = new PrintStream(new FileOutputStream("test.s"));
            //AsmOutput = null;
        }

        try {
            compile(input, IROutput, AsmOutput);
        } catch (Error er) {
            System.err.println(er.getText());
            throw new RuntimeException();
        }
    }

    public static void compile(InputStream input, PrintStream IROutput, PrintStream AsmOutput) throws Exception {
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
        new SemanticChecker(globalScope).visit(ASTRoot);

        IRRoot rootIR = new IRRoot();
        new IRBuilder(globalScope, rootIR).visit(ASTRoot);
        new IRPrinter(IROutput).print(rootIR);

        AsmModule asmModule = new AsmModule();
        new AsmBuilder(asmModule).visit(rootIR);
        new AsmPrinter(AsmOutput).print(asmModule);
    }
}
