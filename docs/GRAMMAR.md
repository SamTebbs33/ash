# Tokens
These are the tokens that can be read in by the lexer (in notation similar to regexes), excluding punctuation.

* Binary integer: 0b[0|1]+
* Hex integer: 0x[a-fA-F0-9]+
* Octal integer: 0o[0-7]+
* Decimal integer: -?[0-9]+
* Double: -?[0-9].[0-9]
* Float: -?[0-9].[0-9]f
* Boolean: true|false
* Long: -?[0-9]+L
* Character: '[unicode_char]'
* String: "[unicode_char]*"
* Comment: //.*
* Binary operator: + | - | * | / | < | > | <= | >= | == | && | ^^ | || | << | >> | & | ^ | |
* Unary operator: ++ | -- | ! | ~
* Assignment operator: = | += | -= | *= | /= | &= | ^= | |=
* Identifier: [unicode_letter][unicode_char]*
* Whitespace: \n |  | \t

# Parser nodes
These are the parser nodes that can be read in with the parser (in notation similar to regexes).

* File: package? import* typedec+
* Package: **package** qualifiedname
* Import: **import** qualifiedname
* QualifiedName: identifier[.identifier]*
* TypeDec: classdec | enumdec | interfacedec
* ClassDec: **class** id args? (: types)? **{** classstmt* }
* Args: **(** arg? (, arg)* )
* Arg: id **:** type
* Type: id (**[]**)*
* Types: id (, id)*
* ClassStmt: funcdec | vardec
* FuncDec: **func** id args (**:** type) funcblock
* FuncBlock: ( **{** funcstmt+ } ) | (**->** funcstmt )
* VarDec: (**var** | **const**) id (**:** type) (**=** expr)?
* FuncStmt: funccall | vardec | varassign
* FuncCall: ((funccall | variable) .)* id ( expressions )
* Variable:  ((funccall | variable) .)* id ([ expression ])
* VarAssign: variable assignop expression
* Expressions: (expression (, expressions)*)?
* Expression: integer | string | char | boolean | float | double | long | (unaryop expression) | (expression unaryop) | (expression **?** expression **:** expression) | (**(** expression **)**) | variable | funccall | **this**
