// Generated from E:/c++/ACM Class/Compiler/Mx-Compiler/src/Parser\Mx.g4 by ANTLR 4.12.0
package Parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class MxParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Add=1, Sub=2, Mul=3, Div=4, Mod=5, Assign=6, Less=7, LessEqual=8, Greater=9, 
		GreaterEqual=10, Equal=11, NotEqual=12, AndAnd=13, OrOr=14, Not=15, RShift=16, 
		LShift=17, And=18, Or=19, Xor=20, Invert=21, AddAdd=22, SubSub=23, Dot=24, 
		LParen=25, RParen=26, LBrack=27, RBrack=28, LBrace=29, RBrace=30, Question=31, 
		Colon=32, Semi=33, Comma=34, Void=35, Bool=36, Int=37, String=38, New=39, 
		Class=40, Null=41, True=42, False=43, This=44, If=45, Else=46, For=47, 
		While=48, Break=49, Continue=50, Return=51, Identifier=52, WhiteSpace=53, 
		LineComment=54, BlockComment=55, IntLiteral=56, StringLiteral=57;
	public static final int
		RULE_program = 0, RULE_literal = 1, RULE_variable = 2, RULE_basicType = 3, 
		RULE_typename = 4, RULE_suite = 5, RULE_primary = 6, RULE_expression = 7, 
		RULE_statement = 8, RULE_funcParameterList = 9, RULE_funcParameter = 10, 
		RULE_returnType = 11, RULE_funcDef = 12, RULE_funcParameterCall = 13, 
		RULE_callFunction = 14, RULE_classConstructor = 15, RULE_classBody = 16, 
		RULE_classDef = 17, RULE_varDeclareUnit = 18, RULE_varDef = 19, RULE_newArrayExpr = 20, 
		RULE_newClassExpr = 21, RULE_newExpression = 22, RULE_exprStatement = 23, 
		RULE_jumpStatement = 24, RULE_condition = 25, RULE_branchStatement = 26, 
		RULE_whileStatement = 27, RULE_forInit = 28, RULE_forInitCondition = 29, 
		RULE_forStatement = 30, RULE_loopStatement = 31;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "literal", "variable", "basicType", "typename", "suite", "primary", 
			"expression", "statement", "funcParameterList", "funcParameter", "returnType", 
			"funcDef", "funcParameterCall", "callFunction", "classConstructor", "classBody", 
			"classDef", "varDeclareUnit", "varDef", "newArrayExpr", "newClassExpr", 
			"newExpression", "exprStatement", "jumpStatement", "condition", "branchStatement", 
			"whileStatement", "forInit", "forInitCondition", "forStatement", "loopStatement"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'='", "'<'", "'<='", "'>'", 
			"'>='", "'=='", "'!='", "'&&'", "'||'", "'!'", "'>>'", "'<<'", "'&'", 
			"'|'", "'^'", "'~'", "'++'", "'--'", "'.'", "'('", "')'", "'['", "']'", 
			"'{'", "'}'", "'?'", "':'", "';'", "','", "'void'", "'bool'", "'int'", 
			"'string'", "'new'", "'class'", "'null'", "'true'", "'false'", "'this'", 
			"'if'", "'else'", "'for'", "'while'", "'break'", "'continue'", "'return'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Add", "Sub", "Mul", "Div", "Mod", "Assign", "Less", "LessEqual", 
			"Greater", "GreaterEqual", "Equal", "NotEqual", "AndAnd", "OrOr", "Not", 
			"RShift", "LShift", "And", "Or", "Xor", "Invert", "AddAdd", "SubSub", 
			"Dot", "LParen", "RParen", "LBrack", "RBrack", "LBrace", "RBrace", "Question", 
			"Colon", "Semi", "Comma", "Void", "Bool", "Int", "String", "New", "Class", 
			"Null", "True", "False", "This", "If", "Else", "For", "While", "Break", 
			"Continue", "Return", "Identifier", "WhiteSpace", "LineComment", "BlockComment", 
			"IntLiteral", "StringLiteral"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Mx.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MxParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MxParser.EOF, 0); }
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public List<ClassDefContext> classDef() {
			return getRuleContexts(ClassDefContext.class);
		}
		public ClassDefContext classDef(int i) {
			return getRuleContext(ClassDefContext.class,i);
		}
		public List<FuncDefContext> funcDef() {
			return getRuleContexts(FuncDefContext.class);
		}
		public FuncDefContext funcDef(int i) {
			return getRuleContext(FuncDefContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4505214535073792L) != 0)) {
				{
				setState(67);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(64);
					varDef();
					}
					break;
				case 2:
					{
					setState(65);
					classDef();
					}
					break;
				case 3:
					{
					setState(66);
					funcDef();
					}
					break;
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(72);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode IntLiteral() { return getToken(MxParser.IntLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(MxParser.StringLiteral, 0); }
		public TerminalNode True() { return getToken(MxParser.True, 0); }
		public TerminalNode False() { return getToken(MxParser.False, 0); }
		public TerminalNode Null() { return getToken(MxParser.Null, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 216188175276572672L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode This() { return getToken(MxParser.This, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_variable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			_la = _input.LA(1);
			if ( !(_la==This || _la==Identifier) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BasicTypeContext extends ParserRuleContext {
		public TerminalNode Int() { return getToken(MxParser.Int, 0); }
		public TerminalNode Bool() { return getToken(MxParser.Bool, 0); }
		public TerminalNode String() { return getToken(MxParser.String, 0); }
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public BasicTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basicType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterBasicType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitBasicType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitBasicType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BasicTypeContext basicType() throws RecognitionException {
		BasicTypeContext _localctx = new BasicTypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_basicType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4504080663707648L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypenameContext extends ParserRuleContext {
		public BasicTypeContext basicType() {
			return getRuleContext(BasicTypeContext.class,0);
		}
		public List<TerminalNode> LBrack() { return getTokens(MxParser.LBrack); }
		public TerminalNode LBrack(int i) {
			return getToken(MxParser.LBrack, i);
		}
		public List<TerminalNode> RBrack() { return getTokens(MxParser.RBrack); }
		public TerminalNode RBrack(int i) {
			return getToken(MxParser.RBrack, i);
		}
		public TypenameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typename; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterTypename(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitTypename(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitTypename(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypenameContext typename() throws RecognitionException {
		TypenameContext _localctx = new TypenameContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_typename);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			basicType();
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBrack) {
				{
				{
				setState(81);
				match(LBrack);
				setState(82);
				match(RBrack);
				}
				}
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SuiteContext extends ParserRuleContext {
		public TerminalNode LBrace() { return getToken(MxParser.LBrace, 0); }
		public TerminalNode RBrace() { return getToken(MxParser.RBrace, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SuiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_suite; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterSuite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitSuite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitSuite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuiteContext suite() throws RecognitionException {
		SuiteContext _localctx = new SuiteContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_suite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(LBrace);
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 225108453568315398L) != 0)) {
				{
				{
				setState(89);
				statement();
				}
				}
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(95);
			match(RBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public CallFunctionContext callFunction() {
			return getRuleContext(CallFunctionContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_primary);
		try {
			setState(100);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(97);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(98);
				callFunction();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(99);
				variable();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewExprContext extends ExpressionContext {
		public NewExpressionContext newExpression() {
			return getRuleContext(NewExpressionContext.class,0);
		}
		public NewExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterNewExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitNewExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitNewExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode AddAdd() { return getToken(MxParser.AddAdd, 0); }
		public TerminalNode SubSub() { return getToken(MxParser.SubSub, 0); }
		public TerminalNode Not() { return getToken(MxParser.Not, 0); }
		public TerminalNode Invert() { return getToken(MxParser.Invert, 0); }
		public TerminalNode Sub() { return getToken(MxParser.Sub, 0); }
		public TerminalNode Add() { return getToken(MxParser.Add, 0); }
		public UnaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterUnaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitUnaryExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitUnaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TernaryExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode Question() { return getToken(MxParser.Question, 0); }
		public TerminalNode Colon() { return getToken(MxParser.Colon, 0); }
		public TernaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterTernaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitTernaryExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitTernaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> LBrack() { return getTokens(MxParser.LBrack); }
		public TerminalNode LBrack(int i) {
			return getToken(MxParser.LBrack, i);
		}
		public List<TerminalNode> RBrack() { return getTokens(MxParser.RBrack); }
		public TerminalNode RBrack(int i) {
			return getToken(MxParser.RBrack, i);
		}
		public ArrayExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterArrayExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitArrayExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitArrayExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BracketExprContext extends ExpressionContext {
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public BracketExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterBracketExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitBracketExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitBracketExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DotFuncExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Dot() { return getToken(MxParser.Dot, 0); }
		public CallFunctionContext callFunction() {
			return getRuleContext(CallFunctionContext.class,0);
		}
		public DotFuncExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterDotFuncExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitDotFuncExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitDotFuncExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtomExprContext extends ExpressionContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public AtomExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterAtomExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitAtomExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitAtomExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BinaryExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode Mul() { return getToken(MxParser.Mul, 0); }
		public TerminalNode Div() { return getToken(MxParser.Div, 0); }
		public TerminalNode Mod() { return getToken(MxParser.Mod, 0); }
		public TerminalNode Add() { return getToken(MxParser.Add, 0); }
		public TerminalNode Sub() { return getToken(MxParser.Sub, 0); }
		public TerminalNode LShift() { return getToken(MxParser.LShift, 0); }
		public TerminalNode RShift() { return getToken(MxParser.RShift, 0); }
		public TerminalNode Less() { return getToken(MxParser.Less, 0); }
		public TerminalNode Greater() { return getToken(MxParser.Greater, 0); }
		public TerminalNode LessEqual() { return getToken(MxParser.LessEqual, 0); }
		public TerminalNode GreaterEqual() { return getToken(MxParser.GreaterEqual, 0); }
		public TerminalNode Equal() { return getToken(MxParser.Equal, 0); }
		public TerminalNode NotEqual() { return getToken(MxParser.NotEqual, 0); }
		public TerminalNode And() { return getToken(MxParser.And, 0); }
		public TerminalNode Xor() { return getToken(MxParser.Xor, 0); }
		public TerminalNode Or() { return getToken(MxParser.Or, 0); }
		public TerminalNode AndAnd() { return getToken(MxParser.AndAnd, 0); }
		public TerminalNode OrOr() { return getToken(MxParser.OrOr, 0); }
		public BinaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterBinaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitBinaryExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitBinaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DotVarExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Dot() { return getToken(MxParser.Dot, 0); }
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public DotVarExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterDotVarExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitDotVarExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitDotVarExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode Assign() { return getToken(MxParser.Assign, 0); }
		public AssignExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterAssignExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitAssignExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitAssignExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PostfixUpdateExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode AddAdd() { return getToken(MxParser.AddAdd, 0); }
		public TerminalNode SubSub() { return getToken(MxParser.SubSub, 0); }
		public PostfixUpdateExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterPostfixUpdateExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitPostfixUpdateExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitPostfixUpdateExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Null:
			case True:
			case False:
			case This:
			case Identifier:
			case IntLiteral:
			case StringLiteral:
				{
				_localctx = new AtomExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(103);
				primary();
				}
				break;
			case LParen:
				{
				_localctx = new BracketExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(104);
				match(LParen);
				setState(105);
				expression(0);
				setState(106);
				match(RParen);
				}
				break;
			case Add:
			case Sub:
			case Not:
			case Invert:
			case AddAdd:
			case SubSub:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(108);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 14712838L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(109);
				expression(14);
				}
				break;
			case New:
				{
				_localctx = new NewExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(110);
				newExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(171);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(169);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(113);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(114);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 56L) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(115);
						expression(13);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(116);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(117);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Add || _la==Sub) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(118);
						expression(12);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(119);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(120);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==RShift || _la==LShift) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(121);
						expression(11);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(122);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(123);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1920L) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(124);
						expression(10);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(125);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(126);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Equal || _la==NotEqual) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(127);
						expression(9);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(128);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(129);
						((BinaryExprContext)_localctx).op = match(And);
						setState(130);
						expression(8);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(131);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(132);
						((BinaryExprContext)_localctx).op = match(Xor);
						setState(133);
						expression(7);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(134);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(135);
						((BinaryExprContext)_localctx).op = match(Or);
						setState(136);
						expression(6);
						}
						break;
					case 9:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(137);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(138);
						((BinaryExprContext)_localctx).op = match(AndAnd);
						setState(139);
						expression(5);
						}
						break;
					case 10:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(140);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(141);
						((BinaryExprContext)_localctx).op = match(OrOr);
						setState(142);
						expression(4);
						}
						break;
					case 11:
						{
						_localctx = new AssignExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(143);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(144);
						match(Assign);
						setState(145);
						expression(2);
						}
						break;
					case 12:
						{
						_localctx = new TernaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(146);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(147);
						match(Question);
						setState(148);
						expression(0);
						setState(149);
						match(Colon);
						setState(150);
						expression(1);
						}
						break;
					case 13:
						{
						_localctx = new DotFuncExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(152);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(153);
						match(Dot);
						setState(154);
						callFunction();
						}
						break;
					case 14:
						{
						_localctx = new DotVarExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(155);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(156);
						match(Dot);
						setState(157);
						variable();
						}
						break;
					case 15:
						{
						_localctx = new ArrayExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(158);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(163); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(159);
								match(LBrack);
								setState(160);
								expression(0);
								setState(161);
								match(RBrack);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(165); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					case 16:
						{
						_localctx = new PostfixUpdateExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(167);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(168);
						_la = _input.LA(1);
						if ( !(_la==AddAdd || _la==SubSub) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(173);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JumpStmtContext extends StatementContext {
		public JumpStatementContext jumpStatement() {
			return getRuleContext(JumpStatementContext.class,0);
		}
		public JumpStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterJumpStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitJumpStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitJumpStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VarDefStmtContext extends StatementContext {
		public VarDefContext varDef() {
			return getRuleContext(VarDefContext.class,0);
		}
		public VarDefStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterVarDefStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitVarDefStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitVarDefStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprStmtContext extends StatementContext {
		public ExprStatementContext exprStatement() {
			return getRuleContext(ExprStatementContext.class,0);
		}
		public ExprStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterExprStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitExprStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitExprStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LoopStmtContext extends StatementContext {
		public LoopStatementContext loopStatement() {
			return getRuleContext(LoopStatementContext.class,0);
		}
		public LoopStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterLoopStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitLoopStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitLoopStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BlockStmtContext extends StatementContext {
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public BlockStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterBlockStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitBlockStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitBlockStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BranchStmtContext extends StatementContext {
		public BranchStatementContext branchStatement() {
			return getRuleContext(BranchStatementContext.class,0);
		}
		public BranchStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterBranchStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitBranchStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitBranchStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_statement);
		try {
			setState(180);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				_localctx = new BlockStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				suite();
				}
				break;
			case 2:
				_localctx = new VarDefStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(175);
				varDef();
				}
				break;
			case 3:
				_localctx = new ExprStmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(176);
				exprStatement();
				}
				break;
			case 4:
				_localctx = new BranchStmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(177);
				branchStatement();
				}
				break;
			case 5:
				_localctx = new LoopStmtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(178);
				loopStatement();
				}
				break;
			case 6:
				_localctx = new JumpStmtContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(179);
				jumpStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FuncParameterListContext extends ParserRuleContext {
		public List<TypenameContext> typename() {
			return getRuleContexts(TypenameContext.class);
		}
		public TypenameContext typename(int i) {
			return getRuleContext(TypenameContext.class,i);
		}
		public List<TerminalNode> Identifier() { return getTokens(MxParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(MxParser.Identifier, i);
		}
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public FuncParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcParameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterFuncParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitFuncParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitFuncParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncParameterListContext funcParameterList() throws RecognitionException {
		FuncParameterListContext _localctx = new FuncParameterListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_funcParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			typename();
			setState(183);
			match(Identifier);
			setState(190);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(184);
				match(Comma);
				setState(185);
				typename();
				setState(186);
				match(Identifier);
				}
				}
				setState(192);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FuncParameterContext extends ParserRuleContext {
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public FuncParameterListContext funcParameterList() {
			return getRuleContext(FuncParameterListContext.class,0);
		}
		public FuncParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterFuncParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitFuncParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitFuncParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncParameterContext funcParameter() throws RecognitionException {
		FuncParameterContext _localctx = new FuncParameterContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_funcParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(LParen);
			setState(195);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4504080663707648L) != 0)) {
				{
				setState(194);
				funcParameterList();
				}
			}

			setState(197);
			match(RParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnTypeContext extends ParserRuleContext {
		public TypenameContext typename() {
			return getRuleContext(TypenameContext.class,0);
		}
		public TerminalNode Void() { return getToken(MxParser.Void, 0); }
		public ReturnTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterReturnType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitReturnType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitReturnType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnTypeContext returnType() throws RecognitionException {
		ReturnTypeContext _localctx = new ReturnTypeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_returnType);
		try {
			setState(201);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Bool:
			case Int:
			case String:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(199);
				typename();
				}
				break;
			case Void:
				enterOuterAlt(_localctx, 2);
				{
				setState(200);
				match(Void);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FuncDefContext extends ParserRuleContext {
		public SuiteContext body;
		public ReturnTypeContext returnType() {
			return getRuleContext(ReturnTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public FuncParameterContext funcParameter() {
			return getRuleContext(FuncParameterContext.class,0);
		}
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public FuncDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterFuncDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitFuncDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitFuncDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncDefContext funcDef() throws RecognitionException {
		FuncDefContext _localctx = new FuncDefContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_funcDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			returnType();
			setState(204);
			match(Identifier);
			setState(205);
			funcParameter();
			setState(206);
			((FuncDefContext)_localctx).body = suite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FuncParameterCallContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public FuncParameterCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcParameterCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterFuncParameterCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitFuncParameterCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitFuncParameterCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncParameterCallContext funcParameterCall() throws RecognitionException {
		FuncParameterCallContext _localctx = new FuncParameterCallContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_funcParameterCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			expression(0);
			setState(213);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(209);
				match(Comma);
				setState(210);
				expression(0);
				}
				}
				setState(215);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CallFunctionContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public FuncParameterCallContext funcParameterCall() {
			return getRuleContext(FuncParameterCallContext.class,0);
		}
		public CallFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterCallFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitCallFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitCallFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallFunctionContext callFunction() throws RecognitionException {
		CallFunctionContext _localctx = new CallFunctionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_callFunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			match(Identifier);
			setState(217);
			match(LParen);
			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 220709916894068742L) != 0)) {
				{
				setState(218);
				funcParameterCall();
				}
			}

			setState(221);
			match(RParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassConstructorContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public ClassConstructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classConstructor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterClassConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitClassConstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitClassConstructor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassConstructorContext classConstructor() throws RecognitionException {
		ClassConstructorContext _localctx = new ClassConstructorContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_classConstructor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			match(Identifier);
			setState(224);
			match(LParen);
			setState(225);
			match(RParen);
			setState(226);
			suite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassBodyContext extends ParserRuleContext {
		public TerminalNode LBrace() { return getToken(MxParser.LBrace, 0); }
		public TerminalNode RBrace() { return getToken(MxParser.RBrace, 0); }
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public List<FuncDefContext> funcDef() {
			return getRuleContexts(FuncDefContext.class);
		}
		public FuncDefContext funcDef(int i) {
			return getRuleContext(FuncDefContext.class,i);
		}
		public List<ClassConstructorContext> classConstructor() {
			return getRuleContexts(ClassConstructorContext.class);
		}
		public ClassConstructorContext classConstructor(int i) {
			return getRuleContext(ClassConstructorContext.class,i);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterClassBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitClassBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			match(LBrace);
			setState(234);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4504115023446016L) != 0)) {
				{
				setState(232);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
				case 1:
					{
					setState(229);
					varDef();
					}
					break;
				case 2:
					{
					setState(230);
					funcDef();
					}
					break;
				case 3:
					{
					setState(231);
					classConstructor();
					}
					break;
				}
				}
				setState(236);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(237);
			match(RBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassDefContext extends ParserRuleContext {
		public TerminalNode Class() { return getToken(MxParser.Class, 0); }
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public ClassDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterClassDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitClassDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitClassDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDefContext classDef() throws RecognitionException {
		ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_classDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(Class);
			setState(240);
			match(Identifier);
			setState(241);
			classBody();
			setState(242);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarDeclareUnitContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode Assign() { return getToken(MxParser.Assign, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VarDeclareUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclareUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterVarDeclareUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitVarDeclareUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitVarDeclareUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclareUnitContext varDeclareUnit() throws RecognitionException {
		VarDeclareUnitContext _localctx = new VarDeclareUnitContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_varDeclareUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(Identifier);
			setState(247);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(245);
				match(Assign);
				setState(246);
				expression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarDefContext extends ParserRuleContext {
		public TypenameContext typename() {
			return getRuleContext(TypenameContext.class,0);
		}
		public List<VarDeclareUnitContext> varDeclareUnit() {
			return getRuleContexts(VarDeclareUnitContext.class);
		}
		public VarDeclareUnitContext varDeclareUnit(int i) {
			return getRuleContext(VarDeclareUnitContext.class,i);
		}
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public VarDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterVarDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitVarDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitVarDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDefContext varDef() throws RecognitionException {
		VarDefContext _localctx = new VarDefContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_varDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			typename();
			setState(250);
			varDeclareUnit();
			setState(255);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(251);
				match(Comma);
				setState(252);
				varDeclareUnit();
				}
				}
				setState(257);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(258);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NewArrayExprContext extends ParserRuleContext {
		public TerminalNode New() { return getToken(MxParser.New, 0); }
		public BasicTypeContext basicType() {
			return getRuleContext(BasicTypeContext.class,0);
		}
		public List<TerminalNode> LBrack() { return getTokens(MxParser.LBrack); }
		public TerminalNode LBrack(int i) {
			return getToken(MxParser.LBrack, i);
		}
		public List<TerminalNode> RBrack() { return getTokens(MxParser.RBrack); }
		public TerminalNode RBrack(int i) {
			return getToken(MxParser.RBrack, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public NewArrayExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newArrayExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterNewArrayExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitNewArrayExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitNewArrayExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewArrayExprContext newArrayExpr() throws RecognitionException {
		NewArrayExprContext _localctx = new NewArrayExprContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_newArrayExpr);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			match(New);
			setState(261);
			basicType();
			setState(267); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(262);
					match(LBrack);
					setState(264);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 220709916894068742L) != 0)) {
						{
						setState(263);
						expression(0);
						}
					}

					setState(266);
					match(RBrack);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(269); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NewClassExprContext extends ParserRuleContext {
		public TerminalNode New() { return getToken(MxParser.New, 0); }
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public NewClassExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newClassExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterNewClassExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitNewClassExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitNewClassExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewClassExprContext newClassExpr() throws RecognitionException {
		NewClassExprContext _localctx = new NewClassExprContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_newClassExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			match(New);
			setState(272);
			match(Identifier);
			setState(275);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(273);
				match(LParen);
				setState(274);
				match(RParen);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NewExpressionContext extends ParserRuleContext {
		public NewArrayExprContext newArrayExpr() {
			return getRuleContext(NewArrayExprContext.class,0);
		}
		public NewClassExprContext newClassExpr() {
			return getRuleContext(NewClassExprContext.class,0);
		}
		public NewExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterNewExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitNewExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitNewExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewExpressionContext newExpression() throws RecognitionException {
		NewExpressionContext _localctx = new NewExpressionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_newExpression);
		try {
			setState(279);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(277);
				newArrayExpr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(278);
				newClassExpr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprStatementContext extends ParserRuleContext {
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExprStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterExprStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitExprStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitExprStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprStatementContext exprStatement() throws RecognitionException {
		ExprStatementContext _localctx = new ExprStatementContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_exprStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 220709916894068742L) != 0)) {
				{
				setState(281);
				expression(0);
				}
			}

			setState(284);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JumpStatementContext extends ParserRuleContext {
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public TerminalNode Break() { return getToken(MxParser.Break, 0); }
		public TerminalNode Continue() { return getToken(MxParser.Continue, 0); }
		public TerminalNode Return() { return getToken(MxParser.Return, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public JumpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterJumpStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitJumpStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitJumpStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpStatementContext jumpStatement() throws RecognitionException {
		JumpStatementContext _localctx = new JumpStatementContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_jumpStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Break:
				{
				setState(286);
				match(Break);
				}
				break;
			case Continue:
				{
				setState(287);
				match(Continue);
				}
				break;
			case Return:
				{
				setState(288);
				match(Return);
				setState(290);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 220709916894068742L) != 0)) {
					{
					setState(289);
					expression(0);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(294);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConditionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BranchStatementContext extends ParserRuleContext {
		public StatementContext ifStatement;
		public StatementContext elseStatement;
		public TerminalNode If() { return getToken(MxParser.If, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode Else() { return getToken(MxParser.Else, 0); }
		public BranchStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_branchStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterBranchStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitBranchStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitBranchStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BranchStatementContext branchStatement() throws RecognitionException {
		BranchStatementContext _localctx = new BranchStatementContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_branchStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
			match(If);
			setState(299);
			match(LParen);
			setState(300);
			condition();
			setState(301);
			match(RParen);
			setState(302);
			((BranchStatementContext)_localctx).ifStatement = statement();
			setState(305);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(303);
				match(Else);
				setState(304);
				((BranchStatementContext)_localctx).elseStatement = statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStatementContext extends ParserRuleContext {
		public StatementContext body;
		public TerminalNode While() { return getToken(MxParser.While, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			match(While);
			setState(308);
			match(LParen);
			setState(309);
			condition();
			setState(310);
			match(RParen);
			setState(311);
			((WhileStatementContext)_localctx).body = statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForInitContext extends ParserRuleContext {
		public ExprStatementContext exprStatement() {
			return getRuleContext(ExprStatementContext.class,0);
		}
		public VarDefContext varDef() {
			return getRuleContext(VarDefContext.class,0);
		}
		public ForInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterForInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitForInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitForInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitContext forInit() throws RecognitionException {
		ForInitContext _localctx = new ForInitContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_forInit);
		try {
			setState(315);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(313);
				exprStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(314);
				varDef();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForInitConditionContext extends ParserRuleContext {
		public ExpressionContext step;
		public ForInitContext forInit() {
			return getRuleContext(ForInitContext.class,0);
		}
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForInitConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInitCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterForInitCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitForInitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitForInitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitConditionContext forInitCondition() throws RecognitionException {
		ForInitConditionContext _localctx = new ForInitConditionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_forInitCondition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			forInit();
			setState(319);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 220709916894068742L) != 0)) {
				{
				setState(318);
				condition();
				}
			}

			setState(321);
			match(Semi);
			setState(323);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 220709916894068742L) != 0)) {
				{
				setState(322);
				((ForInitConditionContext)_localctx).step = expression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForStatementContext extends ParserRuleContext {
		public StatementContext body;
		public TerminalNode For() { return getToken(MxParser.For, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public ForInitConditionContext forInitCondition() {
			return getRuleContext(ForInitConditionContext.class,0);
		}
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(325);
			match(For);
			setState(326);
			match(LParen);
			setState(327);
			forInitCondition();
			setState(328);
			match(RParen);
			setState(329);
			((ForStatementContext)_localctx).body = statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LoopStatementContext extends ParserRuleContext {
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public LoopStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterLoopStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitLoopStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitLoopStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopStatementContext loopStatement() throws RecognitionException {
		LoopStatementContext _localctx = new LoopStatementContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_loopStatement);
		try {
			setState(333);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case While:
				enterOuterAlt(_localctx, 1);
				{
				setState(331);
				whileStatement();
				}
				break;
			case For:
				enterOuterAlt(_localctx, 2);
				{
				setState(332);
				forStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 12);
		case 1:
			return precpred(_ctx, 11);
		case 2:
			return precpred(_ctx, 10);
		case 3:
			return precpred(_ctx, 9);
		case 4:
			return precpred(_ctx, 8);
		case 5:
			return precpred(_ctx, 7);
		case 6:
			return precpred(_ctx, 6);
		case 7:
			return precpred(_ctx, 5);
		case 8:
			return precpred(_ctx, 4);
		case 9:
			return precpred(_ctx, 3);
		case 10:
			return precpred(_ctx, 2);
		case 11:
			return precpred(_ctx, 1);
		case 12:
			return precpred(_ctx, 18);
		case 13:
			return precpred(_ctx, 17);
		case 14:
			return precpred(_ctx, 16);
		case 15:
			return precpred(_ctx, 15);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u00019\u0150\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000"+
		"D\b\u0000\n\u0000\f\u0000G\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0005\u0004T\b\u0004\n\u0004\f\u0004W\t\u0004"+
		"\u0001\u0005\u0001\u0005\u0005\u0005[\b\u0005\n\u0005\f\u0005^\t\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006"+
		"e\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007p\b\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0004\u0007\u00a4\b\u0007\u000b\u0007\f\u0007"+
		"\u00a5\u0001\u0007\u0001\u0007\u0005\u0007\u00aa\b\u0007\n\u0007\f\u0007"+
		"\u00ad\t\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b"+
		"\u00b5\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0005\t\u00bd"+
		"\b\t\n\t\f\t\u00c0\t\t\u0001\n\u0001\n\u0003\n\u00c4\b\n\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0003\u000b\u00ca\b\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0005\r\u00d4\b\r\n\r\f\r\u00d7"+
		"\t\r\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u00dc\b\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u00e9"+
		"\b\u0010\n\u0010\f\u0010\u00ec\t\u0010\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0003\u0012\u00f8\b\u0012\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0005\u0013\u00fe\b\u0013\n\u0013\f\u0013\u0101\t\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003"+
		"\u0014\u0109\b\u0014\u0001\u0014\u0004\u0014\u010c\b\u0014\u000b\u0014"+
		"\f\u0014\u010d\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015"+
		"\u0114\b\u0015\u0001\u0016\u0001\u0016\u0003\u0016\u0118\b\u0016\u0001"+
		"\u0017\u0003\u0017\u011b\b\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0003\u0018\u0123\b\u0018\u0003\u0018\u0125"+
		"\b\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0003"+
		"\u001a\u0132\b\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0003\u001c\u013c\b\u001c\u0001"+
		"\u001d\u0001\u001d\u0003\u001d\u0140\b\u001d\u0001\u001d\u0001\u001d\u0003"+
		"\u001d\u0144\b\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0003\u001f\u014e\b\u001f\u0001"+
		"\u001f\u0000\u0001\u000e \u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>\u0000\n\u0002\u0000"+
		")+89\u0002\u0000,,44\u0002\u0000$&44\u0003\u0000\u0001\u0002\u000f\u000f"+
		"\u0015\u0017\u0001\u0000\u0003\u0005\u0001\u0000\u0001\u0002\u0001\u0000"+
		"\u0010\u0011\u0001\u0000\u0007\n\u0001\u0000\u000b\f\u0001\u0000\u0016"+
		"\u0017\u0166\u0000E\u0001\u0000\u0000\u0000\u0002J\u0001\u0000\u0000\u0000"+
		"\u0004L\u0001\u0000\u0000\u0000\u0006N\u0001\u0000\u0000\u0000\bP\u0001"+
		"\u0000\u0000\u0000\nX\u0001\u0000\u0000\u0000\fd\u0001\u0000\u0000\u0000"+
		"\u000eo\u0001\u0000\u0000\u0000\u0010\u00b4\u0001\u0000\u0000\u0000\u0012"+
		"\u00b6\u0001\u0000\u0000\u0000\u0014\u00c1\u0001\u0000\u0000\u0000\u0016"+
		"\u00c9\u0001\u0000\u0000\u0000\u0018\u00cb\u0001\u0000\u0000\u0000\u001a"+
		"\u00d0\u0001\u0000\u0000\u0000\u001c\u00d8\u0001\u0000\u0000\u0000\u001e"+
		"\u00df\u0001\u0000\u0000\u0000 \u00e4\u0001\u0000\u0000\u0000\"\u00ef"+
		"\u0001\u0000\u0000\u0000$\u00f4\u0001\u0000\u0000\u0000&\u00f9\u0001\u0000"+
		"\u0000\u0000(\u0104\u0001\u0000\u0000\u0000*\u010f\u0001\u0000\u0000\u0000"+
		",\u0117\u0001\u0000\u0000\u0000.\u011a\u0001\u0000\u0000\u00000\u0124"+
		"\u0001\u0000\u0000\u00002\u0128\u0001\u0000\u0000\u00004\u012a\u0001\u0000"+
		"\u0000\u00006\u0133\u0001\u0000\u0000\u00008\u013b\u0001\u0000\u0000\u0000"+
		":\u013d\u0001\u0000\u0000\u0000<\u0145\u0001\u0000\u0000\u0000>\u014d"+
		"\u0001\u0000\u0000\u0000@D\u0003&\u0013\u0000AD\u0003\"\u0011\u0000BD"+
		"\u0003\u0018\f\u0000C@\u0001\u0000\u0000\u0000CA\u0001\u0000\u0000\u0000"+
		"CB\u0001\u0000\u0000\u0000DG\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000"+
		"\u0000EF\u0001\u0000\u0000\u0000FH\u0001\u0000\u0000\u0000GE\u0001\u0000"+
		"\u0000\u0000HI\u0005\u0000\u0000\u0001I\u0001\u0001\u0000\u0000\u0000"+
		"JK\u0007\u0000\u0000\u0000K\u0003\u0001\u0000\u0000\u0000LM\u0007\u0001"+
		"\u0000\u0000M\u0005\u0001\u0000\u0000\u0000NO\u0007\u0002\u0000\u0000"+
		"O\u0007\u0001\u0000\u0000\u0000PU\u0003\u0006\u0003\u0000QR\u0005\u001b"+
		"\u0000\u0000RT\u0005\u001c\u0000\u0000SQ\u0001\u0000\u0000\u0000TW\u0001"+
		"\u0000\u0000\u0000US\u0001\u0000\u0000\u0000UV\u0001\u0000\u0000\u0000"+
		"V\t\u0001\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000X\\\u0005\u001d\u0000"+
		"\u0000Y[\u0003\u0010\b\u0000ZY\u0001\u0000\u0000\u0000[^\u0001\u0000\u0000"+
		"\u0000\\Z\u0001\u0000\u0000\u0000\\]\u0001\u0000\u0000\u0000]_\u0001\u0000"+
		"\u0000\u0000^\\\u0001\u0000\u0000\u0000_`\u0005\u001e\u0000\u0000`\u000b"+
		"\u0001\u0000\u0000\u0000ae\u0003\u0002\u0001\u0000be\u0003\u001c\u000e"+
		"\u0000ce\u0003\u0004\u0002\u0000da\u0001\u0000\u0000\u0000db\u0001\u0000"+
		"\u0000\u0000dc\u0001\u0000\u0000\u0000e\r\u0001\u0000\u0000\u0000fg\u0006"+
		"\u0007\uffff\uffff\u0000gp\u0003\f\u0006\u0000hi\u0005\u0019\u0000\u0000"+
		"ij\u0003\u000e\u0007\u0000jk\u0005\u001a\u0000\u0000kp\u0001\u0000\u0000"+
		"\u0000lm\u0007\u0003\u0000\u0000mp\u0003\u000e\u0007\u000enp\u0003,\u0016"+
		"\u0000of\u0001\u0000\u0000\u0000oh\u0001\u0000\u0000\u0000ol\u0001\u0000"+
		"\u0000\u0000on\u0001\u0000\u0000\u0000p\u00ab\u0001\u0000\u0000\u0000"+
		"qr\n\f\u0000\u0000rs\u0007\u0004\u0000\u0000s\u00aa\u0003\u000e\u0007"+
		"\rtu\n\u000b\u0000\u0000uv\u0007\u0005\u0000\u0000v\u00aa\u0003\u000e"+
		"\u0007\fwx\n\n\u0000\u0000xy\u0007\u0006\u0000\u0000y\u00aa\u0003\u000e"+
		"\u0007\u000bz{\n\t\u0000\u0000{|\u0007\u0007\u0000\u0000|\u00aa\u0003"+
		"\u000e\u0007\n}~\n\b\u0000\u0000~\u007f\u0007\b\u0000\u0000\u007f\u00aa"+
		"\u0003\u000e\u0007\t\u0080\u0081\n\u0007\u0000\u0000\u0081\u0082\u0005"+
		"\u0012\u0000\u0000\u0082\u00aa\u0003\u000e\u0007\b\u0083\u0084\n\u0006"+
		"\u0000\u0000\u0084\u0085\u0005\u0014\u0000\u0000\u0085\u00aa\u0003\u000e"+
		"\u0007\u0007\u0086\u0087\n\u0005\u0000\u0000\u0087\u0088\u0005\u0013\u0000"+
		"\u0000\u0088\u00aa\u0003\u000e\u0007\u0006\u0089\u008a\n\u0004\u0000\u0000"+
		"\u008a\u008b\u0005\r\u0000\u0000\u008b\u00aa\u0003\u000e\u0007\u0005\u008c"+
		"\u008d\n\u0003\u0000\u0000\u008d\u008e\u0005\u000e\u0000\u0000\u008e\u00aa"+
		"\u0003\u000e\u0007\u0004\u008f\u0090\n\u0002\u0000\u0000\u0090\u0091\u0005"+
		"\u0006\u0000\u0000\u0091\u00aa\u0003\u000e\u0007\u0002\u0092\u0093\n\u0001"+
		"\u0000\u0000\u0093\u0094\u0005\u001f\u0000\u0000\u0094\u0095\u0003\u000e"+
		"\u0007\u0000\u0095\u0096\u0005 \u0000\u0000\u0096\u0097\u0003\u000e\u0007"+
		"\u0001\u0097\u00aa\u0001\u0000\u0000\u0000\u0098\u0099\n\u0012\u0000\u0000"+
		"\u0099\u009a\u0005\u0018\u0000\u0000\u009a\u00aa\u0003\u001c\u000e\u0000"+
		"\u009b\u009c\n\u0011\u0000\u0000\u009c\u009d\u0005\u0018\u0000\u0000\u009d"+
		"\u00aa\u0003\u0004\u0002\u0000\u009e\u00a3\n\u0010\u0000\u0000\u009f\u00a0"+
		"\u0005\u001b\u0000\u0000\u00a0\u00a1\u0003\u000e\u0007\u0000\u00a1\u00a2"+
		"\u0005\u001c\u0000\u0000\u00a2\u00a4\u0001\u0000\u0000\u0000\u00a3\u009f"+
		"\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000\u0000\u0000\u00a5\u00a3"+
		"\u0001\u0000\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000\u0000\u00a6\u00aa"+
		"\u0001\u0000\u0000\u0000\u00a7\u00a8\n\u000f\u0000\u0000\u00a8\u00aa\u0007"+
		"\t\u0000\u0000\u00a9q\u0001\u0000\u0000\u0000\u00a9t\u0001\u0000\u0000"+
		"\u0000\u00a9w\u0001\u0000\u0000\u0000\u00a9z\u0001\u0000\u0000\u0000\u00a9"+
		"}\u0001\u0000\u0000\u0000\u00a9\u0080\u0001\u0000\u0000\u0000\u00a9\u0083"+
		"\u0001\u0000\u0000\u0000\u00a9\u0086\u0001\u0000\u0000\u0000\u00a9\u0089"+
		"\u0001\u0000\u0000\u0000\u00a9\u008c\u0001\u0000\u0000\u0000\u00a9\u008f"+
		"\u0001\u0000\u0000\u0000\u00a9\u0092\u0001\u0000\u0000\u0000\u00a9\u0098"+
		"\u0001\u0000\u0000\u0000\u00a9\u009b\u0001\u0000\u0000\u0000\u00a9\u009e"+
		"\u0001\u0000\u0000\u0000\u00a9\u00a7\u0001\u0000\u0000\u0000\u00aa\u00ad"+
		"\u0001\u0000\u0000\u0000\u00ab\u00a9\u0001\u0000\u0000\u0000\u00ab\u00ac"+
		"\u0001\u0000\u0000\u0000\u00ac\u000f\u0001\u0000\u0000\u0000\u00ad\u00ab"+
		"\u0001\u0000\u0000\u0000\u00ae\u00b5\u0003\n\u0005\u0000\u00af\u00b5\u0003"+
		"&\u0013\u0000\u00b0\u00b5\u0003.\u0017\u0000\u00b1\u00b5\u00034\u001a"+
		"\u0000\u00b2\u00b5\u0003>\u001f\u0000\u00b3\u00b5\u00030\u0018\u0000\u00b4"+
		"\u00ae\u0001\u0000\u0000\u0000\u00b4\u00af\u0001\u0000\u0000\u0000\u00b4"+
		"\u00b0\u0001\u0000\u0000\u0000\u00b4\u00b1\u0001\u0000\u0000\u0000\u00b4"+
		"\u00b2\u0001\u0000\u0000\u0000\u00b4\u00b3\u0001\u0000\u0000\u0000\u00b5"+
		"\u0011\u0001\u0000\u0000\u0000\u00b6\u00b7\u0003\b\u0004\u0000\u00b7\u00be"+
		"\u00054\u0000\u0000\u00b8\u00b9\u0005\"\u0000\u0000\u00b9\u00ba\u0003"+
		"\b\u0004\u0000\u00ba\u00bb\u00054\u0000\u0000\u00bb\u00bd\u0001\u0000"+
		"\u0000\u0000\u00bc\u00b8\u0001\u0000\u0000\u0000\u00bd\u00c0\u0001\u0000"+
		"\u0000\u0000\u00be\u00bc\u0001\u0000\u0000\u0000\u00be\u00bf\u0001\u0000"+
		"\u0000\u0000\u00bf\u0013\u0001\u0000\u0000\u0000\u00c0\u00be\u0001\u0000"+
		"\u0000\u0000\u00c1\u00c3\u0005\u0019\u0000\u0000\u00c2\u00c4\u0003\u0012"+
		"\t\u0000\u00c3\u00c2\u0001\u0000\u0000\u0000\u00c3\u00c4\u0001\u0000\u0000"+
		"\u0000\u00c4\u00c5\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005\u001a\u0000"+
		"\u0000\u00c6\u0015\u0001\u0000\u0000\u0000\u00c7\u00ca\u0003\b\u0004\u0000"+
		"\u00c8\u00ca\u0005#\u0000\u0000\u00c9\u00c7\u0001\u0000\u0000\u0000\u00c9"+
		"\u00c8\u0001\u0000\u0000\u0000\u00ca\u0017\u0001\u0000\u0000\u0000\u00cb"+
		"\u00cc\u0003\u0016\u000b\u0000\u00cc\u00cd\u00054\u0000\u0000\u00cd\u00ce"+
		"\u0003\u0014\n\u0000\u00ce\u00cf\u0003\n\u0005\u0000\u00cf\u0019\u0001"+
		"\u0000\u0000\u0000\u00d0\u00d5\u0003\u000e\u0007\u0000\u00d1\u00d2\u0005"+
		"\"\u0000\u0000\u00d2\u00d4\u0003\u000e\u0007\u0000\u00d3\u00d1\u0001\u0000"+
		"\u0000\u0000\u00d4\u00d7\u0001\u0000\u0000\u0000\u00d5\u00d3\u0001\u0000"+
		"\u0000\u0000\u00d5\u00d6\u0001\u0000\u0000\u0000\u00d6\u001b\u0001\u0000"+
		"\u0000\u0000\u00d7\u00d5\u0001\u0000\u0000\u0000\u00d8\u00d9\u00054\u0000"+
		"\u0000\u00d9\u00db\u0005\u0019\u0000\u0000\u00da\u00dc\u0003\u001a\r\u0000"+
		"\u00db\u00da\u0001\u0000\u0000\u0000\u00db\u00dc\u0001\u0000\u0000\u0000"+
		"\u00dc\u00dd\u0001\u0000\u0000\u0000\u00dd\u00de\u0005\u001a\u0000\u0000"+
		"\u00de\u001d\u0001\u0000\u0000\u0000\u00df\u00e0\u00054\u0000\u0000\u00e0"+
		"\u00e1\u0005\u0019\u0000\u0000\u00e1\u00e2\u0005\u001a\u0000\u0000\u00e2"+
		"\u00e3\u0003\n\u0005\u0000\u00e3\u001f\u0001\u0000\u0000\u0000\u00e4\u00ea"+
		"\u0005\u001d\u0000\u0000\u00e5\u00e9\u0003&\u0013\u0000\u00e6\u00e9\u0003"+
		"\u0018\f\u0000\u00e7\u00e9\u0003\u001e\u000f\u0000\u00e8\u00e5\u0001\u0000"+
		"\u0000\u0000\u00e8\u00e6\u0001\u0000\u0000\u0000\u00e8\u00e7\u0001\u0000"+
		"\u0000\u0000\u00e9\u00ec\u0001\u0000\u0000\u0000\u00ea\u00e8\u0001\u0000"+
		"\u0000\u0000\u00ea\u00eb\u0001\u0000\u0000\u0000\u00eb\u00ed\u0001\u0000"+
		"\u0000\u0000\u00ec\u00ea\u0001\u0000\u0000\u0000\u00ed\u00ee\u0005\u001e"+
		"\u0000\u0000\u00ee!\u0001\u0000\u0000\u0000\u00ef\u00f0\u0005(\u0000\u0000"+
		"\u00f0\u00f1\u00054\u0000\u0000\u00f1\u00f2\u0003 \u0010\u0000\u00f2\u00f3"+
		"\u0005!\u0000\u0000\u00f3#\u0001\u0000\u0000\u0000\u00f4\u00f7\u00054"+
		"\u0000\u0000\u00f5\u00f6\u0005\u0006\u0000\u0000\u00f6\u00f8\u0003\u000e"+
		"\u0007\u0000\u00f7\u00f5\u0001\u0000\u0000\u0000\u00f7\u00f8\u0001\u0000"+
		"\u0000\u0000\u00f8%\u0001\u0000\u0000\u0000\u00f9\u00fa\u0003\b\u0004"+
		"\u0000\u00fa\u00ff\u0003$\u0012\u0000\u00fb\u00fc\u0005\"\u0000\u0000"+
		"\u00fc\u00fe\u0003$\u0012\u0000\u00fd\u00fb\u0001\u0000\u0000\u0000\u00fe"+
		"\u0101\u0001\u0000\u0000\u0000\u00ff\u00fd\u0001\u0000\u0000\u0000\u00ff"+
		"\u0100\u0001\u0000\u0000\u0000\u0100\u0102\u0001\u0000\u0000\u0000\u0101"+
		"\u00ff\u0001\u0000\u0000\u0000\u0102\u0103\u0005!\u0000\u0000\u0103\'"+
		"\u0001\u0000\u0000\u0000\u0104\u0105\u0005\'\u0000\u0000\u0105\u010b\u0003"+
		"\u0006\u0003\u0000\u0106\u0108\u0005\u001b\u0000\u0000\u0107\u0109\u0003"+
		"\u000e\u0007\u0000\u0108\u0107\u0001\u0000\u0000\u0000\u0108\u0109\u0001"+
		"\u0000\u0000\u0000\u0109\u010a\u0001\u0000\u0000\u0000\u010a\u010c\u0005"+
		"\u001c\u0000\u0000\u010b\u0106\u0001\u0000\u0000\u0000\u010c\u010d\u0001"+
		"\u0000\u0000\u0000\u010d\u010b\u0001\u0000\u0000\u0000\u010d\u010e\u0001"+
		"\u0000\u0000\u0000\u010e)\u0001\u0000\u0000\u0000\u010f\u0110\u0005\'"+
		"\u0000\u0000\u0110\u0113\u00054\u0000\u0000\u0111\u0112\u0005\u0019\u0000"+
		"\u0000\u0112\u0114\u0005\u001a\u0000\u0000\u0113\u0111\u0001\u0000\u0000"+
		"\u0000\u0113\u0114\u0001\u0000\u0000\u0000\u0114+\u0001\u0000\u0000\u0000"+
		"\u0115\u0118\u0003(\u0014\u0000\u0116\u0118\u0003*\u0015\u0000\u0117\u0115"+
		"\u0001\u0000\u0000\u0000\u0117\u0116\u0001\u0000\u0000\u0000\u0118-\u0001"+
		"\u0000\u0000\u0000\u0119\u011b\u0003\u000e\u0007\u0000\u011a\u0119\u0001"+
		"\u0000\u0000\u0000\u011a\u011b\u0001\u0000\u0000\u0000\u011b\u011c\u0001"+
		"\u0000\u0000\u0000\u011c\u011d\u0005!\u0000\u0000\u011d/\u0001\u0000\u0000"+
		"\u0000\u011e\u0125\u00051\u0000\u0000\u011f\u0125\u00052\u0000\u0000\u0120"+
		"\u0122\u00053\u0000\u0000\u0121\u0123\u0003\u000e\u0007\u0000\u0122\u0121"+
		"\u0001\u0000\u0000\u0000\u0122\u0123\u0001\u0000\u0000\u0000\u0123\u0125"+
		"\u0001\u0000\u0000\u0000\u0124\u011e\u0001\u0000\u0000\u0000\u0124\u011f"+
		"\u0001\u0000\u0000\u0000\u0124\u0120\u0001\u0000\u0000\u0000\u0125\u0126"+
		"\u0001\u0000\u0000\u0000\u0126\u0127\u0005!\u0000\u0000\u01271\u0001\u0000"+
		"\u0000\u0000\u0128\u0129\u0003\u000e\u0007\u0000\u01293\u0001\u0000\u0000"+
		"\u0000\u012a\u012b\u0005-\u0000\u0000\u012b\u012c\u0005\u0019\u0000\u0000"+
		"\u012c\u012d\u00032\u0019\u0000\u012d\u012e\u0005\u001a\u0000\u0000\u012e"+
		"\u0131\u0003\u0010\b\u0000\u012f\u0130\u0005.\u0000\u0000\u0130\u0132"+
		"\u0003\u0010\b\u0000\u0131\u012f\u0001\u0000\u0000\u0000\u0131\u0132\u0001"+
		"\u0000\u0000\u0000\u01325\u0001\u0000\u0000\u0000\u0133\u0134\u00050\u0000"+
		"\u0000\u0134\u0135\u0005\u0019\u0000\u0000\u0135\u0136\u00032\u0019\u0000"+
		"\u0136\u0137\u0005\u001a\u0000\u0000\u0137\u0138\u0003\u0010\b\u0000\u0138"+
		"7\u0001\u0000\u0000\u0000\u0139\u013c\u0003.\u0017\u0000\u013a\u013c\u0003"+
		"&\u0013\u0000\u013b\u0139\u0001\u0000\u0000\u0000\u013b\u013a\u0001\u0000"+
		"\u0000\u0000\u013c9\u0001\u0000\u0000\u0000\u013d\u013f\u00038\u001c\u0000"+
		"\u013e\u0140\u00032\u0019\u0000\u013f\u013e\u0001\u0000\u0000\u0000\u013f"+
		"\u0140\u0001\u0000\u0000\u0000\u0140\u0141\u0001\u0000\u0000\u0000\u0141"+
		"\u0143\u0005!\u0000\u0000\u0142\u0144\u0003\u000e\u0007\u0000\u0143\u0142"+
		"\u0001\u0000\u0000\u0000\u0143\u0144\u0001\u0000\u0000\u0000\u0144;\u0001"+
		"\u0000\u0000\u0000\u0145\u0146\u0005/\u0000\u0000\u0146\u0147\u0005\u0019"+
		"\u0000\u0000\u0147\u0148\u0003:\u001d\u0000\u0148\u0149\u0005\u001a\u0000"+
		"\u0000\u0149\u014a\u0003\u0010\b\u0000\u014a=\u0001\u0000\u0000\u0000"+
		"\u014b\u014e\u00036\u001b\u0000\u014c\u014e\u0003<\u001e\u0000\u014d\u014b"+
		"\u0001\u0000\u0000\u0000\u014d\u014c\u0001\u0000\u0000\u0000\u014e?\u0001"+
		"\u0000\u0000\u0000\u001fCEU\\do\u00a5\u00a9\u00ab\u00b4\u00be\u00c3\u00c9"+
		"\u00d5\u00db\u00e8\u00ea\u00f7\u00ff\u0108\u010d\u0113\u0117\u011a\u0122"+
		"\u0124\u0131\u013b\u013f\u0143\u014d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}