package Util;

import Parser.MxParser;

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

    public boolean isBasic() {
        return dim == 0 && !isClass;
    }

    public boolean isArray() {
        return dim > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Type type)) return false;
        return dim == type.dim && typename.equals(type.typename);
    }
}
