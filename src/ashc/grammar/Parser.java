package ashc.grammar;

import java.util.LinkedList;

import ashc.grammar.Lexer.*;
import ashc.grammar.Node.*;

/**
 * Ash
 *
 * @author samtebbs, 22:23:42 - 20 May 2015
 */
public class Parser {

    private final LinkedList<Token> tokens = new LinkedList<Token>();
    private Lexer lexer;
    private int pointer = 0, line = 1, column = 1;

    public Parser(final Lexer lexer) {
	this.lexer = lexer;
	while (true)
	    try {
		final Token token = lexer.getNextToken();
		if (token == null) break;
		tokens.add(token);
	    } catch (final InvalidTokenException e) {
		System.err.printf("Error:%d:%d Invalid token (%s)%n", e.line, e.column, e.data);
	    }
    }

    public NodeFile start() {
	try {
	    return parseFile();
	} catch (final UnexpectedTokenException e) {
	    int line = e.token.line, colStart = e.token.columnStart, colEnd = e.token.columnEnd;
	    System.err.printf("Error:%d:%d-%d %s%n", line, colStart, colEnd, e.msg);
	    System.out.println(lexer.lines.get(line-1));
	    for(int i = 0; i < colStart - 1; i++) System.out.print(" ");
	    System.out.print("^");
	    if(colEnd - colStart > 1){
		for(int i = colStart; i < colEnd-2; i++) System.out.print("-");
		System.out.println("^");
	    }else System.out.println();
	}
	return null;
    }

    private Token getNext() throws UnexpectedTokenException {
	if (pointer < tokens.size()) {
	    final Token t = tokens.get(pointer++);
	    line = t.line;
	    column = t.columnStart;
	    return t;
	}
	return null;
    }

    private void rewind() {
	pointer--;
    }

    private void rewind(final int a) {
	pointer -= a;
    }

    private Token expect(final TokenType t) throws UnexpectedTokenException {
	final Token token = getNext();
	if (token.type != t) throw new UnexpectedTokenException(token, t);
	return token;
    }

    private Token expect(final TokenType... t) throws UnexpectedTokenException {
	final Token token = getNext();
	boolean found = false;
	for (final TokenType type : t)
	    if (token.type == type) {
		found = true;
		break;
	    }
	if (!found) throw new UnexpectedTokenException(token, t);
	return token;
    }

    private LinkedList<NodeImport> parseImports() throws UnexpectedTokenException {
	final LinkedList<NodeImport> imports = new LinkedList<Node.NodeImport>();
	while (getNext().type == TokenType.IMPORT) {
	    final NodeQualifiedName name = parseQualifiedName();
	    imports.add(new NodeImport(line, column, name));
	}
	rewind();
	return null;
    }

    /**
     * id (.id)*
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
	return null;
    }

    /**
     * package qualifiedName
     */
    private NodePackage parsePackage() throws UnexpectedTokenException {
	if (getNext().type == TokenType.PACKAGE) {
	    final NodeQualifiedName name = parseQualifiedName();
	    if (name != null) return new NodePackage(name.line, name.column, name);
	}
	rewind();
	return null;
    }

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

	if (getNext().type == TokenType.COLON) types = parseTypes();
	else rewind();

	final NodeClassBlock block = parseClassBlock();
	return new NodeClassDec(id.line, id.columnStart, mods, id, args, types, block);
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
	expect(TokenType.COLON, TokenType.ARROW, TokenType.ASSIGNOP, TokenType.BRACEL);
	rewind();

	if (getNext().type == TokenType.COLON) type = parseType();
	else rewind();

	if (getNext().type == TokenType.ARROW) throwsType = parseType();
	else rewind();

	if (needsBody) block = parseFuncBlock();

