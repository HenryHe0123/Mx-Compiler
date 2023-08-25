package Assembly;

public class AsmData {
    public String name;
    public int val = 0;
    public String str = null;
    private final boolean isString; //for label

    public AsmData(String name, int val) {
        this.name = name;
        this.val = val;
        isString = false;
    }

    public AsmData(String name, String str) {
        this.name = name;
        this.str = str;
        isString = true;
    }

    public AsmData(String name, String str, boolean isString) {
        this.name = name;
        this.str = str;
        this.isString = isString;
    }

    public String getText() {
        String dataType = isString ? "rodata" : "data";
        StringBuilder text = new StringBuilder("\t.section\t.").append(dataType).append("\n");

        String prefix = isString ? "asciz" : "word";
        text.append(name).append(":\n\t.").append(prefix).append("\t");

        if (isString) text.append("\"").append(str).append("\"");
        else if (str != null) text.append(str); //label
        else text.append(unsignedIntVal());

        return text.append("\n").toString();
    }

    public long unsignedIntVal() {
        return val & 0xFFFFFFFFL;
    }
}
