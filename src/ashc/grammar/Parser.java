package ashc.grammar;

import java.util.*;

import ashc.grammar.Lexer.*;
import ashc.grammar.Lexer.TokenType;
import ashc.grammar.Lexer.UnexpectedTokenException;
import ashc.grammar.Node.IFuncStmt;
import ashc.grammar.Node.NodeType;
import ashc.grammar.Lexer.*;
import ashc.grammar.Node.*;

/**
 * Ash
 *
 * @author samtebbs, 22:23:42 - 20 May 2015
 */
public class Parser {

    private final LinkedList<Token> tokens = new LinkedList<Token>();
    private final Lexer lexer;
    private int pointer = 0, line = 1, column = 1;
    public boolean silenceErrors = false;

    public Parser(final Lexer lexer) {
	this.lexer = lexer;
	while (true)
	    try {
		final Token token = lexer.getNextToken();
		if (token == null) break; // we have passed EOF
		tokens.add(token);
	    } catch (final InvalidTokenException e) {
		System.err.printf("Error:%d:%d Invalid token (%s)%n", e.line, e.column, e.data);
	    }
    }

    /**
     * Start the parser and return an AST node representing the file
     * @return NodeFile
     *
     */
    public NodeFile start() {
	try {
	    return parseFile();
	} catch (final UnexpectedTokenException e) {
	    final int line = e.token.line, colStart = e.token.columnStart, colEnd = e.token.columnEnd;
	    System.err.printf("Error:%d:%d-%d %s%n", line, colStart, colEnd, e.msg);

	    // Print out the line and location of the error
	    if (line <= lexer.lines.size()) {
		System.out.println(lexer.lines.get(line - 1));
		for (int i = 0; i < colStart - 1; i++)
		    System.out.print(" ");
		System.out.print("^");
		if (colEnd - colStart > 1) {
		    for (int i = colStart; i < colEnd - 2; i++)
			System.out.print("-");
		    System.out.println("^");
		} else System.out.println();
	    }
	}
	return null;
    }

    /**
     * Return the next token, or null if we have passed the EOF token
     * @return {@link Token}
     * @throws UnexpectedTokenException
     *
     */
    private Token getNext() throws UnexpectedTokenException {
	if (pointer < tokens.size()) {
	    final Token t = tokens.get(pointer++);
	    line = t.line;
	    column = t.columnStart;
	    return t;
	}
	return null;
    }

    /**
     * Move back one token
     *
     *
     */
    private void rewind() {
	pointer--;
    }

    /**
     * Move back by **a** tokens
     * @param a
     *
     */
    private void rewind(final int a) {
	pointer -= a;
    }

    /**
     * Expect the next token to have the same type as the argument and return
     * it, else throw an error
     * @param t
     *        - The token type expected
     * @return {@link Token}
     * @throws UnexpectedTokenException
     *
     */
    private Token expect(final TokenType t) throws UnexpectedTokenException {
	final Token token = getNext();
	if (token.type != t && !silenceErrors) throw new UnexpectedTokenException(token, t);
	return token;
    }

    /**
     * Expect the next token to have the same type as any one of the arguments
     * and return it, else throw an error
     * @param t
     *        - The token types expected
     * @return {@link Token}
     * @throws UnexpectedTokenException
     *
     */
    private Token expect(final TokenType... t) throws UnexpectedTokenException {
	final Token token = getNext();
	boolean found = false;
	for (final TokenType type : t)
	    if (token.type == type) {
		found = true;
		break;
	    }
	if (!found && !silenceErrors) throw new UnexpectedTokenException(token, t);
	return token;
    }

    /**
     * Parse an optional list of import declarations
     * @return {@link LinkedList}
     * @throws UnexpectedTokenException
     *
     */
    private LinkedList<NodeImport> parseImports() throws UnexpectedTokenException {
	final LinkedList<NodeImport> imports = new LinkedList<Node.NodeImport>();
	while (getNext().type == TokenType.IMPORT) {
	    final NodeQualifiedName name = parseQualifiedName();
	    imports.add(new NodeImport(line, column, name));
	}
	rewind();
	return imports;
    }

