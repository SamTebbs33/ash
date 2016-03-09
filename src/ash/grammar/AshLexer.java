// Generated from Ash.g by ANTLR 4.5.2
package ash.grammar;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AshLexer extends Lexer {
	public static final int
			WS = 1, OCT_INT = 2, BIN_INT = 3, HEX_INT = 4, INT = 5, DOT = 6, FLOAT = 7, DOUBLE = 8,
			STRING = 9, CHAR = 10, BOOL = 11, TYPE = 12, LAMBDA = 13, COMPOUND_ASSIGN_OP = 14,
			OP = 15, ARRAY_DIM = 16, ARROW = 17, PARENL = 18, PARENR = 19, BRACEL = 20, BRACER = 21,
			BRACKETL = 22, BRACKETR = 23, COMMA = 24, COLON = 25, VAR = 26, FUNC = 27, CONST = 28,
			CLASS = 29, OPERATOR = 30, ENUM = 31, INTTERFACE = 32, IMPORT = 33, PACKAGE = 34,
			RETURN = 35, BREAK = 36, CONTINUE = 37, DEFER = 38, AS = 39, IS = 40, NEW = 41, OP_TYPE = 42,
			MODIFIER = 43, THIS = 44, SUPER = 45, NULL = 46, IF = 47, ELSE = 48, WHILE = 49, FOR = 50,
			MATCH = 51, IN = 52, ID = 53;
	public static final String[] ruleNames = {
			"WS", "OCT_INT", "BIN_INT", "HEX_INT", "INT", "DOT", "FLOAT", "DOUBLE",
			"STRING", "CHAR", "BOOL", "TYPE", "LAMBDA", "COMPOUND_ASSIGN_OP", "OP",
			"ARRAY_DIM", "ARROW", "PARENL", "PARENR", "BRACEL", "BRACER", "BRACKETL",
			"BRACKETR", "COMMA", "COLON", "VAR", "FUNC", "CONST", "CLASS", "OPERATOR",
			"ENUM", "INTTERFACE", "IMPORT", "PACKAGE", "RETURN", "BREAK", "CONTINUE",
			"DEFER", "AS", "IS", "NEW", "OP_TYPE", "MODIFIER", "THIS", "SUPER", "NULL",
			"IF", "ELSE", "WHILE", "FOR", "MATCH", "IN", "ID"
	};
	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	public static final String _serializedATN =
			"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\67\u0210\b\1\4\2" +
					"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
					"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
					"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
					"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t" +
					" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t" +
					"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64" +
					"\t\64\4\65\t\65\4\66\t\66\3\2\6\2o\n\2\r\2\16\2p\3\2\3\2\3\3\3\3\3\3\3" +
					"\3\6\3y\n\3\r\3\16\3z\3\4\3\4\3\4\3\4\6\4\u0081\n\4\r\4\16\4\u0082\3\5" +
					"\3\5\3\5\3\5\6\5\u0089\n\5\r\5\16\5\u008a\3\6\5\6\u008e\n\6\3\6\6\6\u0091" +
					"\n\6\r\6\16\6\u0092\3\7\3\7\3\b\5\b\u0098\n\b\3\b\6\b\u009b\n\b\r\b\16" +
					"\b\u009c\3\b\3\b\6\b\u00a1\n\b\r\b\16\b\u00a2\3\b\3\b\3\t\5\t\u00a8\n" +
					"\t\3\t\6\t\u00ab\n\t\r\t\16\t\u00ac\3\t\3\t\6\t\u00b1\n\t\r\t\16\t\u00b2" +
					"\3\n\3\n\7\n\u00b7\n\n\f\n\16\n\u00ba\13\n\3\n\3\n\3\13\3\13\3\13\3\13" +
					"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00cb\n\f\3\r\3\r\3\r\3\r\3\r" +
					"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3" +
					"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00f0\n\r\3\16" +
					"\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17" +
					"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17" +
					"\u010e\n\17\3\20\6\20\u0111\n\20\r\20\16\20\u0112\3\21\3\21\3\21\3\22" +
					"\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30" +
					"\3\31\3\31\3\32\3\32\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\35" +
					"\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37" +
					"\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3!\3!" +
					"\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3#\3#\3$\3$\3$\3" +
					"$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3&\3&\3&\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3" +
					"\'\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3" +
					"+\3+\3+\3+\3+\3+\3+\3+\3+\3+\5+\u01a0\n+\3,\3,\3,\3,\3,\3,\3,\3,\3,\3" +
					",\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3" +
					",\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3" +
					",\3,\3,\3,\5,\u01dd\n,\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3.\3/\3/\3/\3/\3" +
					"/\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\62\3\62\3\62" +
					"\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\66" +
					"\3\66\7\66\u020c\n\66\f\66\16\66\u020f\13\66\2\2\67\3\3\5\4\7\5\t\6\13" +
					"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'" +
					"\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'" +
					"M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67\3\2\13\5\2\13\f\17\17" +
					"\"\"\3\2\629\4\2\62\63~~\5\2\62;CHch\3\2\62;\4\2$$``\13\2\"#%%\'),-\60" +
					"\61>B``~~\u0080\u0080\4\2C\\c|\5\2\62;C\\c|\u0239\2\3\3\2\2\2\2\5\3\2" +
					"\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21" +
					"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2" +
					"\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3" +
					"\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3" +
					"\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3" +
					"\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2" +
					"\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2" +
					"Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3" +
					"\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\3n\3\2\2\2\5t\3\2\2\2\7|\3\2\2" +
					"\2\t\u0084\3\2\2\2\13\u008d\3\2\2\2\r\u0094\3\2\2\2\17\u0097\3\2\2\2\21" +
					"\u00a7\3\2\2\2\23\u00b4\3\2\2\2\25\u00bd\3\2\2\2\27\u00ca\3\2\2\2\31\u00ef" +
					"\3\2\2\2\33\u00f1\3\2\2\2\35\u010d\3\2\2\2\37\u0110\3\2\2\2!\u0114\3\2" +
					"\2\2#\u0117\3\2\2\2%\u011a\3\2\2\2\'\u011c\3\2\2\2)\u011e\3\2\2\2+\u0120" +
					"\3\2\2\2-\u0122\3\2\2\2/\u0124\3\2\2\2\61\u0126\3\2\2\2\63\u0128\3\2\2" +
					"\2\65\u012a\3\2\2\2\67\u012e\3\2\2\29\u0133\3\2\2\2;\u0139\3\2\2\2=\u013f" +
					"\3\2\2\2?\u0148\3\2\2\2A\u014d\3\2\2\2C\u0157\3\2\2\2E\u015e\3\2\2\2G" +
					"\u0166\3\2\2\2I\u016d\3\2\2\2K\u0173\3\2\2\2M\u017c\3\2\2\2O\u0182\3\2" +
					"\2\2Q\u0185\3\2\2\2S\u0188\3\2\2\2U\u019f\3\2\2\2W\u01dc\3\2\2\2Y\u01de" +
					"\3\2\2\2[\u01e3\3\2\2\2]\u01e9\3\2\2\2_\u01ee\3\2\2\2a\u01f1\3\2\2\2c" +
					"\u01f6\3\2\2\2e\u01fc\3\2\2\2g\u0200\3\2\2\2i\u0206\3\2\2\2k\u0209\3\2" +
					"\2\2mo\t\2\2\2nm\3\2\2\2op\3\2\2\2pn\3\2\2\2pq\3\2\2\2qr\3\2\2\2rs\b\2" +
					"\2\2s\4\3\2\2\2tu\7\62\2\2uv\7q\2\2vx\3\2\2\2wy\t\3\2\2xw\3\2\2\2yz\3" +
					"\2\2\2zx\3\2\2\2z{\3\2\2\2{\6\3\2\2\2|}\7\62\2\2}~\7d\2\2~\u0080\3\2\2" +
					"\2\177\u0081\t\4\2\2\u0080\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0080" +
					"\3\2\2\2\u0082\u0083\3\2\2\2\u0083\b\3\2\2\2\u0084\u0085\7\62\2\2\u0085" +
					"\u0086\7z\2\2\u0086\u0088\3\2\2\2\u0087\u0089\t\5\2\2\u0088\u0087\3\2" +
					"\2\2\u0089\u008a\3\2\2\2\u008a\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b" +
					"\n\3\2\2\2\u008c\u008e\7/\2\2\u008d\u008c\3\2\2\2\u008d\u008e\3\2\2\2" +
					"\u008e\u0090\3\2\2\2\u008f\u0091\t\6\2\2\u0090\u008f\3\2\2\2\u0091\u0092" +
					"\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\f\3\2\2\2\u0094" +
					"\u0095\7\60\2\2\u0095\16\3\2\2\2\u0096\u0098\7/\2\2\u0097\u0096\3\2\2" +
					"\2\u0097\u0098\3\2\2\2\u0098\u009a\3\2\2\2\u0099\u009b\t\6\2\2\u009a\u0099" +
					"\3\2\2\2\u009b\u009c\3\2\2\2\u009c\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d" +
					"\u009e\3\2\2\2\u009e\u00a0\7\60\2\2\u009f\u00a1\t\6\2\2\u00a0\u009f\3" +
					"\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3" +
					"\u00a4\3\2\2\2\u00a4\u00a5\7h\2\2\u00a5\20\3\2\2\2\u00a6\u00a8\7/\2\2" +
					"\u00a7\u00a6\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00aa\3\2\2\2\u00a9\u00ab" +
					"\t\6\2\2\u00aa\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ac" +
					"\u00ad\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00b0\7\60\2\2\u00af\u00b1\t" +
					"\6\2\2\u00b0\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b2" +
					"\u00b3\3\2\2\2\u00b3\22\3\2\2\2\u00b4\u00b8\7$\2\2\u00b5\u00b7\t\7\2\2" +
					"\u00b6\u00b5\3\2\2\2\u00b7\u00ba\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9" +
					"\3\2\2\2\u00b9\u00bb\3\2\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00bc\7$\2\2\u00bc" +
					"\24\3\2\2\2\u00bd\u00be\7)\2\2\u00be\u00bf\13\2\2\2\u00bf\u00c0\7)\2\2" +
					"\u00c0\26\3\2\2\2\u00c1\u00c2\7v\2\2\u00c2\u00c3\7t\2\2\u00c3\u00c4\7" +
					"w\2\2\u00c4\u00cb\7g\2\2\u00c5\u00c6\7h\2\2\u00c6\u00c7\7c\2\2\u00c7\u00c8" +
					"\7n\2\2\u00c8\u00c9\7u\2\2\u00c9\u00cb\7g\2\2\u00ca\u00c1\3\2\2\2\u00ca" +
					"\u00c5\3\2\2\2\u00cb\30\3\2\2\2\u00cc\u00cd\7d\2\2\u00cd\u00ce\7q\2\2" +
					"\u00ce\u00cf\7q\2\2\u00cf\u00f0\7n\2\2\u00d0\u00d1\7f\2\2\u00d1\u00d2" +
					"\7q\2\2\u00d2\u00d3\7w\2\2\u00d3\u00d4\7d\2\2\u00d4\u00d5\7n\2\2\u00d5" +
					"\u00f0\7g\2\2\u00d6\u00d7\7h\2\2\u00d7\u00d8\7n\2\2\u00d8\u00d9\7q\2\2" +
					"\u00d9\u00da\7c\2\2\u00da\u00f0\7v\2\2\u00db\u00dc\7n\2\2\u00dc\u00dd" +
					"\7q\2\2\u00dd\u00de\7p\2\2\u00de\u00f0\7i\2\2\u00df\u00e0\7k\2\2\u00e0" +
					"\u00e1\7p\2\2\u00e1\u00f0\7v\2\2\u00e2\u00e3\7u\2\2\u00e3\u00e4\7j\2\2" +
					"\u00e4\u00e5\7q\2\2\u00e5\u00e6\7t\2\2\u00e6\u00f0\7v\2\2\u00e7\u00e8" +
					"\7d\2\2\u00e8\u00e9\7{\2\2\u00e9\u00ea\7v\2\2\u00ea\u00f0\7g\2\2\u00eb" +
					"\u00ec\7e\2\2\u00ec\u00ed\7j\2\2\u00ed\u00ee\7c\2\2\u00ee\u00f0\7t\2\2" +
					"\u00ef\u00cc\3\2\2\2\u00ef\u00d0\3\2\2\2\u00ef\u00d6\3\2\2\2\u00ef\u00db" +
					"\3\2\2\2\u00ef\u00df\3\2\2\2\u00ef\u00e2\3\2\2\2\u00ef\u00e7\3\2\2\2\u00ef" +
					"\u00eb\3\2\2\2\u00f0\32\3\2\2\2\u00f1\u00f2\7/\2\2\u00f2\u00f3\7@\2\2" +
					"\u00f3\34\3\2\2\2\u00f4\u00f5\7/\2\2\u00f5\u010e\7?\2\2\u00f6\u00f7\7" +
					"-\2\2\u00f7\u010e\7?\2\2\u00f8\u00f9\7,\2\2\u00f9\u010e\7?\2\2\u00fa\u00fb" +
					"\7\61\2\2\u00fb\u010e\7?\2\2\u00fc\u00fd\7\'\2\2\u00fd\u010e\7?\2\2\u00fe" +
					"\u00ff\7,\2\2\u00ff\u0100\7,\2\2\u0100\u010e\7?\2\2\u0101\u0102\7`\2\2" +
					"\u0102\u010e\7?\2\2\u0103\u0104\7(\2\2\u0104\u010e\7?\2\2\u0105\u0106" +
					"\7~\2\2\u0106\u010e\7?\2\2\u0107\u0108\7>\2\2\u0108\u0109\7>\2\2\u0109" +
					"\u010e\7?\2\2\u010a\u010b\7~\2\2\u010b\u010c\7~\2\2\u010c\u010e\7?\2\2" +
					"\u010d\u00f4\3\2\2\2\u010d\u00f6\3\2\2\2\u010d\u00f8\3\2\2\2\u010d\u00fa" +
					"\3\2\2\2\u010d\u00fc\3\2\2\2\u010d\u00fe\3\2\2\2\u010d\u0101\3\2\2\2\u010d" +
					"\u0103\3\2\2\2\u010d\u0105\3\2\2\2\u010d\u0107\3\2\2\2\u010d\u010a\3\2" +
					"\2\2\u010e\36\3\2\2\2\u010f\u0111\t\b\2\2\u0110\u010f\3\2\2\2\u0111\u0112" +
					"\3\2\2\2\u0112\u0110\3\2\2\2\u0112\u0113\3\2\2\2\u0113 \3\2\2\2\u0114" +
					"\u0115\7]\2\2\u0115\u0116\7_\2\2\u0116\"\3\2\2\2\u0117\u0118\7?\2\2\u0118" +
					"\u0119\7@\2\2\u0119$\3\2\2\2\u011a\u011b\7*\2\2\u011b&\3\2\2\2\u011c\u011d" +
					"\7+\2\2\u011d(\3\2\2\2\u011e\u011f\7}\2\2\u011f*\3\2\2\2\u0120\u0121\7" +
					"\177\2\2\u0121,\3\2\2\2\u0122\u0123\7]\2\2\u0123.\3\2\2\2\u0124\u0125" +
					"\7_\2\2\u0125\60\3\2\2\2\u0126\u0127\7.\2\2\u0127\62\3\2\2\2\u0128\u0129" +
					"\7<\2\2\u0129\64\3\2\2\2\u012a\u012b\7x\2\2\u012b\u012c\7c\2\2\u012c\u012d" +
					"\7t\2\2\u012d\66\3\2\2\2\u012e\u012f\7h\2\2\u012f\u0130\7w\2\2\u0130\u0131" +
					"\7p\2\2\u0131\u0132\7e\2\2\u01328\3\2\2\2\u0133\u0134\7e\2\2\u0134\u0135" +
					"\7q\2\2\u0135\u0136\7p\2\2\u0136\u0137\7u\2\2\u0137\u0138\7v\2\2\u0138" +
					":\3\2\2\2\u0139\u013a\7e\2\2\u013a\u013b\7n\2\2\u013b\u013c\7c\2\2\u013c" +
					"\u013d\7u\2\2\u013d\u013e\7u\2\2\u013e<\3\2\2\2\u013f\u0140\7q\2\2\u0140" +
					"\u0141\7r\2\2\u0141\u0142\7g\2\2\u0142\u0143\7t\2\2\u0143\u0144\7c\2\2" +
					"\u0144\u0145\7v\2\2\u0145\u0146\7q\2\2\u0146\u0147\7t\2\2\u0147>\3\2\2" +
					"\2\u0148\u0149\7g\2\2\u0149\u014a\7p\2\2\u014a\u014b\7w\2\2\u014b\u014c" +
					"\7o\2\2\u014c@\3\2\2\2\u014d\u014e\7k\2\2\u014e\u014f\7p\2\2\u014f\u0150" +
					"\7v\2\2\u0150\u0151\7g\2\2\u0151\u0152\7t\2\2\u0152\u0153\7h\2\2\u0153" +
					"\u0154\7c\2\2\u0154\u0155\7e\2\2\u0155\u0156\7g\2\2\u0156B\3\2\2\2\u0157" +
					"\u0158\7k\2\2\u0158\u0159\7o\2\2\u0159\u015a\7r\2\2\u015a\u015b\7q\2\2" +
					"\u015b\u015c\7t\2\2\u015c\u015d\7v\2\2\u015dD\3\2\2\2\u015e\u015f\7r\2" +
					"\2\u015f\u0160\7c\2\2\u0160\u0161\7e\2\2\u0161\u0162\7m\2\2\u0162\u0163" +
					"\7c\2\2\u0163\u0164\7i\2\2\u0164\u0165\7g\2\2\u0165F\3\2\2\2\u0166\u0167" +
					"\7t\2\2\u0167\u0168\7g\2\2\u0168\u0169\7v\2\2\u0169\u016a\7w\2\2\u016a" +
					"\u016b\7t\2\2\u016b\u016c\7p\2\2\u016cH\3\2\2\2\u016d\u016e\7d\2\2\u016e" +
					"\u016f\7t\2\2\u016f\u0170\7g\2\2\u0170\u0171\7c\2\2\u0171\u0172\7m\2\2" +
					"\u0172J\3\2\2\2\u0173\u0174\7e\2\2\u0174\u0175\7q\2\2\u0175\u0176\7p\2" +
					"\2\u0176\u0177\7v\2\2\u0177\u0178\7k\2\2\u0178\u0179\7p\2\2\u0179\u017a" +
					"\7w\2\2\u017a\u017b\7g\2\2\u017bL\3\2\2\2\u017c\u017d\7f\2\2\u017d\u017e" +
					"\7g\2\2\u017e\u017f\7h\2\2\u017f\u0180\7g\2\2\u0180\u0181\7t\2\2\u0181" +
					"N\3\2\2\2\u0182\u0183\7c\2\2\u0183\u0184\7u\2\2\u0184P\3\2\2\2\u0185\u0186" +
					"\7k\2\2\u0186\u0187\7u\2\2\u0187R\3\2\2\2\u0188\u0189\7p\2\2\u0189\u018a" +
					"\7g\2\2\u018a\u018b\7y\2\2\u018bT\3\2\2\2\u018c\u018d\7d\2\2\u018d\u018e" +
					"\7k\2\2\u018e\u018f\7p\2\2\u018f\u0190\7c\2\2\u0190\u0191\7t\2\2\u0191" +
					"\u01a0\7{\2\2\u0192\u0193\7r\2\2\u0193\u0194\7t\2\2\u0194\u0195\7g\2\2" +
					"\u0195\u0196\7h\2\2\u0196\u0197\7k\2\2\u0197\u01a0\7z\2\2\u0198\u0199" +
					"\7r\2\2\u0199\u019a\7q\2\2\u019a\u019b\7u\2\2\u019b\u019c\7v\2\2\u019c" +
					"\u019d\7h\2\2\u019d\u019e\7k\2\2\u019e\u01a0\7z\2\2\u019f\u018c\3\2\2" +
					"\2\u019f\u0192\3\2\2\2\u019f\u0198\3\2\2\2\u01a0V\3\2\2\2\u01a1\u01a2" +
					"\7r\2\2\u01a2\u01a3\7w\2\2\u01a3\u01a4\7d\2\2\u01a4\u01a5\7n\2\2\u01a5" +
					"\u01a6\7k\2\2\u01a6\u01dd\7e\2\2\u01a7\u01a8\7r\2\2\u01a8\u01a9\7t\2\2" +
					"\u01a9\u01aa\7k\2\2\u01aa\u01ab\7x\2\2\u01ab\u01ac\7c\2\2\u01ac\u01ad" +
					"\7v\2\2\u01ad\u01dd\7g\2\2\u01ae\u01af\7r\2\2\u01af\u01b0\7t\2\2\u01b0" +
					"\u01b1\7q\2\2\u01b1\u01b2\7v\2\2\u01b2\u01b3\7g\2\2\u01b3\u01b4\7e\2\2" +
					"\u01b4\u01b5\7v\2\2\u01b5\u01b6\7g\2\2\u01b6\u01dd\7f\2\2\u01b7\u01b8" +
					"\7h\2\2\u01b8\u01b9\7k\2\2\u01b9\u01ba\7p\2\2\u01ba\u01bb\7c\2\2\u01bb" +
					"\u01dd\7n\2\2\u01bc\u01bd\7p\2\2\u01bd\u01be\7c\2\2\u01be\u01bf\7v\2\2" +
					"\u01bf\u01c0\7k\2\2\u01c0\u01c1\7x\2\2\u01c1\u01dd\7g\2\2\u01c2\u01c3" +
					"\7q\2\2\u01c3\u01c4\7x\2\2\u01c4\u01c5\7g\2\2\u01c5\u01c6\7t\2\2\u01c6" +
					"\u01c7\7t\2\2\u01c7\u01c8\7k\2\2\u01c8\u01c9\7f\2\2\u01c9\u01dd\7g\2\2" +
					"\u01ca\u01cb\7u\2\2\u01cb\u01cc\7v\2\2\u01cc\u01cd\7c\2\2\u01cd\u01ce" +
					"\7v\2\2\u01ce\u01cf\7k\2\2\u01cf\u01dd\7e\2\2\u01d0\u01d1\7u\2\2\u01d1" +
					"\u01d2\7{\2\2\u01d2\u01d3\7p\2\2\u01d3\u01d4\7e\2\2\u01d4\u01d5\7j\2\2" +
					"\u01d5\u01d6\7t\2\2\u01d6\u01d7\7q\2\2\u01d7\u01d8\7p\2\2\u01d8\u01d9" +
					"\7k\2\2\u01d9\u01da\7u\2\2\u01da\u01db\7g\2\2\u01db\u01dd\7f\2\2\u01dc" +
					"\u01a1\3\2\2\2\u01dc\u01a7\3\2\2\2\u01dc\u01ae\3\2\2\2\u01dc\u01b7\3\2" +
					"\2\2\u01dc\u01bc\3\2\2\2\u01dc\u01c2\3\2\2\2\u01dc\u01ca\3\2\2\2\u01dc" +
					"\u01d0\3\2\2\2\u01ddX\3\2\2\2\u01de\u01df\7v\2\2\u01df\u01e0\7j\2\2\u01e0" +
					"\u01e1\7k\2\2\u01e1\u01e2\7u\2\2\u01e2Z\3\2\2\2\u01e3\u01e4\7u\2\2\u01e4" +
					"\u01e5\7w\2\2\u01e5\u01e6\7r\2\2\u01e6\u01e7\7g\2\2\u01e7\u01e8\7t\2\2" +
					"\u01e8\\\3\2\2\2\u01e9\u01ea\7p\2\2\u01ea\u01eb\7w\2\2\u01eb\u01ec\7n" +
					"\2\2\u01ec\u01ed\7n\2\2\u01ed^\3\2\2\2\u01ee\u01ef\7k\2\2\u01ef\u01f0" +
					"\7h\2\2\u01f0`\3\2\2\2\u01f1\u01f2\7g\2\2\u01f2\u01f3\7n\2\2\u01f3\u01f4" +
					"\7u\2\2\u01f4\u01f5\7g\2\2\u01f5b\3\2\2\2\u01f6\u01f7\7y\2\2\u01f7\u01f8" +
					"\7j\2\2\u01f8\u01f9\7k\2\2\u01f9\u01fa\7n\2\2\u01fa\u01fb\7g\2\2\u01fb" +
					"d\3\2\2\2\u01fc\u01fd\7h\2\2\u01fd\u01fe\7q\2\2\u01fe\u01ff\7t\2\2\u01ff" +
					"f\3\2\2\2\u0200\u0201\7o\2\2\u0201\u0202\7c\2\2\u0202\u0203\7v\2\2\u0203" +
					"\u0204\7e\2\2\u0204\u0205\7j\2\2\u0205h\3\2\2\2\u0206\u0207\7k\2\2\u0207" +
					"\u0208\7p\2\2\u0208j\3\2\2\2\u0209\u020d\t\t\2\2\u020a\u020c\t\n\2\2\u020b" +
					"\u020a\3\2\2\2\u020c\u020f\3\2\2\2\u020d\u020b\3\2\2\2\u020d\u020e\3\2" +
					"\2\2\u020el\3\2\2\2\u020f\u020d\3\2\2\2\27\2pz\u0082\u008a\u008d\u0092" +
					"\u0097\u009c\u00a2\u00a7\u00ac\u00b2\u00b8\u00ca\u00ef\u010d\u0112\u019f" +
					"\u01dc\u020d\3\b\2\2";
	public static final ATN _ATN =
			new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
			new PredictionContextCache();
	private static final String[] _LITERAL_NAMES = {
			null, null, null, null, null, null, "'.'", null, null, null, null, null,
			null, "'->'", null, null, "'[]'", "'=>'", "'('", "')'", "'{'", "'}'",
			"'['", "']'", "','", "':'", "'var'", "'func'", "'const'", "'class'", "'operator'",
			"'enum'", "'interface'", "'import'", "'package'", "'return'", "'break'",
			"'continue'", "'defer'", "'as'", "'is'", "'new'", null, null, "'this'",
			"'super'", "'null'", "'if'", "'else'", "'while'", "'for'", "'match'",
			"'in'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
			null, "WS", "OCT_INT", "BIN_INT", "HEX_INT", "INT", "DOT", "FLOAT", "DOUBLE",
			"STRING", "CHAR", "BOOL", "TYPE", "LAMBDA", "COMPOUND_ASSIGN_OP", "OP",
			"ARRAY_DIM", "ARROW", "PARENL", "PARENR", "BRACEL", "BRACER", "BRACKETL",
			"BRACKETR", "COMMA", "COLON", "VAR", "FUNC", "CONST", "CLASS", "OPERATOR",
			"ENUM", "INTTERFACE", "IMPORT", "PACKAGE", "RETURN", "BREAK", "CONTINUE",
			"DEFER", "AS", "IS", "NEW", "OP_TYPE", "MODIFIER", "THIS", "SUPER", "NULL",
			"IF", "ELSE", "WHILE", "FOR", "MATCH", "IN", "ID"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
	public static String[] modeNames = {
			"DEFAULT_MODE"
	};

	static {
		RuntimeMetaData.checkVersion("4.5.2", RuntimeMetaData.VERSION); }

	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}

	public AshLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Ash.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }
}