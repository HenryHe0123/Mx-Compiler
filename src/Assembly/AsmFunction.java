package Assembly;

import java.util.ArrayList;

public class AsmFunction {
    public String name;
    public ArrayList<AsmBlock> blocks = new ArrayList<>();

    public AsmFunction(String funcName) {
        name = funcName;
    }

    public void addBlock(AsmBlock block) {
        blocks.add(block);
    }

    public String getText() {
        StringBuilder text = new StringBuilder("\t.globl  " + name + "\n");
        text.append("\t.type   ").append(name).append(",@function\n");
        //text.append(name).append(":\n");
        for (AsmBlock block : blocks) {
            text.append(block.getText());
        }
        return text.toString();
    }

    private static int cnt = 0;
    public final int id = cnt++;
}


