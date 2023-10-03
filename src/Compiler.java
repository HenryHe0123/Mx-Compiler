import java.io.*;

import AST.RootNode;
import Assembly.*;
import BackEnd.*;
import FrontEnd.*;
import IR.*;
import Optimizer.Optimizer;
import Parser.MxLexer;
import Parser.MxParser;
import Util.Error.MxError;
import Util.Scope.GlobalScope;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import Util.MxErrorListener;

public class Compiler {
    public static final boolean online = true;
    public static final boolean runRavel = true; //only for local

    public static void main(String[] args) throws Exception {
        InputStream input = System.in;
        PrintStream IROutput = null;
        PrintStream AsmOutput = System.out;

        if (!online) { //local
            input = new FileInputStream("test.mx");
            IROutput = new PrintStream(new FileOutputStream("test.ll"));
            AsmOutput = new PrintStream(new FileOutputStream("test.s"));
        }

        try {
            compile(input, IROutput, AsmOutput);
        } catch (MxError error) {
            System.err.println(error.getText());
            throw new RuntimeException();
        }

        if (!online && runRavel) ravel();
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
        Optimizer.optimize(rootIR); //so-called middle end
        new IRPrinter(IROutput).print(rootIR);

        AsmRoot rootAsm = new AsmRoot();
        new InstSelector(rootAsm).visit(rootIR);
        new RegAllocator().visit(rootAsm);
        Optimizer.optimize(rootAsm);
        new AsmPrinter(AsmOutput).print(rootAsm);
    }

    private static void ravel() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("wsl",
                    "/usr/local/opt/bin/ravel", "--oj-mode");
            processBuilder.inheritIO();
            Process process = processBuilder.start();

            long timeout = 6000;
            Thread.sleep(timeout);
            if (process.isAlive()) {
                process.destroy();
                System.err.println("ravel process forcibly terminated after " + timeout + "ms");
            }

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
