package IR.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassType extends IRType { //base type
    public String className;
    public ArrayList<IRType> memberTypes = new ArrayList<>();
    public HashMap<String, Integer> memberMap = new HashMap<>(); //store index

    ClassType(String name) {
        className = name;
    }

    public void addMember(String name, IRType type) {
        memberTypes.add(type);
        memberMap.put(name, memberTypes.size() - 1);
    }

    public boolean hasMember(String name) {
        return memberMap.containsKey(name);
    }

    public IRType getMemberType(String name) {
        return hasMember(name) ? memberTypes.get(memberMap.get(name)) : null;
    }

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
}
