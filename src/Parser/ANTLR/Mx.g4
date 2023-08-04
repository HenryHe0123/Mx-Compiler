grammar Mx;
import MxLexerRules;

program: (varDef | classDef | funcDef)* EOF;

literal: IntLiteral | StringLiteral | True | False | Null;

variable: Identifier | This;

simpleType: Int | Bool | String | Identifier;

typename: simpleType (LBrack RBrack)*; //include array type

suite: LBrace statement* RBrace;

primary: literal | callFunction | variable;

expression
    : primary                                                                  #atomExpr
    | LParen expression RParen                                                 #bracketExpr
    | expression Dot callFunction                                              #memberExpr
    | expression Dot variable                                                  #memberExpr
    | expression (LBrack expression RBrack)+                                   #arrayExpr
    | expression (AddAdd | SubSub)                                             #postfixUpdateExpr
    | <assoc=right> (AddAdd | SubSub | Not | Invert | Sub) expression          #unaryExpr
    | newExpression                                                            #newExpr
    | expression op=(Mul | Div | Mod) expression                               #binaryExpr
    | expression op=(Add | Sub) expression                                     #binaryExpr
    | expression op=(LShift | RShift) expression                               #binaryExpr
    | expression op=(Less | Greater | LessEqual | GreaterEqual) expression     #binaryExpr
    | expression op=(Equal | NotEqual) expression                              #binaryExpr
    | expression op=And expression                                             #binaryExpr
    | expression op=Xor expression                                             #binaryExpr
    | expression op=Or expression                                              #binaryExpr
    | expression op=AndAnd expression                                          #binaryExpr
    | expression op=OrOr expression                                            #binaryExpr
    | <assoc=right> expression Assign expression                               #assignExpr
    | <assoc=right> expression Question expression Colon expression            #ternaryExpr
    ;

statement
    : suite             #blockStmt
    | varDef            #varDefStmt
    | exprStatement     #exprStmt
    | branchStatement   #branchStmt
    | forStatement      #forStmt
    | whileStatement    #whileStmt
    | ctrlStatement     #ctrlStmt
    | returnStatement   #returnStmt
    ;

//function
funcParameterList: typename Identifier (Comma typename Identifier)*;
funcParameter: LParen funcParameterList? RParen;
returnType: typename | Void;

funcDef: returnType Identifier funcParameter body=suite;

funcParameterCall: expression (Comma expression)*;
callFunction: Identifier LParen funcParameterCall? RParen;

//class
classConstructor: Identifier LParen RParen suite;
classBody: LBrace (varDef | funcDef | classConstructor)* RBrace;

classDef: Class Identifier classBody Semi;

//varieble
varDeclareUnit: Identifier (Assign expression)?;

varDef: typename varDeclareUnit (Comma varDeclareUnit)* Semi;

//details (of expression/statement)
newArrayIndex: LBrack expression? RBrack; //cannot be inline

newArrayExpr : New simpleType newArrayIndex+;

newClassExpr: New Identifier (LParen RParen)?;

newExpression: newArrayExpr | newClassExpr;

exprStatement: expression? Semi; //maybe empty

ctrlStatement: (Break | Continue) Semi;

condition: expression;

branchStatement: If LParen condition RParen ifStatement=statement
                (Else elseStatement=statement)?;

whileStatement: While LParen condition RParen body=statement;

forInit: exprStatement | varDef; //already include ? and ;
forInitCondition: forInit condition? Semi step=expression?;
forStatement: For LParen forInitCondition RParen body=statement;

returnStatement: Return expression? Semi;