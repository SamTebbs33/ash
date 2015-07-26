package ashc.grammar;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Ash
 *
 * @author samtebbs, 20:12:36 - 20 May 2015
 */
public class Lexer {

    private final Matcher matcher;
    public LinkedList<String> lines = new LinkedList<String>();
    public int line = 1, column = 1, numLines = 0;
    public static final int TAB_SIZE = 4;

    public static interface TokenMatcher {
	public String getName();

	public boolean matches(Token token);
    }

    public static enum TokenTypeGroup implements TokenMatcher {
	EXPRESSION_STARTER(
		"expression",
		TokenType.NEW,
		TokenType.NULL,
		TokenType.BRACEL,
		TokenType.ID,
		TokenType.THIS,
		TokenType.SELF,
		TokenType.UNARYOP,
		TokenType.BRACKETL,
		TokenType.PARENL,
		TokenType.OCTINT,
		TokenType.HEXINT,
		TokenType.BININT,
		TokenType.INT,
		TokenType.LONG,
		TokenType.FLOAT,
		TokenType.DOUBLE,
		TokenType.STRING,
		TokenType.CHAR,
		TokenType.BOOL), FUNC_CALL("function call", TokenType.THIS, TokenType.ID, TokenType.SELF, TokenType.SUPER), VAR_DEC(
		"variable/constant declaration",
		TokenType.VAR,
		TokenType.CONST), CONTROL_STMT("control statement", TokenType.IF, TokenType.WHILE, TokenType.FOR, TokenType.MATCH);

	public TokenType[] tokenTypes;
	public String name;

	private TokenTypeGroup(final String name, final TokenType... tokenTypes) {
	    this.name = name;
	    this.tokenTypes = tokenTypes;
	}

	@Override
	public boolean matches(final Token token) {
	    for (final TokenType type : tokenTypes)
		if (type == token.type) return true;
	    return false;
	}

	@Override
	public String getName() {
	    return name;
	}

    }

    public static enum TokenType implements TokenMatcher {
	// The ERROR token type must be the last one, as it matches anything
	// that isn't matched by other tokens
	COMMENT("(//.+)|(/\\*(.|[\r\n])*?\\*/)", "comment"), // Source:
	// http://blog.ostermiller.org/find-comment
	OCTINT("0o[0-7]+", "octal integer"),
	BININT("0b[0|1]+", "binary integer"),
	HEXINT("0x[0-9a-fA-F]+", "hexadecimal integer"),
	FLOAT("-?[0-9]+\\.[0-9]+f", "float"),
	DOUBLE("-?[0-9]+\\.[0-9]+", "double"),
	LONG("-?[0-9]+L", "long"),
	INT("-?[0-9]+", "integer"),
	STRING("\"[^\"]*\"", "string"),
	CHAR("\'.\'", "character"),
	BOOL("true|false", "boolean"),
	PRIMITIVE("bool|double|float|long|int|short|byte|ubyte|ushort|ulong|uint|char|void", "primitive"),
	LAMBDAARROW("->", "lambda arrow"),

	COMPOUNDASSIGNOP("-=|\\+=|\\*=|/=|%=|\\*\\*=|^=|&=|\\|=|<<=|>>>=|>>=", "compound assignment operator"),
	UNARYOP("\\+\\+|\\-\\-|!|~", "unary operator"),
	BINARYOP("<|>|<=|>=|==|!=|/|\\+|\\-|\\*\\*|\\*|\\^\\^|&&|\\|\\||<<|>>|&|\\|", "binary operator"),
	ASSIGNOP("=", "assignment operator"),
	CUSTOMOP("[\\+|\\-|!|~|=|\\*|/|%|^|&|<|>|?|@|#]+", "custom operator"),
	ARRAYDIMENSION("\\[\\]"),
	WHITESPACE("[\n\t ]+", "whitespace"),

	UNDERSCORE("_", "underscore"),
	ARROW("=>", "throws arrow"),
	PARENL("\\(", "left parenthesis"),
	PARENR("\\)", "right parenthesis"),
	BRACEL("\\{", "left brace"),
	BRACER("\\}", "right brace"),
	BRACKETL("\\[", "left bracket"),
	BRACKETR("\\]", "right bracket"),
	ELLIPSIS("\\.\\.\\."),
	DOUBLEDOT("\\.\\.", "double dot"),
	DOT("\\.", "dot"),
	COMMA(",", "comma"),
	QUESTIONMARK("\\?", "question mark"),
	COLON(":", "colon"),

