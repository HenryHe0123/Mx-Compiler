package Util;

import Parser.MxParser;

public class Type {
    public String typename;
    public boolean isClass = false; //class array is also class
    public int dim = 0; //array's dimension

    public Type(String name) {
        typename = name;
        if (!typename.equals("void") && !typename.equals("int") && !typename.equals("bool") && !typename.equals("string") && !typename.equals("null"))
            isClass = true;
    }

    public Type(String name, int d) {
        typename = name;
        if (!typename.equals("void") && !typename.equals("int") && !typename.equals("bool") && !typename.equals("string") && !typename.equals("null"))
            isClass = true;
        dim = d;
    }

    public Type(Type type) {
        typename = type.typename;
        isClass = type.isClass;
        dim = type.dim;
    }

    public Type(MxParser.TypenameContext ctx) {
        if (ctx == null) return;
        else if (ctx.simpleType().Int() != null) typename = "int";
        else if (ctx.simpleType().Bool() != null) typename = "bool";
        else if (ctx.simpleType().String() != null) typename = "string";
        else {
            typename = ctx.simpleType().Identifier().getText();
            isClass = true;
        }
        dim = ctx.LBrack().size();
    }

    public Type(MxParser.ReturnTypeContext ctx) {
        if (ctx != null) {
            if (ctx.Void() != null) typename = "void";
            else { //typename
                var ctx0 = ctx.typename();
                if (ctx0 == null) return;
                else if (ctx0.simpleType().Int() != null) typename = "int";
                else if (ctx0.simpleType().Bool() != null) typename = "bool";
                else if (ctx0.simpleType().String() != null) typename = "string";
                else {
                    typename = ctx0.simpleType().Identifier().getText();
                    isClass = true;
                }
                dim = ctx0.LBrack().size();
            }
        }
    }

    public Type(MxParser.SimpleTypeContext ctx) {
        if (ctx != null) {
            if (ctx.Int() != null) typename = "int";
            else if (ctx.Bool() != null) typename = "bool";
            else if (ctx.String() != null) typename = "string";
            else {
                typename = ctx.Identifier().getText();
                isClass = true;
            }
        }
    }

    public boolean isInt() {
        return typename.equals("int") && dim == 0;
    }

    public boolean isBool() {
        return typename.equals("bool") && dim == 0;
    }

    public boolean isString() {
        return typename.equals("string") && dim == 0;
    }

    public boolean isVoid() {
        return typename.equals("void") && dim == 0;
    }

    public boolean isNull() {
        return typename.equals("null") && dim == 0;
    }

    public boolean isArray() {
        return dim > 0;
    }

    public boolean isBasic() { //build-in classes, not include null
        return isInt() || isBool() || isString();
    }

    public boolean isReference() { //support null
        return !isBasic() && !isVoid();
    }

    public boolean equals(Type type) {
        if (type == null) return false;
        if (type == this) return true;
        if (isNull() && type.isReference()) return true;
        if (type.isNull() && isReference()) return true;
        return dim == type.dim && typename.equals(type.typename);
    }

    public boolean notEquals(Type type) {
        if (type == null) return true;
        if (type == this) return false;
        if (isNull() && type.isReference()) return false;
        if (type.isNull() && isReference()) return false;
        return dim != type.dim || !typename.equals(type.typename);
    }

    static public boolean notBool(Type type) {
        return Bool.notEquals(type);
    }

    static public boolean notInt(Type type) {
        return Int.notEquals(type);
    }

    static public boolean notString(Type type) {
        return String.notEquals(type);
    }

    // built-in types -----------------------------------------------------------------

    static private final Type Int = new Type("int");
    static private final Type Bool = new Type("bool");
    static private final Type String = new Type("string");
    static private final Type Void = new Type("void");
    static private final Type Null = new Type("null");

    // new built-in types ------------------------------------------------------------
    static public Type Int() {
        return new Type(Int);
    }

    static public Type Bool() {
        return new Type(Bool);
    }

    static public Type String() {
        return new Type(String);
    }

    static public Type Void() {
        return new Type(Void);
    }

    static public Type Null() {
        return new Type(Null);
    }
}