	return new NodeFuncDec(id.line, id.columnStart, mods, id.data, args, type, throwsType, block);
    }

    private NodeFuncBlock parseFuncBlock() throws UnexpectedTokenException {
	final NodeFuncBlock block = new NodeFuncBlock();
	final Token token = expect(TokenType.ASSIGNOP, TokenType.BRACEL);
	if (token.type == TokenType.BRACEL) while (getNext().type != TokenType.BRACER) {
	    rewind();
	    block.add(parseFuncStmt());
	}
	else {
	    rewind();
	    block.add(parseFuncStmt());
	}
	return block;
    }

    private IFuncStmt parseFuncStmt() throws UnexpectedTokenException {
	final Token token = expect(TokenType.ID, TokenType.VAR, TokenType.CONST);
	rewind();
	switch (token.type) {
	    case ID:
		return parsePrefix();
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
	    }

	} while (getNext().type == TokenType.DOT);
	rewind();
	return prefix;
    }

    private NodeExprs parseCallArgs() throws UnexpectedTokenException {
	final NodeExprs exprs = new NodeExprs();
	expect(TokenType.PARENL);
	Token next;
	do {
	    IExpression expr = parseExpression();
	    System.out.println(expr);
	    exprs.add(expr);
	    next = getNext();
	} while (next.type == TokenType.COMMA);
	rewind();
	expect(TokenType.PARENR);
	return exprs;
    }

    private IExpression parsePrimaryExpression() throws UnexpectedTokenException{
	Token next = expect(TokenType.UNARYOP, TokenType.PARENL, TokenType.OCTINT, TokenType.HEXINT, TokenType.BININT, TokenType.INT, TokenType.FLOAT, TokenType.DOUBLE, TokenType.STRING, TokenType.CHAR, TokenType.BOOL);
	IExpression expr = null;
	
	switch(next.type){
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
		expr =  new NodeInteger(Integer.parseInt(next.data, 16));
		break;
	    case BININT:
		expr =  new NodeInteger(Integer.parseInt(next.data, 2));
		break;
	    case INT:
		expr = new NodeInteger(Integer.parseInt(next.data, 10));
		break;
	    case FLOAT:
		expr =  new NodeFloat(Float.parseFloat(next.data));
		break;
	    case DOUBLE:
		expr =  new NodeDouble(Double.parseDouble(next.data));
		break;
	    case STRING:
		expr =  new NodeString(next.data.substring(1, next.data.length()-1));
		break;
	    case CHAR:
		expr =  new NodeChar(next.data.charAt(1));
		break;
	    case BOOL:
		expr =  new NodeBool(next.data.equals("true"));
		break;
	}
	if(expr != null){
	    next = getNext();
	    switch(next.type){
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
	Token next = getNext();
	
	switch(next.type){
	    // Postfix unary expression
	    case UNARYOP:
		    return new NodeUnary(next.line, next.columnStart, expr, next.data, false);
	    case QUESTIONMARK:
		IExpression exprTrue = parseExpression();
		expect(TokenType.COLON);
		return new NodeTernary(expr, exprTrue, parseExpression());
	}
	rewind();
	return expr;
    }

    private NodeVarDec parseVarDec(final LinkedList<NodeModifier> mods) throws UnexpectedTokenException {
	expect(TokenType.CONST, TokenType.VAR);
	expect(TokenType.ID);
	return null;
    }

    private NodeTypes parseTypes() throws UnexpectedTokenException {
	final NodeTypes types = new NodeTypes();
	types.add(parseType());
	while (getNext().type == TokenType.COMMA)
	    types.add(parseType());
	rewind();
	return types;
    }

    private NodeArgs parseArgs() throws UnexpectedTokenException {
	expect(TokenType.PARENL);
	final NodeArgs args = new NodeArgs();
	NodeArg arg;
	while ((arg = parseArg()) != null)
	    args.add(arg);
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
	final Token id = expect(TokenType.ID);
	final NodeType type = new NodeType(id.data);
	while (getNext().type == TokenType.BRACKETL) {
	    expect(TokenType.BRACKETR);
	    type.arrDims++;
	}
	rewind();
	return type;
    }

    private NodeEnumDec parseEnumDec(final LinkedList<NodeModifier> mods) {
	return null;
    }

    private NodeInterfaceDec parseInterfaceDec(final LinkedList<NodeModifier> mods) {
	return null;
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
	parsePackage();
	parseImports();
	parseTypeDecs();
	return null;
    }

}
