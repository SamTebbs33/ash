grammar Ash;

STRING : '"' ~["\\]* '"' ;
WS : (' ' | '\t' | '\r' | '\n')+ -> skip ;

typeDef : COLON type ;
file : packageDec? importDec* classDec+ EOF ;
packageDec : PACKAGE qualifiedName ;
qualifiedName : ID (DOT ID)* ;
importDec : IMPORT qualifiedName (aliasedImport | multiImport)? ;
aliasedImport : AS ID ;
multiImport : (COMMA ID)+ ;
type : (ID | qualifiedName | PRIMITIVE) ARRAY_DIM*  ;
classDec : mods CLASS ID params? typeDecSupers? classBlock? ;
params : PARENL funcParam (COMMA funcParam)* PARENR ;
typeDecSupers : COLON qualifiedName (COMMA qualifiedName)* ;
classBlock : BRACEL (varDec | funcDec)* BRACER ;
varDec : mods VAR ID typeDef? ('=' expr)? ;
funcDec : mods FUNC ID params? (LAMBDA type)? funcBlock;
funcBlock : bracedBlock | (ASSIGN_OP (expr | stmt)) ;
bracedBlock : BRACEL stmt* BRACER ;
funcParam : ID typeDef ;
mods : MODIFIER* ;
expr : INT
    | HEX_INT
    | OCT_INT
    | BIN_INT
    | FLOAT
    | DOUBLE
    | STRING
    | CHAR
    | BOOL
    | PARENL bracketed=expr PARENR
    | expr binaryOp=OP expr
    | prefixOp=OP expr
    | expr postfixOp=OP
    | var
    | funcCall
    | BRACEL (expr (list=COMMA expr)*)? BRACER
    | expr QUESTION expr COLON expr ;
stmt : varAssignment
    | funcCall
    | ifStmt
    | returnStmt
    | bracedBlock
    | varDec ;
ifStmt : IF expr bracedBlock elseIfStmt? elseStmt? ;
elseIfStmt : ELSE ifStmt ;
elseStmt : ELSE bracedBlock ;
returnStmt : RETURN expr? ;
suffix : DOT (var | funcCall) ;
var : ID suffix? ;
varAssignment : var (ASSIGN_OP | COMPOUND_ASSIGN_OP) expr ;
funcCall : ID PARENL (expr (COMMA expr)*)? PARENR suffix? ;

// skip spaces, tabs, newlines
OCT_INT : '0o' [0-7]+ ;
BIN_INT : '0b' [0|1]+ ;
HEX_INT : '0x' [0-9a-fA-F]+ ;
INT : '-'? [0-9]+ ;

ASSIGN_OP : '=' ;
QUESTION : '?' ;
DOT : '.' ;
FLOAT : '-'? [0-9]+ '.' [0-9]+ 'f' ;
DOUBLE : '-'? [0-9]+ '.' [0-9]+ ;
CHAR : '\'' . '\'' ;
BOOL : 'true' | 'false' ;
PRIMITIVE : 'bool' | 'double' | 'float' | 'long' | 'int' | 'short' | 'byte' | 'char' ;
LAMBDA : '->' ;

COMPOUND_ASSIGN_OP : '-=' | '+=' | '*=' | '/=' | '%=' | '**=' | '^=' | '&=' | '|=' | '<<=' | '||=' ;
OP : ['..' | '+' | '-' | '!' | '~' | ASSIGN_OP | '*' | '/' | '%' | '\^' | '&' | '<' | '>' | '@' | '#' | '?']+;

ARRAY_DIM : '[]' ;

ARROW : '=>' ;
PARENL : '(' ;
PARENR : ')' ;
BRACEL : '{' ;
BRACER : '}' ;
BRACKETL : '[' ;
BRACKETR : ']' ;

COMMA : ',' ;
COLON : ':' ;

VAR : 'var' ;
FUNC : 'func' ;
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

ID : [a-zA-Z] [a-zA-Z0-9]* ;