    /**
     * Parses and returns a qualified name node
     * @return {@link NodeQualifiedName}
     * @throws UnexpectedTokenException
     *
     */
    private NodeQualifiedName parseQualifiedName() throws UnexpectedTokenException {
	Token id = expect(TokenType.ID);
	final NodeQualifiedName name = new NodeQualifiedName(id.line, id.columnStart);
	name.add(id.data);
	while (getNext().type == TokenType.DOT) {
	    id = expect(TokenType.ID);
	    name.add(id.data);
	}
	rewind();
	return name;
    }

    /**
     * Parses and returns an optional package declaration
     * @return {@link NodePackage}
     * @throws UnexpectedTokenException
     *
     */
    private NodePackage parsePackage() throws UnexpectedTokenException {
	if (getNext().type == TokenType.PACKAGE) {
	    final NodeQualifiedName name = parseQualifiedName();
	    if (name != null) return new NodePackage(name.line, name.column, name);
	}
	rewind();
	return null;
    }

    /**
     * Returns true if the argument is a modifier token type
     * @param boolean
     * @return
     *
     */
    private boolean isModifier(final TokenType type) {
	return type == TokenType.PUBLIC || type == TokenType.PRIVATE || type == TokenType.PROTECTED || type == TokenType.FINAL || type == TokenType.REQUIRED || type == TokenType.NATIVE || type == TokenType.OVERRIDE || type == TokenType.STANDARD || type == TokenType.STATIC;
    }

    private LinkedList<NodeModifier> parseMods() throws UnexpectedTokenException {
	final LinkedList<NodeModifier> mods = new LinkedList<NodeModifier>();
	Token token = getNext();
	TokenType type = token.type;
	while (isModifier(type)) {
	    mods.add(new NodeModifier(line, column, token.data));
	    token = getNext();
	    type = token.type;
	}
	rewind();
	return mods;
    }

    /**
     * class id args? (: types)? { block }
     */
    private NodeClassDec parseClassDec(final LinkedList<NodeModifier> mods) throws UnexpectedTokenException {
	final Token id = expect(TokenType.ID);
	NodeArgs args = null;
	NodeTypes types = null;

	if (getNext().type == TokenType.PARENL) {
	    rewind();
	    args = parseArgs();
	} else rewind();

	if (getNext().type == TokenType.COLON) types = parseSuperTypes();
	else rewind();

	final NodeClassBlock block = parseClassBlock();
	return new NodeClassDec(id.line, id.columnStart, mods, types, args, id, block);
    }

    private NodeClassBlock parseClassBlock() throws UnexpectedTokenException {
	final NodeClassBlock block = new NodeClassBlock();
	if(getNext().type == TokenType.BRACEL){
	while (getNext().type != TokenType.BRACER) {
	    rewind();
	    final LinkedList<NodeModifier> mods = parseMods();
	    final Token token = expect(TokenType.FUNC, TokenType.CONST, TokenType.VAR, TokenType.BRACER);
	    rewind();
	    switch (token.type) {
		case VAR:
		case CONST:
		    block.add(parseVarDec(mods));
		    continue;
		case FUNC:
		    block.add(parseFuncDec(true, mods));
		    continue;
	    }
	}
	}else rewind();
	return block;
    }

    /**
     * func id(args?) (: type)?
     */
    private NodeFuncDec parseFuncDec(final boolean needsBody, final LinkedList<NodeModifier> mods) throws UnexpectedTokenException {
	expect(TokenType.FUNC);
	final Token id = expect(TokenType.ID);
	final NodeArgs args = parseArgs();
	NodeType type = null, throwsType = null;
	NodeFuncBlock block = new NodeFuncBlock();
	expect(TokenType.COLON, TokenType.ARROW, TokenType.LAMBDAARROW, TokenType.BRACEL);
	rewind();

	if (getNext().type == TokenType.COLON) type = parseType();
	else {
	    type = new NodeType("void");
	    rewind();
	}

	if (getNext().type == TokenType.ARROW) throwsType = parseType();
	else rewind();

	if (needsBody) block = parseFuncBlock();

	return new NodeFuncDec(id.line, id.columnStart, mods, id.data, args, type, throwsType, block);
    }

    private NodeFuncBlock parseFuncBlock() throws UnexpectedTokenException {
	return parseFuncBlock(true);
    }

