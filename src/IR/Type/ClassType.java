package IR.Type;

import Util.Error.CodegenError;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassType extends IRType { //base type
    public String className;
    public final ArrayList<IRType> memberTypes = new ArrayList<>();
    public final HashMap<String, Integer> memberIndexes = new HashMap<>();

    ClassType(String name) {
        className = name;
    }

    public void addMember(String name, IRType type) {
        memberTypes.add(type);
        memberIndexes.put(name, memberTypes.size() - 1);
    }

    public boolean hasMember(String name) {
        return memberIndexes.containsKey(name);
    }

    public int getMemberIndex(String name) {
        if (hasMember(name)) return memberIndexes.get(name);
        throw new CodegenError("Class " + className + " has no member named " + name);
    }

    public IRType getMemberType(String name) {
        return hasMember(name) ? memberTypes.get(memberIndexes.get(name)) : null;
    }

    public IRType getMemberType(int index) {
        return memberTypes.get(index);
    }

    @Override
    public String asPrefix() {
        return "__" + className + ".";
    }

    @Override
    public String getText() {
        return "%class." + className;
    }

    @Override
    public int getBytes() {
        return memberTypes.size() << 2;
    }

    public String getConstructorName() {
        return asPrefix() + className;
    }
}
