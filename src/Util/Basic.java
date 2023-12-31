package Util;

import Parser.MxParser;

public class Basic { //unified basic type of Mx (including null)
    public boolean boolVal = false;
    public long intVal = 0; //use larger int scope to handle the problem of -2147483648
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

    public Basic(long intVal) {
        this.intVal = intVal;
        isInt = true;
    }

    public Basic(String stringVal) {
        this.stringVal = stringVal;
    }

    public Basic(MxParser.LiteralContext ctx) {
        if (ctx.IntLiteral() != null) {
            intVal = Long.parseLong(ctx.IntLiteral().getText());
            isInt = true;
        } else if (ctx.StringLiteral() != null) {
            stringVal = ctx.StringLiteral().getText();
            stringVal = stringVal.substring(1, stringVal.length() - 1); //remove the quotation marks
        } else if (ctx.False() != null) {
            boolVal = false;
            isBool = true;
        } else if (ctx.True() != null) {
            boolVal = true;
            isBool = true;
        } else {
            isNull = true;
        }
    }

    public boolean equals(Basic basic) {
        if (basic == null) return false;
        if (basic == this) return true;
        if (isNull && basic.isNull) return true;
        if (isBool && basic.isBool) return boolVal == basic.boolVal;
        if (isInt && basic.isInt) return intVal == basic.intVal;
        if (isString() && basic.isString()) return stringVal.equals(basic.stringVal);
        return false;
    }

    public Type type() {
        if (isNull) return new Type("null");
        if (isBool) return new Type("bool");
        if (isInt) return new Type("int");
        return new Type("string");
    }
}
