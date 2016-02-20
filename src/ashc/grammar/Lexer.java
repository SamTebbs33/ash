package ashc.grammar;

import ashc.error.AshError;
import ashc.grammar.Parser.GrammarException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ash
 *
 * @author samtebbs, 20:12:36 - 20 May 2015
 */
public class Lexer {

    public static final int TAB_SIZE = 4;
    private static StringBuffer keywordRegex = new StringBuffer("");
    private final Matcher matcher;
    public LinkedList<String> lines = new LinkedList<String>();
    public int line = 1, column = 1, numLines = 0;

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

    public enum TokenTypeGroup implements TokenMatcher {
        EXPRESSION_STARTER("expression", TokenType.MATCH, TokenType.NEW, TokenType.NULL, TokenType.BRACEL, TokenType.ID, TokenType.THIS, TokenType.SELF,
                TokenType.FUNC,
                TokenType.INTERFACE,
                TokenType.OP,
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
                TokenType.BOOL),
        FUNC_CALL("function call", TokenType.THIS, TokenType.ID, TokenType.SELF, TokenType.SUPER),
        VAR_DEC(
                "variable/constant declaration",
                TokenType.VAR,
                TokenType.CONST),
        CONTROL_STMT("control statement", TokenType.IF, TokenType.WHILE, TokenType.FOR, TokenType.MATCH);

        public TokenType[] tokenTypes;
        public String name;

        TokenTypeGroup(final String name, final TokenType... tokenTypes) {
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
        public String toString() {
            return name;
        }

    }

    public enum TokenType implements TokenMatcher {
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
        PRIMITIVE("(bool|double|float|long|int|short|byte|ubyte|ushort|ulong|uint|char|void)", "primitive", true),
        LAMBDAARROW("->", "->"),

        COMPOUNDASSIGNOP("-=|\\+=|\\*=|/=|%=|\\*\\*=|^=|&=|\\|=|<<=|>>>=|>>=", "compound assignment operator"),
        OP("[\\+|\\-|!|~|=|\\*|/|%|^|&|<|>|@|#|\\?]+", "operator"),
        ARRAYDIMENSION("\\[\\]", "array dimension"),
        WHITESPACE("[\n\t ]+", "whitespace"),

        UNDERSCORE("_"),
        ARROW("=>"),
        PARENL("\\(", "("),
        PARENR("\\)", ")"),
        BRACEL("\\{", "{"),
        BRACER("\\}", "}"),
        BRACKETL("\\[", "["),
        BRACKETR("\\]", "]"),
        ELLIPSIS("\\.\\.\\."),
        DOUBLEDOT("\\.\\.", "double dot"),
        DOT("\\.", "dot"),
        COMMA(",", "comma"),
        COLON(":", "colon"),

        VAR("var", true),
        CONST("const", true),
        FUNC("func", true),
        CONSTRUCT("construct", true),
        MUT("mut", true),
        ALIAS("alias", true),
        CLASS("class", true),
        OPERATOR("operator", true),
        ENUM("enum", true),
        INTERFACE("interface", true),
        INCLUDE("include", true),
        IMPORT("import", true),
        PACKAGE("package", true),
        RETURN("return", true),
        BREAK("break", true),
        CONTINUE("continue", true),

        PUBLIC("public", true),
        PRIVATE("private", true),
        PROTECTED("protected", true),
        FINAL("final", true),
        REQUIRED("required", true),
        NATIVE("native", true),
        OVERRIDE("override", true),
        STANDARD("standard", true),
        STATIC("static", true),
        SYNCHRONISED("synchronised", true),
        THIS("this", true),
        SUPER("super", true),
        SELF("self", true),
        NULL("null", true),
        AS("as", true),
        IS("is", true),
        NEW("new", true),
        OPTYPE("binary|prefix|postfix", "operator type"),

        IF("if", true),
        ELSE("else", true),
        WHILE("while", true),
        FOR("for", true),
        MATCH("match ", true),
        IN("in", true),

        ID(Lexer.keywordRegex + "([a-zA-Z_](\\d|[a-zA-Z_])*)", "identifier"),
        EOF("\\Z", "end of file"),
        ERROR(".*", "errored token");

        public String regex, typeName;

        TokenType(final String regex) {
            this(regex, regex);
        }

        TokenType(final String str, final String typeName) {
            regex = str;
            this.typeName = typeName.trim();
        }

        TokenType(String str, String name, boolean isKeyword) {
            this(str, name);
            if (isKeyword) {
                Lexer.keywordRegex.append("(\\\\" + regex + ")|");
                regex = "\\b" + regex + "\\b";
            }
        }

        TokenType(String str, boolean isKeyword) {
            this(str, str, isKeyword);
        }

        @Override
        public boolean matches(final Token token) {
            return token.type == this;
        }

        @Override
        public String toString() {
            return typeName;
        }
    }

    public interface TokenMatcher {
        boolean matches(Token token);
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

    public static class UnexpectedTokenException extends GrammarException {

        public Token token;

        public UnexpectedTokenException(final Token found, final TokenMatcher... t) {
            super(String.format("Unexpected %s. Expected %s", found.type.typeName, asString(t)), found.line, found.columnStart);
            token = found;
        }

        public UnexpectedTokenException(final Token t) {
            super(String.format("Unexpected %s", t.type.typeName), t.line, t.columnStart);
            token = t;
        }

        public UnexpectedTokenException(final Token next, final String... tokenData) {
            super(String.format("Unexpected %s. Expected %s", next.type.typeName, asString(tokenData)), next.line, next.columnStart);
            token = next;
        }

        public UnexpectedTokenException(final Token next, final TokenMatcher[] matchers, final String...data) {
            super(String.format("Unexpected %s. Expected %s" + (matchers.length > 1 ? ", " : " or ") + "%s", next.type.typeName, asString(data), asString(matchers)), next.line, next.columnStart);
            token = next;
        }

        private static String asString(final Object[] t) {
            final StringBuilder str = new StringBuilder(t[0].toString());
            for (int i = 1; i < t.length; i++) {
                if (i == (t.length - 1)) if (t.length > 1) str.append(" or ");
                else str.append(", ");
                str.append(t[i].toString());
            }
            return str.toString();
        }

        @Override
        public void print(final int lineOffset, final int columnOffset, final Lexer lexer) {
            final int line = token.line + lineOffset, colStart = token.columnStart + columnOffset, colEnd = token.columnEnd + columnOffset;
            System.err.printf("Error[%d:%d-%d] %s%n", line, colStart, colEnd, getMessage());
            AshError.numErrors++;

            // Print out the line and location of the error
            if (line <= lexer.lines.size()) {
                System.err.println(lexer.lines.get(line - 1));
                for (int i = 0; i < (colStart - 1); i++)
                    System.err.print(" ");
                System.err.print("^");
                if ((colEnd - colStart) > 1) {
                    for (int i = colStart; i < (colEnd - 2); i++)
                        System.err.print("-");
                    System.err.println("^");
                } else System.err.println();
            }
        }

    }

}
