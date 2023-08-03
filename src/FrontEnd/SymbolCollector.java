package FrontEnd;

import AST.*;
import AST.Def.*;
import Util.Error.SyntaxError;
import Util.Scope.GlobalScope;

public class SymbolCollector implements ASTVisitor {
    private final GlobalScope gScope;

    public SymbolCollector(GlobalScope globalScope) {
        gScope = globalScope;
    }

    @Override
    public void visit(RootNode node) {
        node.classDefs.forEach(this::visit);
        node.funcDefs.forEach(this::visit);
        //do nothing for varDefs, as global variables don't support forward reference
        var mainFn = gScope.getFuncDefNode("main");
        if (mainFn == null || !mainFn.type.isInt() || mainFn.parameter.args.size() > 0) {
            throw new SyntaxError("invalid main function", node.pos);
        }
    }

    @Override
    public void visit(FuncDefNode node) {
        gScope.addFunc(node.identifier, node);
    }

    @Override
    public void visit(ClassDefNode node) {
        gScope.addClass(node.identifier, node);
    }
}