	VAR("var "),
	CONST("const "),
	FUNC("func "),
	INIT("init"),
	CONSTRUCT("construct"),
	MUT("mut "),
	ALIAS("alias "),
	CLASS("class "),
	OPERATOR("operator "),
	ENUM("enum "),
	INTERFACE("interface "),
	INCLUDE("include "),
	IMPORT("import "),
	PACKAGE("package "),
	RETURN("return "),

	PUBLIC("public"),
	PRIVATE("private"),
	PROTECTED("protected"),
	FINAL("final"),
	REQUIRED("required"),
	NATIVE("native"),
	OVERRIDE("override"),
	STANDARD("standard"),
	STATIC("static"),
	THIS("this"),
	SUPER("super"),
	SELF("self "),
	NULL("null"),
	AS("as "),
	IS("is "),
	GET("get "),
	SET("set "),
	NEW("new "),
	OPTYPE("binary|unary", "operator type"),

	IF("if"),
	ELSE("else"),
	WHILE("while"),
	FOR("for"),
	MATCH("match "),
	IN("in "),

	ID("[a-zA-Z](\\d|[a-zA-Z])*", "identifier"),
	EOF("\\Z", "end of file"),
	ERROR(".*", "error");

	public String regex, typeName;

	TokenType(final String regex) {
	    this(regex, regex);
	}

	TokenType(final String str, final String typeName) {
	    regex = str;
	    this.typeName = typeName.trim();
	}

	@Override
	public boolean matches(final Token token) {
	    return token.type == this;
	}

	@Override
	public String getName() {
	    return typeName;
	}
    }

    public static class Token {
	public TokenType type;
	public String data;
	public int line, columnStart, columnEnd;

	public Token(final TokenType type, final String data, final int line, final int column, final int columnEnd) {
	    this.type = type;
	    this.data = data;
	    this.line = line;
	    columnStart = column;
	    this.columnEnd = columnEnd;
	}

	@Override
	public String toString() {
	    return String.format("%d:%d-%d = %s (%s)", line, columnStart, columnEnd, type.name(), data);
	}

    }

    public static class InvalidTokenException extends Exception {

	public String data;
	public int line, column;

	public InvalidTokenException(final String data, final int line, final int column) {
	    this.data = data;
	    this.line = line;
	    this.column = column;
	}

    }

    public static class UnexpectedTokenException extends Exception {

	public Token token;
	public String msg;

	public UnexpectedTokenException(final Token found, final TokenMatcher... t) {
	    token = found;
	    final StringBuilder typesStr = new StringBuilder("");
	    for (int i = 0; i < t.length; i++) {
		typesStr.append(t[i].getName());
		if (i == (t.length - 2)) typesStr.append(" or ");
		else if (i < (t.length - 1)) typesStr.append(", ");
	    }
	    msg = String.format("Expected %s, found %s", typesStr, found.type.typeName);
	}

	public UnexpectedTokenException(final Token t) {
	    token = t;
	    msg = String.format("Unexpected %s", t.type.typeName);
	}

	public UnexpectedTokenException(final Token next, final String tokenData) {
	    token = next;
	    msg = String.format("Unexpected %s, expected %s", next.type.typeName, tokenData);
	}

    }

    public Lexer(final BufferedReader reader) throws IOException {
	final StringBuffer lineBuffer = new StringBuffer();
	String line;
	while ((line = reader.readLine()) != null) {
	    lines.add(line.replace("\t", "    "));
	    lineBuffer.append(line + "\n");
	    numLines++;
	}
	final StringBuffer patternsBuffer = new StringBuffer();
	for (final TokenType type : TokenType.values())
	    patternsBuffer.append(String.format("|(?<%s>%s)", type.name(), type.regex));
	final String patternStr = patternsBuffer.toString();
	final Pattern patterns = Pattern.compile(patternStr.substring(1));

	matcher = patterns.matcher(lineBuffer.toString());
	reader.close();
    }

    public Token getNextToken() throws InvalidTokenException {
	while (matcher.find())
	    for (final TokenType type : TokenType.values()) {
		final String data = matcher.group(type.name());
		if (data != null) {
		    // Skip whitespace
		    if (type == TokenType.WHITESPACE) {
			for (final char ch : data.toCharArray())
			    switch (ch) {
				case '\n':
				    line++;
				    column = 1;
				    break;
				case '\t':
				    column += TAB_SIZE;
				    break;
				case ' ':
				    column++;
			    }
			continue;
		    } else if (type == TokenType.ERROR) throw new InvalidTokenException(data, line, column);
		    else if (type == TokenType.COMMENT) {
			column += data.length();
			continue;
		    }
		    final Token t = new Token(type, data, line, column, column + data.length());
		    column += data.length();
		    return t;
		}
	    }
	return null;
    }

}
