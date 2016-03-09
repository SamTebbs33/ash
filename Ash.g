grammar Ash;

// skip spaces, tabs, newlines
WS : [ \t\r\n]+ -> skip ;
OCT_INT : '0o' [0-7]+ ;
BIN_INT : '0b' [0|1]+ ;
HEX_INT : '0x' [0-9a-fA-F]+ ;
INT : '-'? [0-9]+ ;

FLOAT : '-'? [0-9]+ '.' [0-9]+ 'f' ;
DOUBLE : '-'? [0-9]+ '.' [0-9]+ ;
STRING : '"' [^"]* '"' ;
CHAR : '\'' . '\'' ;
BOOL : 'true' | 'false' ;
TYPE : 'bool' | 'double' | 'float' | 'long' | 'int' | 'short' | 'byte' | 'char' ;
LAMBDA : '->' ;
COMPOUND_ASSIGN_OP : '-=' | '+=' | '*=' | '/=' | '%=' | '**=' | '^=' | '&=' | '|=' | '<<=' | '||=' ;
OP : ['..'+ | '+' | '-' | '!' | '~' | '=' | '*' | '/' | '%' | '^' | '&' | '<' | '>' | '@' | '#' | '?']+;
ARRAY_DIM : '[]' ;

ARROW : '=>' ;
PARENL : '(' ;
PARENR : ')' ;
BRACEL : '{' ;
BRACER : '}' ;
BRACKETL : '[' ;
BRACKETR : ']' ;
DOT : '.' ;
COMMA : ',' ;
COLON : ':' ;

VAR : 'var' ;
CONST : 'const' ;
CLASS : 'class' ;
OPERATOR : 'operator' ;
ENUM : 'enum' ;
INTTERFACE : 'interface' ;
IMPORT : 'import' ;
PACKAGE : 'package' ;
RETURN : 'return' ;
BREAK : 'break' ;
CONTINUE : 'continue' ;
DEFER : 'defer' ;
AS : 'as' ;
IS : 'is' ;
NEW : 'new' ;
OP_TYPE : 'binary' | 'prefix' | 'postfix' ;

MODIFIER : 'public' | 'private' | 'protected' | 'final' | 'native' | 'override' | 'static' | 'synchronised' ;
THIS : 'this' ;
SUPER : 'super' ;
NULL : 'null' ;

IF : 'if' ;
ELSE : 'else' ;
WHILE : 'while' ;
FOR : 'for' ;
MATCH: 'match' ;
IN : 'in' ;