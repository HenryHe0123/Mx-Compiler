package FrontEnd;

import AST.*;
import Util.Scope.*;

public class SemanticChecker implements ASTVisitor {
    private final GlobalScope gScope;
    private Scope currentScope;

    public SemanticChecker(GlobalScope globalScope) {
        this.gScope = globalScope;
        currentScope = globalScope;
    }

}