    private NodeFuncBlock parseFuncBlock(boolean allowSingleLine) throws UnexpectedTokenException {
	final NodeFuncBlock block = new NodeFuncBlock();
	Token token = null;
	 if(allowSingleLine) token  = expect(TokenType.LAMBDAARROW, TokenType.BRACEL);
	 else token = expect(TokenType.BRACEL);
	if (token.type == TokenType.BRACEL) while (getNext().type != TokenType.BRACER) {
	    rewind();
	    block.add(parseFuncStmt());
	}
	else block.singleLineExpr = parseExpression();
	return block;
    }

    private IFuncStmt parseFuncStmt() throws UnexpectedTokenException {
	final Token token = expect(TokenType.ID, TokenType.SELF, TokenType.IF, TokenType.WHILE, TokenType.FOR, TokenType.VAR, TokenType.CONST, TokenType.RETURN);
	switch (token.type) {
	    case RETURN:
		rewind();
		silenceErrors = true;
		final IExpression expr = parseExpression();
		silenceErrors = false;
		return new NodeReturn(token.line, token.columnStart, expr);
	    case ID:
	    case SELF:
		rewind();
		final IFuncStmt stmt = parsePrefix();
		if (stmt instanceof NodeVariable) {
		    final Token assignOp = expect(TokenType.ASSIGNOP, TokenType.COMPOUNDASSIGNOP);
		    return new NodeVarAssign(assignOp.line, assignOp.columnStart, (NodeVariable) stmt, assignOp.data, parseExpression());
		} else return stmt;
	    case IF:
		return parseIfStmt();
	    case WHILE:
		return parseWhileStmt();
	    case FOR:
		return parseForStmt();
	    case VAR:
	    case CONST:
		rewind();
		return parseVarDec(null);
	}
	return null;
    }

    private IFuncStmt parseForStmt() throws UnexpectedTokenException {
	int parens = 0;
	while(getNext().type == TokenType.PARENL) parens++;
	rewind();
	Token next = expect(TokenType.ID, TokenType.VAR, TokenType.COMMA);
	if(next.type == TokenType.ID){
	    expect(TokenType.IN);
	    IExpression expr = parseExpression();
	    while(parens-- > 0) expect(TokenType.PARENR);
	    NodeForIn forIn = new NodeForIn(line, column, next.data, expr, parseFuncBlock());
	    return forIn;
	}else{
	    IFuncStmt initStmt = null, endStmt = null;
	    IExpression condition = null;
	    if(next.type != TokenType.COMMA){
		initStmt = parseFuncStmt();
		expect(TokenType.COMMA);
	    }
	    if(getNext().type != TokenType.COMMA){
		rewind();
		condition = parseExpression();
		expect(TokenType.COMMA);
	    }
	    endStmt = parseFuncStmt();
	    while(parens-- > 0) expect(TokenType.PARENR);
	    return new NodeForNormal(line, column, initStmt, condition, endStmt, parseFuncBlock());
	}
    }

    private IFuncStmt parseWhileStmt() throws UnexpectedTokenException {
	return new NodeWhile(line, column, parseExpression(), parseFuncBlock());
    }

    private NodeIf parseIfStmt() throws UnexpectedTokenException {
	NodeIf ifStmt = new NodeIf(line, column, parseExpression(), parseFuncBlock());
	if(getNext().type == TokenType.ELSE) {
	    TokenType next = expect(TokenType.IF, TokenType.BRACEL).type;
	    if(next == TokenType.IF) ifStmt.elseStmt = parseIfStmt();
	    else{
		rewind();
		ifStmt.elseStmt = new NodeIf(line, column, null, parseFuncBlock());
	    }
	}else rewind();
	return ifStmt;
    }

    // A prefix is just another name for a func call or variable
    private NodePrefix parsePrefix() throws UnexpectedTokenException {
	NodePrefix prefix = null;
	do {
	    final Token id = expect(TokenType.ID, TokenType.SELF);
	    if (getNext().type == TokenType.PARENL) {
		rewind();
		final NodeExprs exprs = parseCallArgs(TokenType.PARENL, TokenType.PARENR);
		prefix = new NodeFuncCall(id.line, id.columnStart, id.data, exprs, prefix);
	    } else {
		rewind();
		prefix = new NodeVariable(id.line, id.columnStart, id.data, prefix);
		while (getNext().type == TokenType.BRACKETL) {
		    ((NodeVariable) prefix).exprs.exprs.add(parseExpression());
		    expect(TokenType.BRACKETR);
		}
		rewind();
	    }

	} while (getNext().type == TokenType.DOT);
	rewind();
	return prefix;
    }

