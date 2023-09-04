package Assembly;

import java.io.PrintStream;

public class AsmPrinter {
    private final PrintStream out;

    public AsmPrinter(PrintStream out) {
        this.out = out;
    }

    public void print(AsmRoot root) {
        if (out == null) return;
        out.print(root.getText());
    }
}
