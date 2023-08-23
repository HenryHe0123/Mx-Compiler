package Assembly;

public class AsmData {
    public String name;
    public int val = 0;
    public String str = null;

    public AsmData(String name, int val) {
        this.name = name;
        this.val = val;
    }

    public AsmData(String name, String str) {
        this.name = name;
        this.str = str;
    }

    public boolean isString() {
        return str != null;
    }

    public String getText() {
        boolean isString = isString();
        String dataType = isString ? "rodata" : "data";
        StringBuilder text = new StringBuilder("\t.section\t.").append(dataType).append("\n");

        String prefix = isString ? "asciz" : "word";
        text.append(name).append(":\n\t.").append(prefix).append("\t");

        if (isString) text.append("\"").append(str).append("\"");
        else text.append(val);

        return text.append("\n").toString();
    }
}
