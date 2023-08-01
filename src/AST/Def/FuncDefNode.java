package AST.Def;

import AST.*;
import Util.*;

import java.util.ArrayList;

public class FuncDefNode extends ASTNode {
    public Type type;
    public String identifier;
    public VarDefNode parameter;
    public ArrayList<StmtNode> stmts = new ArrayList<>();

    public FuncDefNode(Position pos) {
        super(pos);
    }

    public FuncDefNode(Position pos, Type type, String id, VarDefNode parameter) {
        super(pos);
        identifier = id;
        this.type = type;
        this.parameter = parameter;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
