package FrontEnd;

import AST.*;
import AST.Def.*;
import AST.Expr.*;
import AST.Expr.primary.*;
import AST.Stmt.*;
import AST.Util.*;
import Util.Error.SemanticError;
import Util.*;
import Util.Scope.*;

public class SemanticChecker implements ASTVisitor {
    private final GlobalScope gScope;
    private Scope currentScope;
    private Type currentType = null; //for varDefStatement
    private int inLoop = 0; //for loopStatement

    public SemanticChecker(GlobalScope globalScope) {
        this.gScope = globalScope;
        currentScope = globalScope;
    }

    @Override
    public void visit(RootNode node) {
        node.defs.forEach(def -> def.accept(this));
    }

    public void checkType(Type type, Position pos) {
        if (type.isClass)
            if (!gScope.hasClass(type.typename))
                throw new SemanticError("undefined class type " + type.typename, pos);
    }

    public void implFuncReturn(FuncDefNode node) { //implement the omitted return statement for IR
        if (currentScope.isReturned) return;
        if (node.type.isVoid()) {
            node.stmts.add(new ReturnStmtNode(Position.none));
            currentScope.isReturned = true;
        } else if (node.identifier.equals("main")) {
            node.stmts.add(new ReturnStmtNode(Position.none, new LiteralExprNode(Position.none, new Basic(0))));
            currentScope.isReturned = true;
        }
    }

    public void implConstructorReturn(ClassConstructorNode node) {
        if (currentScope.isReturned) return;
        node.stmts.add(new ReturnStmtNode(Position.none));
        currentScope.isReturned = true;
    }

    // ------------------------------ definition ------------------------------

    @Override
    public void visit(ClassDefNode node) {
        if (node.isBuiltIn()) throw new SemanticError("unexpected redefine of built-in class", node.pos);
        currentScope = new Scope(currentScope, node);
        node.varDefs.forEach(def -> def.accept(this));
        node.constructor.accept(this);
        node.funcDefs.forEach(def -> def.accept(this));
        currentScope = currentScope.getParent();
    }

    @Override
    public void visit(FuncDefNode node) {
        checkType(node.type, node.pos);
        currentScope = new Scope(currentScope, node.type);
        if (node.parameter != null) node.parameter.args.forEach(arg -> arg.accept(this));
        node.stmts.forEach(stmt -> stmt.accept(this));
        if (!node.type.isVoid() && !currentScope.isReturned && !node.identifier.equals("main"))
            throw new SemanticError("miss return statement in function " + node.identifier, node.pos);
        implFuncReturn(node); //for IR
        currentScope = currentScope.getParent();
    }

    @Override
    public void visit(ParameterUnitNode node) {
        checkType(node.type, node.pos);
        currentScope.defineVariable(node.identifier, node.type, node.pos);
    }

    @Override
    public void visit(VarDefNode node) {
        checkType(node.type, node.pos);
        currentType = node.type;
        node.varDeclareUnits.forEach(unit -> unit.accept(this));
        currentType = null;
    }

    @Override
    public void visit(VarDeclareUnitNode node) {
        if (node.expression != null) {
            node.expression.accept(this);
            if (node.expression.type.notEquals(currentType))
                throw new SemanticError("type mismatch in var declaration", node.pos);
        }
        currentScope.defineVariable(node.identifier, currentType, node.pos);
    }

    @Override
    public void visit(ClassConstructorNode node) {
        currentScope = new Scope(currentScope, Type.Void());
        node.stmts.forEach(stmt -> stmt.accept(this));
        //return check in returnStmt visit (just like void function)
        implConstructorReturn(node); //for IR
        currentScope = currentScope.getParent();
    }

    // ------------------------------ statement ------------------------------

    @Override
    public void visit(ReturnStmtNode node) {
        for (var scope = currentScope; scope != null; scope = scope.getParent()) {
            if (scope.inFunction()) { //back to the function scope
                if (node.returnExpr == null) { //return void
                    if (!scope.returnType.isVoid())
                        throw new SemanticError("type mismatch in return statement", node.pos);
                } else {
                    node.returnExpr.accept(this);
                    if (scope.returnType.notEquals(node.returnExpr.type))
                        throw new SemanticError("type mismatch in return statement", node.pos);
                }
                scope.isReturned = true;
                return;
            }
        }
        throw new SemanticError("unexpected return in non-function", node.pos);
    }