    private NodeExprs parseCallArgs(TokenType start, TokenType end) throws UnexpectedTokenException {
	final NodeExprs exprs = new NodeExprs();
	expect(start);
	Token next = getNext();
	if (next.type == end) return exprs;
	else rewind();

	do {
	    final IExpression expr = parseExpression();
	    // System.out.println(expr);
	    exprs.add(expr);
	    next = getNext();
	} while (next.type == TokenType.COMMA);
	rewind();
	expect(end);
	return exprs;
    }

    private IExpression parsePrimaryExpression() throws UnexpectedTokenException {
	Token next = expect(TokenType.NULL, TokenType.ID, TokenType.THIS, TokenType.SELF, TokenType.UNARYOP, TokenType.BRACKETL, TokenType.PARENL, TokenType.OCTINT, TokenType.HEXINT, TokenType.BININT, TokenType.INT, TokenType.LONG, TokenType.FLOAT, TokenType.DOUBLE, TokenType.STRING, TokenType.CHAR, TokenType.BOOL);
	IExpression expr = null;

	switch (next.type) {
	    case NULL:
		expr = new NodeNull();
		break;
	    case ID:
		rewind();
		expr = parsePrefix();
		break;
	    case THIS:
		expr = new NodeThis(next.line, next.columnStart);
		break;
	    case SELF:
		expr = new NodeSelf(next.line, next.columnStart);
		break;
	    case UNARYOP:
		expr = new NodeUnary(next.line, next.columnStart, parsePrimaryExpression(), next.data, true);
		break;
	    case BRACKETL:
		expr = new NodeTupleExpr(next.line, next.columnStart);
		rewind();
		((NodeTupleExpr)expr).exprs = parseCallArgs(TokenType.BRACKETL, TokenType.BRACKETR);
		break;
	    case PARENL:
		expr = parseExpression();
		expect(TokenType.PARENR);
		break;
	    case OCTINT:
		expr = new NodeInteger(Integer.parseInt(next.data, 8));
		break;
	    case HEXINT:
		expr = new NodeInteger(Integer.parseInt(next.data, 16));
		break;
	    case BININT:
		expr = new NodeInteger(Integer.parseInt(next.data, 2));
		break;
	    case INT:
		expr = new NodeInteger(Integer.parseInt(next.data, 10));
		break;
	    case LONG:
		expr = new NodeLong(Long.parseLong(next.data.substring(0, next.data.length() - 1)));
		break;
	    case FLOAT:
		expr = new NodeFloat(Float.parseFloat(next.data));
		break;
	    case DOUBLE:
		expr = new NodeDouble(Double.parseDouble(next.data));
		break;
	    case STRING:
		expr = new NodeString(next.data.substring(1, next.data.length() - 1));
		break;
	    case CHAR:
		expr = new NodeChar(next.data.charAt(1));
		break;
	    case BOOL:
		expr = new NodeBool(next.data.equals("true"));
		break;
	}
	if (expr != null) {
	    next = getNext();
	    switch (next.type) {
		case BINARYOP:
		    return new NodeBinary(expr, next.data, parsePrimaryExpression());
		default:
		    rewind();
	    }
	}
	return expr;
    }

    private IExpression parseExpression() throws UnexpectedTokenException {
	final IExpression expr = parsePrimaryExpression();
	final Token next = getNext();

	switch (next.type) {
	    // Postfix unary expression
	    case UNARYOP:
		return new NodeUnary(next.line, next.columnStart, expr, next.data, false);
	    case QUESTIONMARK:
		final IExpression exprTrue = parseExpression();
		expect(TokenType.COLON);
		return new NodeTernary(expr, exprTrue, parseExpression());
	}
	rewind();
	return expr;
    }

