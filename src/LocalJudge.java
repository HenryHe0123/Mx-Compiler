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

public class LocalJudge {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    public static void testSemantic() throws Exception {
        String folderPath = "testcases/sema";
        traverseFolder(new File(folderPath));
    }

    public static void traverseFolder(File folder) throws Exception {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    traverseFolder(file);
                }
            }
        } else {
            System.out.println(YELLOW + folder.getAbsolutePath().substring(folder.getAbsolutePath().indexOf("sema\\") + 5) + ": ");
            if (check(folder.getAbsolutePath())) {
                System.out.println(GREEN + "Pass!");
            } else {
                System.out.println(RED + "Fail!");
            }
        }
    }

    public static boolean check(String fileName) throws Exception {
        boolean success = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int lineNumber = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                if (lineNumber == 6) {
                    String result = line.substring(line.indexOf(":") + 2);
                    success = result.equals("Success");
                    break; // 找到第六行后退出循环
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream input = new FileInputStream(fileName);
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
        } catch (Error er) {
            //System.err.println(er.toString());
            return !success;
        }
        return success;
    }
}