    @Override
    public void visit(BlockStmtNode node) {
        currentScope = new Scope(currentScope);
        node.stmts.forEach(stmt -> stmt.accept(this));
        currentScope = currentScope.getParent();
    }

    @Override
    public void visit(BranchStmtNode node) {
        node.condition.accept(this);
        if (Type.notBool(node.condition.type))
            throw new SemanticError("branch statement condition not bool", node.pos);
        currentScope = new Scope(currentScope);
        if (node.ifStmt != null) node.ifStmt.accept(this);
        currentScope = currentScope.getParent();
        if (node.elseStmt != null) {
            currentScope = new Scope(currentScope);
            node.elseStmt.accept(this);
            currentScope = currentScope.getParent();
        }
    }

    @Override
    public void visit(WhileStmtNode node) {
        node.condition.accept(this);
        if (!node.condition.type.isBool())
            throw new SemanticError("while statement condition not bool", node.pos);
        currentScope = new Scope(currentScope);
        inLoop++;
        if (node.body != null) node.body.accept(this);
        currentScope = currentScope.getParent();
        inLoop--;
    }

    @Override
    public void visit(ForStmtNode node) {
        currentScope = new Scope(currentScope);
        inLoop++;
        if (node.init != null) node.init.accept(this);
        if (node.condition != null) {
            node.condition.accept(this);
            if (!node.condition.type.isBool())
                throw new SemanticError("for statement condition not bool", node.pos);
        }
        if (node.step != null) node.step.accept(this);
        if (node.body != null) node.body.accept(this);
        currentScope = currentScope.getParent();
        inLoop--;
    }

    @Override
    public void visit(CtrlStmtNode node) {
        if (inLoop == 0) throw new SemanticError("control statement not in loop", node.pos);
    }

    @Override
    public void visit(ExprStmtNode node) {
        if (node.expression != null) node.expression.accept(this);
    }

    // ------------------------------ expression ------------------------------

    @Override
    public void visit(VarExprNode node) {
        if (node.isThis()) { //this must be used in class method
            for (var scope = currentScope.getParent(); scope != null; scope = scope.getParent()) {
                if (scope.inClass != null) { //back to the class scope
                    node.type = new Type(scope.inClass.identifier);
                    return;
                }
            }
            throw new SemanticError("this used out of class method", node.pos);
        } else { //normal variable
            node.type = currentScope.getType(node.identifier, true);
            if (node.type == null)
                throw new SemanticError("undefined variable " + node.identifier, node.pos);
        }
    }

    @Override
    public void visit(FuncExprNode node) {
        //only check global function, class method is checked in memberExpr
        node.args.forEach(arg -> arg.accept(this));
        Type type = gScope.getCallGlobalFuncType(node);
        if (type == null) {
            //debug: maybe in class environment and call class method
            var inClass = currentScope.insideClass();
            if (inClass == null)
                throw new SemanticError("call global function " + node.funcName + " failed", node.pos);
            type = gScope.getCallMethodType(new Type(inClass.identifier), node);
            if (type == null)
                throw new SemanticError("call function " + node.funcName + " in class environment failed", node.pos);
        }
        node.type = type;
    }

    @Override
    public void visit(MemberExprNode node) {
        node.caller.accept(this);
        Type classType = node.caller.type;
        String className = classType.typename;
        if (node.dotFunc()) {
            var method = (FuncExprNode) node.member;
            method.args.forEach(arg -> arg.accept(this)); //debug: don't forget to visit args first
            Type type = gScope.getCallMethodType(classType, method);
            if (type == null) {
                //System.err.println("method name: " + method.funcName + ", arg size: " + method.args.size());
                throw new SemanticError("call class method " + className + "." + method.funcName + " failed", node.pos);
            }
            node.type = type;
            node.member.type = type;
            return;
        }
        if (node.dotVar()) {
            if (classType.isArray())
                throw new SemanticError("variable member expression's caller type should not be array", node.pos);
            if (classType.isClass) {
                var member = (VarExprNode) node.member;
                var classDef = gScope.getClassDefNode(className, node.pos);
                Type type = classDef.getVarMemberType(member);
                if (type == null)
                    throw new SemanticError("call class member " + className + "." + member.identifier + " failed", node.pos);
                node.type = type;
                node.member.type = type;
                return;
            }
            throw new SemanticError("variable member expression's caller type should not be " + className, node.pos);
        }
        throw new SemanticError("unexpected member expression error", node.pos);
    }