    private NodeVarDec parseVarDec(final LinkedList<NodeModifier> mods) throws UnexpectedTokenException {
	final Token keyword = expect(TokenType.CONST, TokenType.VAR);
	final Token id = expect(TokenType.ID);
	Token next = expect(TokenType.COLON, TokenType.ASSIGNOP);
	NodeVarDec varDec;
	TokenType type = next.type;
	if (type == TokenType.COLON) {
	    final NodeType nodeType = parseType();
	    varDec = new NodeVarDecExplicit(id.line, id.columnStart, mods, keyword.data, id.data, nodeType, null);
	    if (getNext().type == TokenType.ASSIGNOP) varDec = new NodeVarDecExplicit(id.line, id.columnStart, mods, keyword.data, id.data, nodeType, parseExpression());
	    else rewind();
	} else varDec = new NodeVarDecImplicit(id.line, id.columnStart, mods, keyword.data, id.data, parseExpression());
	
	if(getNext().type == TokenType.LAMBDAARROW){
	    expect(TokenType.BRACEL);
	    NodeFuncBlock getBlock = null, setBlock = null;
	    while(getNext().type != TokenType.BRACER){
		rewind();
		if(setBlock == null && getBlock == null){
		    next = expect(TokenType.GET, TokenType.SET);
		    if(next.type == TokenType.SET) setBlock = parseFuncBlock(false);
		    else getBlock = parseFuncBlock();
		}
		next = getNext();
		if(next.type == TokenType.GET){
		    if(getBlock != null){
			if(setBlock == null) throw new UnexpectedTokenException(next, TokenType.SET, TokenType.BRACER);
			else throw new UnexpectedTokenException(next, TokenType.BRACER);
		    }else getBlock = parseFuncBlock();
		}else if(next.type == TokenType.SET){
		    if(setBlock != null){
			if(getBlock == null) throw new UnexpectedTokenException(next, TokenType.GET, TokenType.BRACER);
			else throw new UnexpectedTokenException(next, TokenType.BRACER);
		    }else setBlock = parseFuncBlock();
		}else rewind();
	    }
	    varDec.getBlock = getBlock;
	    varDec.setBlock = setBlock;
	}else rewind();
	return varDec;
    }

    private NodeTypes parseTypes() throws UnexpectedTokenException {
	final NodeTypes types = new NodeTypes();
	types.add(parseType());
	while (getNext().type == TokenType.COMMA)
	    types.add(parseType());
	rewind();
	return types;
    }

    private NodeTypes parseSuperTypes() throws UnexpectedTokenException {
	final NodeTypes types = new NodeTypes();
	types.add(parseSuperType());
	while (getNext().type == TokenType.COMMA)
	    types.add(parseSuperType());
	rewind();
	return types;
    }

    private NodeArgs parseArgs() throws UnexpectedTokenException {
	expect(TokenType.PARENL);
	final NodeArgs args = new NodeArgs();
	NodeArg arg;
	if ((arg = parseArg()) != null) {
	    args.add(arg);
	    while (getNext().type == TokenType.COMMA)
		args.add(parseArg());
	    rewind();
	}
	expect(TokenType.PARENR);
	return args;
    }

    private NodeArg parseArg() throws UnexpectedTokenException {
	final Token id = getNext();
	if (id.type == TokenType.ID) {
	    expect(TokenType.COLON);
	    final NodeType type = parseType();
	    IExpression defExpr = null;
	    if(expect(TokenType.COMMA, TokenType.ASSIGNOP, TokenType.PARENR).type == TokenType.ASSIGNOP) defExpr = parseExpression();
	    else rewind();
	    return new NodeArg(id.line, id.columnStart, id.data, type, defExpr);
	} else rewind();
	return null;
    }

    private NodeType parseType() throws UnexpectedTokenException {
	final NodeType type = new NodeType();
	// Parse tuple types
	if (getNext().type == TokenType.BRACKETL) {
	    boolean named = false, unnamed = false;
	    do{
		NodeTupleType tupleType = parseTupleType(named, unnamed);
		if(tupleType.name != null) named = true;
		else{
		    tupleType.name = ((char)('a'+type.tupleTypes.size()))+"";
		    unnamed = true;
		}
		type.tupleTypes.add(tupleType);
	    }while (expect(TokenType.COMMA, TokenType.BRACKETR).type == TokenType.COMMA);
	} else {
	    rewind();
	    type.id = expect(TokenType.ID, TokenType.PRIMITIVE).data;
	}

	// Parse array dimensions
	while (getNext().type == TokenType.BRACKETL) {
	    expect(TokenType.BRACKETR);
	    type.arrDims++;
	}
	rewind();
	if (getNext().type == TokenType.QUESTIONMARK) type.optional = true;
	else rewind();
	return type;
    }

