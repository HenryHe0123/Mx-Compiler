import java.io.*;

import Parser.MxLexer;
import Parser.MxParser;
import Util.Error.Error;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import Util.MxErrorListener;

public class Compiler {
    public static void main(String[] args) throws Exception {
        InputStream input = System.in;

        try {
            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());

            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());

            ParseTree parseTreeRoot = parser.program();

        } catch (Error er) {
            System.err.println(er.toString());
            throw new RuntimeException();
        }
    }
}
