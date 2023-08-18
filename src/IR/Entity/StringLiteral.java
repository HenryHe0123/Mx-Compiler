package IR.Entity;

import IR.Type.PtrType;

public class StringLiteral extends Entity {
    public String str;

    public StringLiteral(String s) {
        super(PtrType.IRStringLiteral);
        str = s;
    }

    public int size() {
        return str.length() + 1 - escCount(str);
    }

    @Override
    public String getText() {
        return "c\"" + convertForIR(str) + "\\00\""; //ex: c"hello\00"
    }

    @Override
    public String getFullText() {
        return "private unnamed_addr constant [" + size() + " x i8] " + getText();
    }

    public static String convertForIR(String s) {
        StringBuilder conversion = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '\\') { //look ahead
                char next = s.charAt(i + 1);
                if (next == 'n') { // \n
                    conversion.append("\\0A");
                    ++i;
                } else if (next == '\\') { // \\
                    conversion.append("\\\\");
                    ++i;
                } else if (next == '\"') { // \"
                    conversion.append("\\22");
                    ++i;
                } else conversion.append(c);
            } else conversion.append(c);
        }
        return conversion.toString();
    }

    public static int escCount(String s) {
        int cnt = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '\\') { //look ahead
                char next = s.charAt(i + 1);
                if (next == 'n' || next == '\\' || next == '\"') {
                    ++cnt;
                    ++i;
                }
            }
        }
        return cnt;
    }
}
