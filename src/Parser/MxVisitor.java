// Generated from E:/c++/ACM Class/Compiler/Mx-Compiler/src/Parser\Mx.g4 by ANTLR 4.12.0
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MxParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MxParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(MxParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#basicType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicType(MxParser.BasicTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#typename}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypename(MxParser.TypenameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(MxParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(MxParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(MxParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ternaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryExpr(MxParser.TernaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(MxParser.ArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bracketExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketExpr(MxParser.BracketExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dotFuncExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotFuncExpr(MxParser.DotFuncExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpr(MxParser.AtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dotVarExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotVarExpr(MxParser.DotVarExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code postfixUpdateExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixUpdateExpr(MxParser.PostfixUpdateExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStmt(MxParser.BlockStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varDefStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefStmt(MxParser.VarDefStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(MxParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code branchStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranchStmt(MxParser.BranchStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code loopStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStmt(MxParser.LoopStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jumpStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpStmt(MxParser.JumpStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#funcParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncParameterList(MxParser.FuncParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#funcParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncParameter(MxParser.FuncParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnType(MxParser.ReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDef(MxParser.FuncDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#funcParameterCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncParameterCall(MxParser.FuncParameterCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#callFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallFunction(MxParser.CallFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#classConstructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassConstructor(MxParser.ClassConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(MxParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#varDeclareUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclareUnit(MxParser.VarDeclareUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDef(MxParser.VarDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#newArrayExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayExpr(MxParser.NewArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#newClassExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewClassExpr(MxParser.NewClassExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#newExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpression(MxParser.NewExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#exprStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStatement(MxParser.ExprStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpStatement(MxParser.JumpStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(MxParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#branchStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranchStatement(MxParser.BranchStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(MxParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(MxParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#forInitCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInitCondition(MxParser.ForInitConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(MxParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(MxParser.LoopStatementContext ctx);
}