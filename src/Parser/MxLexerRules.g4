lexer grammar MxLexerRules;

// Operator
Add : '+';
Sub : '-';
Mul : '*';
Div : '/';
Mod : '%';
Assign : '=';

Less : '<';
LessEqual : '<=';
Greater : '>';
GreaterEqual : '>=';
Equal : '==';
NotEqual : '!=';

AndAnd : '&&';
OrOr : '||';
Not : '!';

RShift : '>>';
LShift : '<<';
And : '&';
Or : '|';
Xor : '^';
Invert : '~';

AddAdd : '++';
SubSub : '--';

Dot : '.';

LParen : '(';
RParen : ')';
LBrack : '[';
RBrack : ']';
LBrace : '{';
RBrace : '}';

Question : '?';
Colon : ':';
Semi : ';';
Comma : ',';

// Keyword
Void: 'void';
Bool: 'bool';
Int : 'int';
String: 'string';
New : 'new';
Class : 'class';
Null : 'null';
True : 'true';
False : 'false';
This : 'this';
If : 'if';
Else : 'else';
For : 'for';
While : 'while';
Break : 'break';
Continue : 'continue';
Return : 'return';

// Identifier (lexer order counts!)
Identifier : ([a-zA-Z])([0-9_a-zA-Z])*;

// Skip
WhiteSpace : [ \t\r\n]+ -> skip ;
LineComment : '//' ~[\r\n]* -> skip;
BlockComment : '/*' .*? '*/' -> skip;

// Literal
IntLiteral : [1-9][0-9]* | '0' ;
StringLiteral : '"' (ESC | .)*? '"';

// Escape Character
fragment ESC : '\\"' | '\\\\' | '\\n';
