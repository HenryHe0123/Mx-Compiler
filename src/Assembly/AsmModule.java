package Assembly;

import java.util.ArrayList;

public class AsmModule {
    public ArrayList<AsmData> data = new ArrayList<>();
    public ArrayList<AsmFunction> functions = new ArrayList<>();

    public void addData(AsmData data) {
        this.data.add(data);
    }

    public void addFunction(AsmFunction function) {
        this.functions.add(function);
    }

    public String getText() {
        StringBuilder text = new StringBuilder(".text\n");
        for (AsmFunction function : functions) {
            text.append(function.getText());
        }
        for (AsmData data : data) {
            text.append(data.getText());
        }
        return text.toString();
    }
}