    public NodeTupleType parseTupleType(boolean mustBeNamed, boolean mustBeUnnamed) throws UnexpectedTokenException {
	String id = expect(TokenType.ID, TokenType.PRIMITIVE).data;
	Token next = getNext();
	if(next.type == TokenType.COLON){
	    if(mustBeUnnamed) throw new UnexpectedTokenException(next, TokenType.COMMA, TokenType.BRACKETR);
	    NodeTupleType type = new NodeTupleType(line, column, parseType());
	    type.name = id;
	    return type;
	}else if(mustBeNamed) throw new UnexpectedTokenException(next, TokenType.COLON);
	rewind(2);
	return new NodeTupleType(line, column, parseType());
    }

    private NodeType parseSuperType() throws UnexpectedTokenException {
	final Token id = expect(TokenType.ID);
	final NodeType type = new NodeType(id.data);
	return type;
    }

    private NodeEnumDec parseEnumDec(final LinkedList<NodeModifier> mods) throws UnexpectedTokenException {
	final Token id = expect(TokenType.ID);
	NodeArgs args = null;

	if (getNext().type == TokenType.PARENL) {
	    rewind();
	    args = parseArgs();
	} else rewind();

	final NodeEnumBlock block = parseEnumBlock();
	return new NodeEnumDec(id.line, id.columnStart, mods, id, args, block);
    }

    private NodeEnumBlock parseEnumBlock() throws UnexpectedTokenException {
	final LinkedList<NodeEnumInstance> instances = new LinkedList<Node.NodeEnumInstance>();
	instances.add(parseEnumInstance());
	while (getNext().type == TokenType.COMMA)
	    instances.add(parseEnumInstance());
	rewind();
	return new NodeEnumBlock(line, column, instances, parseClassBlock());
    }

    private NodeInterfaceDec parseInterfaceDec(final LinkedList<NodeModifier> mods) throws UnexpectedTokenException {
	final Token id = expect(TokenType.ID);
	NodeArgs args = null;
	NodeTypes types = null;

	if (getNext().type == TokenType.PARENL) {
	    rewind();
	    args = parseArgs();
	} else rewind();

	if (getNext().type == TokenType.COLON) types = parseSuperTypes();
	else rewind();

	final NodeClassBlock block = parseClassBlock();
	return new NodeInterfaceDec(id.line, id.columnStart, mods, types, args, id, block);
    }

    private NodeTypeDec parseTypeDec() throws UnexpectedTokenException {
	final LinkedList<NodeModifier> mods = parseMods();
	final Token token = expect(TokenType.CLASS, TokenType.ENUM, TokenType.INTERFACE);
	switch (token.type) {
	    case CLASS:
		final NodeClassDec clsDec = parseClassDec(mods);
		return clsDec;
	    case ENUM:
		final NodeEnumDec enumDec = parseEnumDec(mods);
		return enumDec;
	    case INTERFACE:
		final NodeInterfaceDec interfaceDec = parseInterfaceDec(mods);
		return interfaceDec;
	}
	return null;
    }

    private LinkedList<NodeTypeDec> parseTypeDecs() throws UnexpectedTokenException {
	final LinkedList<NodeTypeDec> typeDecs = new LinkedList<Node.NodeTypeDec>();
	NodeTypeDec typeDec = parseTypeDec();
	typeDecs.add(typeDec);
	while (true) {
	    // Having more than 1 type declaration is optional, so look out for
	    // EOF
	    if (getNext().type == TokenType.EOF) break;
	    rewind();
	    typeDec = parseTypeDec();
	    typeDecs.add(typeDec);
	}
	return typeDecs;
    }

    private NodeFile parseFile() throws UnexpectedTokenException {
	final NodePackage pkg = parsePackage();
	final LinkedList<NodeImport> imports = parseImports();
	final LinkedList<NodeTypeDec> typeDecs = parseTypeDecs();
	return new NodeFile(pkg, imports, typeDecs);
    }

    private NodeEnumInstance parseEnumInstance() throws UnexpectedTokenException {
	final Token id = expect(TokenType.ID);
	if (getNext().type == TokenType.PARENL) {
	    rewind();
	    return new NodeEnumInstance(id.line, id.columnStart, id, parseCallArgs(TokenType.PARENL, TokenType.PARENR));
	}
	rewind();
	return new NodeEnumInstance(id.line, id.columnStart, id, null);
    }

}
