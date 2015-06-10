package ashc.grammar;

import java.util.*;

import ashc.grammar.Lexer.InvalidTokenException;
import ashc.grammar.Lexer.Token;
import ashc.grammar.Lexer.TokenType;
import ashc.grammar.Lexer.UnexpectedTokenException;
import ashc.grammar.Node.IExpression;
import ashc.grammar.Node.IFuncStmt;
import ashc.grammar.Node.NodeArg;
import ashc.grammar.Node.NodeArgs;
import ashc.grammar.Node.NodeBinary;
import ashc.grammar.Node.NodeBool;
import ashc.grammar.Node.NodeChar;
import ashc.grammar.Node.NodeClassBlock;
import ashc.grammar.Node.NodeClassDec;
import ashc.grammar.Node.NodeDouble;
import ashc.grammar.Node.NodeEnumBlock;
import ashc.grammar.Node.NodeEnumDec;
import ashc.grammar.Node.NodeEnumInstance;
import ashc.grammar.Node.NodeExprs;
import ashc.grammar.Node.NodeFile;
import ashc.grammar.Node.NodeFloat;
import ashc.grammar.Node.NodeFuncBlock;
import ashc.grammar.Node.NodeFuncCall;
import ashc.grammar.Node.NodeFuncDec;
import ashc.grammar.Node.NodeImport;
import ashc.grammar.Node.NodeInteger;
import ashc.grammar.Node.NodeInterfaceDec;
import ashc.grammar.Node.NodeLong;
import ashc.grammar.Node.NodeModifier;
import ashc.grammar.Node.NodeNull;
import ashc.grammar.Node.NodePackage;
import ashc.grammar.Node.NodePrefix;
import ashc.grammar.Node.NodeQualifiedName;
import ashc.grammar.Node.NodeReturn;
import ashc.grammar.Node.NodeString;
import ashc.grammar.Node.NodeTernary;
import ashc.grammar.Node.NodeThis;
import ashc.grammar.Node.NodeType;
import ashc.grammar.Node.NodeTypeDec;
import ashc.grammar.Node.NodeTypes;
import ashc.grammar.Node.NodeUnary;
import ashc.grammar.Node.NodeVarAssign;
import ashc.grammar.Node.NodeVarDec;
import ashc.grammar.Node.NodeVarDecExplicit;
import ashc.grammar.Node.NodeVarDecImplicit;
import ashc.grammar.Node.NodeVariable;

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
	expect(TokenType.BRACEL);
	final NodeClassBlock block = new NodeClassBlock();
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
	final NodeFuncBlock block = new NodeFuncBlock();
	final Token token = expect(TokenType.LAMBDAARROW, TokenType.BRACEL);
	if (token.type == TokenType.BRACEL) while (getNext().type != TokenType.BRACER) {
	    rewind();
	    block.add(parseFuncStmt());
	}
	else block.singleLineExpr = parseExpression();
	return block;
    }

    private IFuncStmt parseFuncStmt() throws UnexpectedTokenException {
	final Token token = expect(TokenType.ID, TokenType.VAR, TokenType.CONST, TokenType.RETURN);
	rewind();
	switch (token.type) {
	    case RETURN:
		silenceErrors = true;
		final IExpression expr = parseExpression();
		silenceErrors = false;
		return new NodeReturn(token.line, token.columnStart, expr);
	    case ID:
		final IFuncStmt stmt = parsePrefix();
		if (stmt instanceof NodeVariable) {
		    final Token assignOp = expect(TokenType.ASSIGNOP, TokenType.COMPOUNDASSIGNOP);
		    return new NodeVarAssign(assignOp.line, assignOp.columnStart, (NodeVariable) stmt, assignOp.data, parseExpression());
		} else return stmt;
	    case VAR:
	    case CONST:
		return parseVarDec(null);
	}
	return null;
    }

    // A prefix is just another name for a func call or variable
    private NodePrefix parsePrefix() throws UnexpectedTokenException {
	NodePrefix prefix = null;
	do {
	    final Token id = expect(TokenType.ID);
	    if (getNext().type == TokenType.PARENL) {
		rewind();
		final NodeExprs exprs = parseCallArgs();
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

    private NodeExprs parseCallArgs() throws UnexpectedTokenException {
	final NodeExprs exprs = new NodeExprs();
	expect(TokenType.PARENL);
	Token next = getNext();
	if (next.type == TokenType.PARENR) return exprs;
	else rewind();

	do {
	    final IExpression expr = parseExpression();
	    // System.out.println(expr);
	    exprs.add(expr);
	    next = getNext();
	} while (next.type == TokenType.COMMA);
	rewind();
	expect(TokenType.PARENR);
	return exprs;
    }

    private IExpression parsePrimaryExpression() throws UnexpectedTokenException {
	Token next = expect(TokenType.NULL, TokenType.ID, TokenType.THIS, TokenType.UNARYOP, TokenType.PARENL, TokenType.OCTINT, TokenType.HEXINT, TokenType.BININT, TokenType.INT, TokenType.LONG, TokenType.FLOAT, TokenType.DOUBLE, TokenType.STRING, TokenType.CHAR, TokenType.BOOL);
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
	    case UNARYOP:
		expr = new NodeUnary(next.line, next.columnStart, parsePrimaryExpression(), next.data, true);
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
	    next = getNext();
	    type = next.type;
	    if (type == TokenType.ASSIGNOP) varDec = new NodeVarDecExplicit(id.line, id.columnStart, mods, keyword.data, id.data, nodeType, parseExpression());
	    else rewind();
	} else varDec = new NodeVarDecImplicit(id.line, id.columnStart, mods, keyword.data, id.data, parseExpression());
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
	    return new NodeArg(id.line, id.columnStart, id.data, type);
	} else rewind();
	return null;
    }

    private NodeType parseType() throws UnexpectedTokenException {
	final NodeType type = new NodeType();
	// Parse tuple types
	if (getNext().type == TokenType.PARENL) {
	    do
		type.tupleTypes.add(parseType());
	    while (expect(TokenType.COMMA, TokenType.PARENR).type == TokenType.COMMA);
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
	    return new NodeEnumInstance(id.line, id.columnStart, id, parseCallArgs());
	}
	rewind();
	return new NodeEnumInstance(id.line, id.columnStart, id, null);
    }

}
