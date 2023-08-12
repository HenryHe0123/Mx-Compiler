// Generated from E:/c++/ACM Class/Compiler/Mx-Compiler/src/Parser/ANTLR\Mx.g4 by ANTLR 4.12.0
package Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxParser}.
 */
public interface MxListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link MxParser#program}.
     *
     * @param ctx the parse tree
     */
    void enterProgram(MxParser.ProgramContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#program}.
     *
     * @param ctx the parse tree
     */
    void exitProgram(MxParser.ProgramContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#literal}.
     *
     * @param ctx the parse tree
     */
    void enterLiteral(MxParser.LiteralContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#literal}.
     *
     * @param ctx the parse tree
     */
    void exitLiteral(MxParser.LiteralContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#variable}.
     *
     * @param ctx the parse tree
     */
    void enterVariable(MxParser.VariableContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#variable}.
     *
     * @param ctx the parse tree
     */
    void exitVariable(MxParser.VariableContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#simpleType}.
     *
     * @param ctx the parse tree
     */
    void enterSimpleType(MxParser.SimpleTypeContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#simpleType}.
     *
     * @param ctx the parse tree
     */
    void exitSimpleType(MxParser.SimpleTypeContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#typename}.
     *
     * @param ctx the parse tree
     */
    void enterTypename(MxParser.TypenameContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#typename}.
     *
     * @param ctx the parse tree
     */
    void exitTypename(MxParser.TypenameContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#suite}.
     *
     * @param ctx the parse tree
     */
    void enterSuite(MxParser.SuiteContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#suite}.
     *
     * @param ctx the parse tree
     */
    void exitSuite(MxParser.SuiteContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#primary}.
     *
     * @param ctx the parse tree
     */
    void enterPrimary(MxParser.PrimaryContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#primary}.
     *
     * @param ctx the parse tree
     */
    void exitPrimary(MxParser.PrimaryContext ctx);

    /**
     * Enter a parse tree produced by the {@code newExpr}
     * labeled alternative in {@link MxParser#expression}.
     * @param ctx the parse tree
	 */
	void enterNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExpr}
     * labeled alternative in {@link MxParser#expression}.
     * @param ctx the parse tree
     */
    void enterUnaryExpr(MxParser.UnaryExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code unaryExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitUnaryExpr(MxParser.UnaryExprContext ctx);

    /**
     * Enter a parse tree produced by the {@code ternaryExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void enterTernaryExpr(MxParser.TernaryExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code ternaryExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitTernaryExpr(MxParser.TernaryExprContext ctx);

    /**
     * Enter a parse tree produced by the {@code arrayExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void enterArrayExpr(MxParser.ArrayExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code arrayExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitArrayExpr(MxParser.ArrayExprContext ctx);

    /**
     * Enter a parse tree produced by the {@code bracketExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void enterBracketExpr(MxParser.BracketExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code bracketExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitBracketExpr(MxParser.BracketExprContext ctx);

    /**
     * Enter a parse tree produced by the {@code memberExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void enterMemberExpr(MxParser.MemberExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code memberExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitMemberExpr(MxParser.MemberExprContext ctx);

    /**
     * Enter a parse tree produced by the {@code atomExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void enterAtomExpr(MxParser.AtomExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code atomExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitAtomExpr(MxParser.AtomExprContext ctx);

    /**
     * Enter a parse tree produced by the {@code binaryExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void enterBinaryExpr(MxParser.BinaryExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code binaryExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxParser#expression}.
     * @param ctx the parse tree
     */
    void enterAssignExpr(MxParser.AssignExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code assignExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitAssignExpr(MxParser.AssignExprContext ctx);

    /**
     * Enter a parse tree produced by the {@code postfixUpdateExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void enterPostfixUpdateExpr(MxParser.PostfixUpdateExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code postfixUpdateExpr}
     * labeled alternative in {@link MxParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitPostfixUpdateExpr(MxParser.PostfixUpdateExprContext ctx);

    /**
     * Enter a parse tree produced by the {@code blockStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void enterBlockStmt(MxParser.BlockStmtContext ctx);

    /**
     * Exit a parse tree produced by the {@code blockStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void exitBlockStmt(MxParser.BlockStmtContext ctx);

    /**
     * Enter a parse tree produced by the {@code varDefStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void enterVarDefStmt(MxParser.VarDefStmtContext ctx);

    /**
     * Exit a parse tree produced by the {@code varDefStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void exitVarDefStmt(MxParser.VarDefStmtContext ctx);

    /**
     * Enter a parse tree produced by the {@code exprStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void enterExprStmt(MxParser.ExprStmtContext ctx);

    /**
     * Exit a parse tree produced by the {@code exprStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void exitExprStmt(MxParser.ExprStmtContext ctx);

    /**
     * Enter a parse tree produced by the {@code branchStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void enterBranchStmt(MxParser.BranchStmtContext ctx);

    /**
     * Exit a parse tree produced by the {@code branchStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void exitBranchStmt(MxParser.BranchStmtContext ctx);

    /**
     * Enter a parse tree produced by the {@code forStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void enterForStmt(MxParser.ForStmtContext ctx);

    /**
     * Exit a parse tree produced by the {@code forStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void exitForStmt(MxParser.ForStmtContext ctx);

    /**
     * Enter a parse tree produced by the {@code whileStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void enterWhileStmt(MxParser.WhileStmtContext ctx);

    /**
     * Exit a parse tree produced by the {@code whileStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void exitWhileStmt(MxParser.WhileStmtContext ctx);

    /**
     * Enter a parse tree produced by the {@code ctrlStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void enterCtrlStmt(MxParser.CtrlStmtContext ctx);

    /**
     * Exit a parse tree produced by the {@code ctrlStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void exitCtrlStmt(MxParser.CtrlStmtContext ctx);

    /**
     * Enter a parse tree produced by the {@code returnStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void enterReturnStmt(MxParser.ReturnStmtContext ctx);

    /**
     * Exit a parse tree produced by the {@code returnStmt}
     * labeled alternative in {@link MxParser#statement}.
     *
     * @param ctx the parse tree
     */
    void exitReturnStmt(MxParser.ReturnStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#funcParameterList}.
     *
     * @param ctx the parse tree
     */
    void enterFuncParameterList(MxParser.FuncParameterListContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#funcParameterList}.
     *
     * @param ctx the parse tree
     */
    void exitFuncParameterList(MxParser.FuncParameterListContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#funcParameter}.
     *
     * @param ctx the parse tree
     */
    void enterFuncParameter(MxParser.FuncParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funcParameter}.
	 * @param ctx the parse tree
	 */
	void exitFuncParameter(MxParser.FuncParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(MxParser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(MxParser.ReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void enterFuncDef(MxParser.FuncDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void exitFuncDef(MxParser.FuncDefContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#funcParameterCall}.
     *
     * @param ctx the parse tree
     */
    void enterFuncParameterCall(MxParser.FuncParameterCallContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#funcParameterCall}.
     *
     * @param ctx the parse tree
     */
    void exitFuncParameterCall(MxParser.FuncParameterCallContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#callFunction}.
     *
     * @param ctx the parse tree
     */
    void enterCallFunction(MxParser.CallFunctionContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#callFunction}.
     *
     * @param ctx the parse tree
     */
    void exitCallFunction(MxParser.CallFunctionContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#classConstructor}.
     *
     * @param ctx the parse tree
     */
    void enterClassConstructor(MxParser.ClassConstructorContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#classConstructor}.
     *
     * @param ctx the parse tree
     */
    void exitClassConstructor(MxParser.ClassConstructorContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#classBody}.
     *
     * @param ctx the parse tree
     */
    void enterClassBody(MxParser.ClassBodyContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#classBody}.
     *
     * @param ctx the parse tree
     */
    void exitClassBody(MxParser.ClassBodyContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#classDef}.
     *
     * @param ctx the parse tree
     */
    void enterClassDef(MxParser.ClassDefContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#classDef}.
     *
     * @param ctx the parse tree
     */
    void exitClassDef(MxParser.ClassDefContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#varDeclareUnit}.
     *
     * @param ctx the parse tree
     */
    void enterVarDeclareUnit(MxParser.VarDeclareUnitContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#varDeclareUnit}.
     *
     * @param ctx the parse tree
     */
    void exitVarDeclareUnit(MxParser.VarDeclareUnitContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#varDef}.
     *
     * @param ctx the parse tree
     */
    void enterVarDef(MxParser.VarDefContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#varDef}.
     *
     * @param ctx the parse tree
     */
    void exitVarDef(MxParser.VarDefContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#newArrayIndex}.
     *
     * @param ctx the parse tree
     */
    void enterNewArrayIndex(MxParser.NewArrayIndexContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#newArrayIndex}.
     *
     * @param ctx the parse tree
     */
    void exitNewArrayIndex(MxParser.NewArrayIndexContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#newArrayExpr}.
     *
     * @param ctx the parse tree
     */
    void enterNewArrayExpr(MxParser.NewArrayExprContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#newArrayExpr}.
     *
     * @param ctx the parse tree
     */
    void exitNewArrayExpr(MxParser.NewArrayExprContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#newClassExpr}.
     *
     * @param ctx the parse tree
     */
    void enterNewClassExpr(MxParser.NewClassExprContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#newClassExpr}.
     *
     * @param ctx the parse tree
     */
    void exitNewClassExpr(MxParser.NewClassExprContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#newExpression}.
     *
     * @param ctx the parse tree
     */
    void enterNewExpression(MxParser.NewExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#newExpression}.
     *
     * @param ctx the parse tree
     */
    void exitNewExpression(MxParser.NewExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#exprStatement}.
     *
     * @param ctx the parse tree
     */
    void enterExprStatement(MxParser.ExprStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#exprStatement}.
     *
     * @param ctx the parse tree
     */
    void exitExprStatement(MxParser.ExprStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#ctrlStatement}.
     *
     * @param ctx the parse tree
     */
    void enterCtrlStatement(MxParser.CtrlStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#ctrlStatement}.
     *
     * @param ctx the parse tree
     */
    void exitCtrlStatement(MxParser.CtrlStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#condition}.
     *
     * @param ctx the parse tree
     */
    void enterCondition(MxParser.ConditionContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#condition}.
     *
     * @param ctx the parse tree
     */
    void exitCondition(MxParser.ConditionContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#branchStatement}.
     *
     * @param ctx the parse tree
     */
    void enterBranchStatement(MxParser.BranchStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#branchStatement}.
     *
     * @param ctx the parse tree
     */
    void exitBranchStatement(MxParser.BranchStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#whileStatement}.
     *
     * @param ctx the parse tree
     */
    void enterWhileStatement(MxParser.WhileStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#whileStatement}.
     *
     * @param ctx the parse tree
     */
    void exitWhileStatement(MxParser.WhileStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#forInit}.
     *
     * @param ctx the parse tree
     */
    void enterForInit(MxParser.ForInitContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#forInit}.
     *
     * @param ctx the parse tree
     */
    void exitForInit(MxParser.ForInitContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#forInitCondition}.
     *
     * @param ctx the parse tree
     */
    void enterForInitCondition(MxParser.ForInitConditionContext ctx);

    /**
     * Exit a parse tree produced by {@link MxParser#forInitCondition}.
     *
     * @param ctx the parse tree
     */
    void exitForInitCondition(MxParser.ForInitConditionContext ctx);

    /**
     * Enter a parse tree produced by {@link MxParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(MxParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(MxParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(MxParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(MxParser.ReturnStatementContext ctx);
}