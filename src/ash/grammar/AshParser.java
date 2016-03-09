// Generated from Ash.g by ANTLR 4.5.2
package ash.grammar;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AshParser extends Parser {
	public static final int
			WS = 1, OCT_INT = 2, BIN_INT = 3, HEX_INT = 4, INT = 5, DOT = 6, FLOAT = 7, DOUBLE = 8,
			STRING = 9, CHAR = 10, BOOL = 11, TYPE = 12, LAMBDA = 13, COMPOUND_ASSIGN_OP = 14,
			OP = 15, ARRAY_DIM = 16, ARROW = 17, PARENL = 18, PARENR = 19, BRACEL = 20, BRACER = 21,
			BRACKETL = 22, BRACKETR = 23, COMMA = 24, COLON = 25, VAR = 26, FUNC = 27, CONST = 28,
			CLASS = 29, OPERATOR = 30, ENUM = 31, INTTERFACE = 32, IMPORT = 33, PACKAGE = 34,
			RETURN = 35, BREAK = 36, CONTINUE = 37, DEFER = 38, AS = 39, IS = 40, NEW = 41, OP_TYPE = 42,
			MODIFIER = 43, THIS = 44, SUPER = 45, NULL = 46, IF = 47, ELSE = 48, WHILE = 49, FOR = 50,
			MATCH = 51, IN = 52, ID = 53;
	public static final int
			RULE_file = 0, RULE_packageDec = 1, RULE_qualifiedName = 2, RULE_importDec = 3,
			RULE_typeDec = 4, RULE_classDec = 5, RULE_typeDecParams = 6, RULE_typeDecSupers = 7,
			RULE_classBlock = 8, RULE_varDec = 9, RULE_funcDec = 10, RULE_funcParam = 11,
			RULE_mods = 12;
	public static final String[] ruleNames = {
			"file", "packageDec", "qualifiedName", "importDec", "typeDec", "classDec",
			"typeDecParams", "typeDecSupers", "classBlock", "varDec", "funcDec", "funcParam",
			"mods"
	};
	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	public static final String _serializedATN =
			"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\67~\4\2\t\2\4\3\t" +
					"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4" +
					"\f\t\f\4\r\t\r\4\16\t\16\3\2\5\2\36\n\2\3\2\7\2!\n\2\f\2\16\2$\13\2\3" +
					"\2\6\2\'\n\2\r\2\16\2(\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\7\4\63\n\4\f\4" +
					"\16\4\66\13\4\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\5\7A\n\7\3\7\5\7D\n" +
					"\7\3\7\5\7G\n\7\3\b\3\b\3\b\3\b\7\bM\n\b\f\b\16\bP\13\b\3\b\3\b\3\t\3" +
					"\t\3\t\3\t\7\tX\n\t\f\t\16\t[\13\t\3\n\3\n\7\n_\n\n\f\n\16\nb\13\n\3\n" +
					"\7\ne\n\n\f\n\16\nh\13\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3" +
					"\r\3\r\3\r\3\r\3\16\7\16y\n\16\f\16\16\16|\13\16\3\16\2\2\17\2\4\6\b\n" +
					"\f\16\20\22\24\26\30\32\2\3\4\2\34\34\36\36|\2\35\3\2\2\2\4,\3\2\2\2\6" +
					"/\3\2\2\2\b\67\3\2\2\2\n:\3\2\2\2\f=\3\2\2\2\16H\3\2\2\2\20S\3\2\2\2\22" +
					"\\\3\2\2\2\24k\3\2\2\2\26o\3\2\2\2\30s\3\2\2\2\32z\3\2\2\2\34\36\5\4\3" +
					"\2\35\34\3\2\2\2\35\36\3\2\2\2\36\"\3\2\2\2\37!\5\b\5\2 \37\3\2\2\2!$" +
					"\3\2\2\2\" \3\2\2\2\"#\3\2\2\2#&\3\2\2\2$\"\3\2\2\2%\'\5\n\6\2&%\3\2\2" +
					"\2\'(\3\2\2\2(&\3\2\2\2()\3\2\2\2)*\3\2\2\2*+\7\2\2\3+\3\3\2\2\2,-\7$" +
					"\2\2-.\5\6\4\2.\5\3\2\2\2/\64\7\67\2\2\60\61\7\b\2\2\61\63\7\67\2\2\62" +
					"\60\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\7\3\2\2\2\66" +
					"\64\3\2\2\2\678\7#\2\289\5\6\4\29\t\3\2\2\2:;\5\32\16\2;<\5\f\7\2<\13" +
					"\3\2\2\2=>\7\37\2\2>@\7\67\2\2?A\5\16\b\2@?\3\2\2\2@A\3\2\2\2AC\3\2\2" +
					"\2BD\5\20\t\2CB\3\2\2\2CD\3\2\2\2DF\3\2\2\2EG\5\22\n\2FE\3\2\2\2FG\3\2" +
					"\2\2G\r\3\2\2\2HI\7\24\2\2IN\5\30\r\2JK\7\32\2\2KM\5\30\r\2LJ\3\2\2\2" +
					"MP\3\2\2\2NL\3\2\2\2NO\3\2\2\2OQ\3\2\2\2PN\3\2\2\2QR\7\25\2\2R\17\3\2" +
					"\2\2ST\7\33\2\2TY\5\6\4\2UV\7\32\2\2VX\5\6\4\2WU\3\2\2\2X[\3\2\2\2YW\3" +
					"\2\2\2YZ\3\2\2\2Z\21\3\2\2\2[Y\3\2\2\2\\`\7\26\2\2]_\5\24\13\2^]\3\2\2" +
					"\2_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2af\3\2\2\2b`\3\2\2\2ce\5\26\f\2dc\3\2" +
					"\2\2eh\3\2\2\2fd\3\2\2\2fg\3\2\2\2gi\3\2\2\2hf\3\2\2\2ij\7\27\2\2j\23" +
					"\3\2\2\2kl\5\32\16\2lm\t\2\2\2mn\7\67\2\2n\25\3\2\2\2op\5\32\16\2pq\7" +
					"\35\2\2qr\7\67\2\2r\27\3\2\2\2st\7\67\2\2tu\7\33\2\2uv\7\67\2\2v\31\3" +
					"\2\2\2wy\7-\2\2xw\3\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{\33\3\2\2\2|z" +
					"\3\2\2\2\16\35\"(\64@CFNY`fz";
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

	public AshParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
	public ATN getATN() { return _ATN;
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(27);
				_la = _input.LA(1);
				if (_la == PACKAGE) {
					{
						setState(26);
						packageDec();
					}
				}

				setState(32);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == IMPORT) {
					{
						{
							setState(29);
							importDec();
						}
					}
					setState(34);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						{
							setState(35);
							typeDec();
						}
					}
					setState(38);
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while (_la == CLASS || _la == MODIFIER);
				setState(40);
				match(EOF);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final PackageDecContext packageDec() throws RecognitionException {
		PackageDecContext _localctx = new PackageDecContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_packageDec);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(42);
				match(PACKAGE);
				setState(43);
				qualifiedName();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(45);
				match(ID);
				setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
				while (_la ==DOT) {
				{
					{
						setState(46);
						match(DOT);
						setState(47);
				match(ID);
				}
				}
					setState(52);
				_errHandler.sync(this);
				_la = _input.LA(1);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public final ImportDecContext importDec() throws RecognitionException {
		ImportDecContext _localctx = new ImportDecContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_importDec);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(53);
				match(IMPORT);
				setState(54);
				qualifiedName();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final TypeDecContext typeDec() throws RecognitionException {
		TypeDecContext _localctx = new TypeDecContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_typeDec);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(56);
				mods();
				setState(57);
				classDec();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final ClassDecContext classDec() throws RecognitionException {
		ClassDecContext _localctx = new ClassDecContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_classDec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(59);
				match(CLASS);
				setState(60);
				match(ID);
				setState(62);
				_la = _input.LA(1);
				if (_la == PARENL) {
					{
						setState(61);
						typeDecParams();
					}
				}

				setState(65);
				_la = _input.LA(1);
				if (_la == COLON) {
					{
						setState(64);
						typeDecSupers();
					}
				}

				setState(68);
				_la = _input.LA(1);
				if (_la == BRACEL) {
					{
						setState(67);
						classBlock();
					}
				}

			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final TypeDecParamsContext typeDecParams() throws RecognitionException {
		TypeDecParamsContext _localctx = new TypeDecParamsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_typeDecParams);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(70);
				match(PARENL);
				setState(71);
				funcParam();
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == COMMA) {
					{
						{
							setState(72);
							match(COMMA);
							setState(73);
							funcParam();
						}
					}
					setState(78);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(79);
				match(PARENR);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final TypeDecSupersContext typeDecSupers() throws RecognitionException {
		TypeDecSupersContext _localctx = new TypeDecSupersContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_typeDecSupers);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(81);
				match(COLON);
				setState(82);
				qualifiedName();
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == COMMA) {
					{
						{
							setState(83);
							match(COMMA);
							setState(84);
							qualifiedName();
						}
					}
					setState(89);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final ClassBlockContext classBlock() throws RecognitionException {
		ClassBlockContext _localctx = new ClassBlockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_classBlock);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(90);
				match(BRACEL);
				setState(94);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						{
							{
								setState(91);
								varDec();
							}
						}
					}
					setState(96);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
				}
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == FUNC || _la == MODIFIER) {
					{
						{
							setState(97);
							funcDec();
						}
					}
					setState(102);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(103);
				match(BRACER);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final VarDecContext varDec() throws RecognitionException {
		VarDecContext _localctx = new VarDecContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_varDec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(105);
				mods();
				setState(106);
				_la = _input.LA(1);
				if (!(_la == VAR || _la == CONST)) {
					_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(107);
				match(ID);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final FuncDecContext funcDec() throws RecognitionException {
		FuncDecContext _localctx = new FuncDecContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_funcDec);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(109);
				mods();
				setState(110);
				match(FUNC);
				setState(111);
				match(ID);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final FuncParamContext funcParam() throws RecognitionException {
		FuncParamContext _localctx = new FuncParamContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_funcParam);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(113);
				match(ID);
				setState(114);
				match(COLON);
				setState(115);
				match(ID);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final ModsContext mods() throws RecognitionException {
		ModsContext _localctx = new ModsContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_mods);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == MODIFIER) {
					{
						{
							setState(117);
							match(MODIFIER);
						}
					}
					setState(122);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FileContext extends ParserRuleContext {
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode EOF() {
			return getToken(AshParser.EOF, 0);
		}

		public PackageDecContext packageDec() {
			return getRuleContext(PackageDecContext.class, 0);
		}

		public List<ImportDecContext> importDec() {
			return getRuleContexts(ImportDecContext.class);
		}

		public ImportDecContext importDec(int i) {
			return getRuleContext(ImportDecContext.class, i);
		}

		public List<TypeDecContext> typeDec() {
			return getRuleContexts(TypeDecContext.class);
		}

		public TypeDecContext typeDec(int i) {
			return getRuleContext(TypeDecContext.class, i);
		}

		@Override
		public int getRuleIndex() {
			return RULE_file;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterFile(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitFile(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class PackageDecContext extends ParserRuleContext {
		public PackageDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode PACKAGE() {
			return getToken(AshParser.PACKAGE, 0);
		}

		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_packageDec;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterPackageDec(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitPackageDec(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitPackageDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class QualifiedNameContext extends ParserRuleContext {
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public List<TerminalNode> ID() {
			return getTokens(AshParser.ID);
		}

		public TerminalNode ID(int i) {
			return getToken(AshParser.ID, i);
		}

		public List<TerminalNode> DOT() {
			return getTokens(AshParser.DOT);
		}

		public TerminalNode DOT(int i) {
			return getToken(AshParser.DOT, i);
		}

		@Override
		public int getRuleIndex() {
			return RULE_qualifiedName;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterQualifiedName(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitQualifiedName(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ImportDecContext extends ParserRuleContext {
		public ImportDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode IMPORT() {
			return getToken(AshParser.IMPORT, 0);
		}

		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_importDec;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterImportDec(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitImportDec(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitImportDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class TypeDecContext extends ParserRuleContext {
		public TypeDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public ModsContext mods() {
			return getRuleContext(ModsContext.class, 0);
		}

		public ClassDecContext classDec() {
			return getRuleContext(ClassDecContext.class, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_typeDec;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterTypeDec(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitTypeDec(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitTypeDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ClassDecContext extends ParserRuleContext {
		public ClassDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode CLASS() {
			return getToken(AshParser.CLASS, 0);
		}

		public TerminalNode ID() {
			return getToken(AshParser.ID, 0);
		}

		public TypeDecParamsContext typeDecParams() {
			return getRuleContext(TypeDecParamsContext.class, 0);
		}

		public TypeDecSupersContext typeDecSupers() {
			return getRuleContext(TypeDecSupersContext.class, 0);
		}

		public ClassBlockContext classBlock() {
			return getRuleContext(ClassBlockContext.class, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_classDec;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterClassDec(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitClassDec(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitClassDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class TypeDecParamsContext extends ParserRuleContext {
		public TypeDecParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode PARENL() {
			return getToken(AshParser.PARENL, 0);
		}

		public List<FuncParamContext> funcParam() {
			return getRuleContexts(FuncParamContext.class);
		}

		public FuncParamContext funcParam(int i) {
			return getRuleContext(FuncParamContext.class, i);
		}

		public TerminalNode PARENR() {
			return getToken(AshParser.PARENR, 0);
		}

		public List<TerminalNode> COMMA() {
			return getTokens(AshParser.COMMA);
		}

		public TerminalNode COMMA(int i) {
			return getToken(AshParser.COMMA, i);
		}

		@Override
		public int getRuleIndex() {
			return RULE_typeDecParams;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterTypeDecParams(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitTypeDecParams(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitTypeDecParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class TypeDecSupersContext extends ParserRuleContext {
		public TypeDecSupersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode COLON() {
			return getToken(AshParser.COLON, 0);
		}

		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}

		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class, i);
		}

		public List<TerminalNode> COMMA() {
			return getTokens(AshParser.COMMA);
		}

		public TerminalNode COMMA(int i) {
			return getToken(AshParser.COMMA, i);
		}

		@Override
		public int getRuleIndex() {
			return RULE_typeDecSupers;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterTypeDecSupers(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitTypeDecSupers(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitTypeDecSupers(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ClassBlockContext extends ParserRuleContext {
		public ClassBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode BRACEL() {
			return getToken(AshParser.BRACEL, 0);
		}

		public TerminalNode BRACER() {
			return getToken(AshParser.BRACER, 0);
		}

		public List<VarDecContext> varDec() {
			return getRuleContexts(VarDecContext.class);
		}

		public VarDecContext varDec(int i) {
			return getRuleContext(VarDecContext.class, i);
		}

		public List<FuncDecContext> funcDec() {
			return getRuleContexts(FuncDecContext.class);
		}

		public FuncDecContext funcDec(int i) {
			return getRuleContext(FuncDecContext.class, i);
		}

		@Override
		public int getRuleIndex() {
			return RULE_classBlock;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterClassBlock(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitClassBlock(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitClassBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class VarDecContext extends ParserRuleContext {
		public VarDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public ModsContext mods() {
			return getRuleContext(ModsContext.class, 0);
		}

		public TerminalNode ID() {
			return getToken(AshParser.ID, 0);
		}

		public TerminalNode VAR() {
			return getToken(AshParser.VAR, 0);
		}

		public TerminalNode CONST() {
			return getToken(AshParser.CONST, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_varDec;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterVarDec(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitVarDec(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitVarDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class FuncDecContext extends ParserRuleContext {
		public FuncDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public ModsContext mods() {
			return getRuleContext(ModsContext.class, 0);
		}

		public TerminalNode FUNC() {
			return getToken(AshParser.FUNC, 0);
		}

		public TerminalNode ID() {
			return getToken(AshParser.ID, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_funcDec;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterFuncDec(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitFuncDec(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitFuncDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class FuncParamContext extends ParserRuleContext {
		public FuncParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public List<TerminalNode> ID() {
			return getTokens(AshParser.ID);
		}

		public TerminalNode ID(int i) {
			return getToken(AshParser.ID, i);
		}

		public TerminalNode COLON() {
			return getToken(AshParser.COLON, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_funcParam;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterFuncParam(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitFuncParam(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitFuncParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ModsContext extends ParserRuleContext {
		public ModsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public List<TerminalNode> MODIFIER() {
			return getTokens(AshParser.MODIFIER);
		}

		public TerminalNode MODIFIER(int i) {
			return getToken(AshParser.MODIFIER, i);
		}

		@Override
		public int getRuleIndex() {
			return RULE_mods;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).enterMods(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof AshListener) ((AshListener) listener).exitMods(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof AshVisitor) return ((AshVisitor<? extends T>) visitor).visitMods(this);
			else return visitor.visitChildren(this);
		}
	}
}