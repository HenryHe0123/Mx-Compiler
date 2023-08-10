package IR;

import java.io.PrintStream;

public class IRPrinter {
    private final PrintStream out;

    public IRPrinter(PrintStream out) {
        this.out = out;
    }

    public void print(IRRoot root) {
        out.println(root.getText());
    }
}
