package Util;

public class Type {
    public String typename;
    public boolean isClass = false;
    public int dim = 0; //array's dimension

    public Type() {
    }

    public Type(String name) {
        typename = name;
        if (!typename.equals("void") && !typename.equals("int") && !typename.equals("bool")
                && !typename.equals("string") && !typename.equals("null")) isClass = true;
    }

    public Type(String name, int d) {
        typename = name;
        if (!typename.equals("void") && !typename.equals("int") && !typename.equals("bool")
                && !typename.equals("string") && !typename.equals("null")) isClass = true;
        dim = d;
    }

    public boolean isBasic() {
        return dim == 0 && !isClass;
    }

}
