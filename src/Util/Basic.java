package Util;

public class Basic { //unified basic type of Mx
    public boolean boolVal = false;
    public int intVal = 0;
    public String stringVal = null;
    public boolean isNull = false;
    public boolean isBool = false;
    public boolean isInt = false;

    public boolean isString() {
        return !isNull && !isBool && !isInt;
    }

    public Basic() {
        isNull = true;
    }

    public Basic(boolean boolVal) {
        this.boolVal = boolVal;
        isBool = true;
    }

    public Basic(int intVal) {
        this.intVal = intVal;
        isInt = true;
    }

    public Basic(String stringVal) {
        this.stringVal = stringVal;
    }

    public Type type() {
        if (isNull) return new Type("null");
        if (isBool) return new Type("bool");
        if (isInt) return new Type("int");
        return new Type("String");
    }
}