    @Override
    public void visit(ArrayExprNode node) {
        node.array.accept(this);
        node.type = new Type(node.array.type); //deep copy
        for (var index : node.indexes) {
            index.accept(this);
            if (Type.notInt(index.type))
                throw new SemanticError("array index type not int", node.pos);
            node.type.dim--;
        }
        if (node.type.dim < 0)
            throw new SemanticError("array index number exceed [] number", node.pos);
    }

    @Override
    public void visit(NewExprNode node) {
        //node.type already set
        if (node.isNewClass()) {
            if (node.type.isBasic()) //it seems like no need
                throw new SemanticError("unexpected new basic type expression", node.pos);
            return;
        }
        boolean flag = false; //if null appeared
        for (var index : node.dimExpr) {
            if (index == null) {
                flag = true;
            } else if (flag) {
                throw new SemanticError("non-null index appeared after null in new array expression", node.pos);
            } else {
                index.accept(this);
                if (Type.notInt(index.type))
                    throw new SemanticError("new array index type not int", node.pos);
            }
        }
    }

    @Override
    public void visit(UnaryExprNode node) {
        node.expression.accept(this);
        if (node.isPrefixUpdate()) { //debug: check if update expression assignable
            if (!node.expression.isAssignable())
                throw new SemanticError("prefix update expression should be assignable", node.pos);
        }
        if (node.isInt()) {
            if (Type.notInt(node.expression.type))
                throw new SemanticError("unary expression type mismatch: should be int", node.pos);
            node.type = Type.Int();
        } else if (node.isBool()) {
            if (Type.notBool(node.expression.type))
                throw new SemanticError("unary expression type mismatch: should be bool", node.pos);
            node.type = Type.Bool();
        } else {
            throw new SemanticError("unexpected unary expression error", node.pos);
        }
    }

    @Override
    public void visit(BinaryExprNode node) {
        node.rhs.accept(this);
        node.lhs.accept(this);
        if (node.rhs.type.notEquals(node.lhs.type))
            throw new SemanticError("binary expression type mismatch", node.pos);
        Type type = node.rhs.type; //type of rhs and lhs already same
        if (node.isCmp()) {
            node.type = Type.Bool();
        } else if (node.isLogic()) { //bool operation
            if (Type.notBool(type))
                throw new SemanticError("logic binary expression type mismatch: should be bool", node.pos);
            node.type = Type.Bool();
        } else if (node.isAdd()) { //debug: string also support add operation
            if (Type.notInt(type) && Type.notString(type))
                throw new SemanticError("add binary expression type mismatch: should be int or string", node.pos);
            node.type = new Type(type);
        } else if (node.isArithmetic() || node.isBitOperation()) { //int operation
            if (Type.notInt(type))
                throw new SemanticError("binary expression type mismatch: should be int", node.pos);
            node.type = Type.Int();
        } else {
            throw new SemanticError("unexpected binary expression error", node.pos);
        }
    }

    @Override
    public void visit(PostfixUpdateExprNode node) {
        node.expression.accept(this);
        if (!node.expression.isAssignable()) //debug: check if update expression assignable
            throw new SemanticError("postfix update expression should be assignable", node.pos);
        if (Type.notInt(node.expression.type))
            throw new SemanticError("postfix update expression type should be int", node.pos);
        node.type = Type.Int();
    }

    @Override
    public void visit(AssignExprNode node) {
        node.rhs.accept(this);
        node.lhs.accept(this);
        if (node.lhs.type.notEquals(node.rhs.type))
            throw new SemanticError("assign expression type mismatch", node.pos);
        if (!node.lhs.isAssignable())
            throw new SemanticError("assign to an unAssignable expression", node.pos);
        node.type = new Type(node.lhs.type);
        //rhs may be null, but lhs shouldn't;
    }

    @Override
    public void visit(TernaryExprNode node) {
        node.condition.accept(this);
        if (Type.notBool(node.condition.type))
            throw new SemanticError("ternary expression condition type should be bool", node.pos);
        node.ifExpr.accept(this);
        node.elseExpr.accept(this);
        if (node.ifExpr.type.notEquals(node.elseExpr.type))
            throw new SemanticError("ternary expression type mismatch", node.pos);
        node.type = new Type(node.ifExpr.type);
    }

}
