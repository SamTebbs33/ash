// Generated from Ash.g by ANTLR 4.5.2
package ash.grammar.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AshParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, OCT_INT=2, BIN_INT=3, HEX_INT=4, INT=5, ASSIGN_OP=6, QUESTION=7, 
		DOT=8, FLOAT=9, DOUBLE=10, STRING=11, CHAR=12, BOOL=13, PRIMITIVE=14, 
		LAMBDA=15, COMPOUND_ASSIGN_OP=16, OP=17, ARRAY_DIM=18, ARROW=19, PARENL=20, 
		PARENR=21, BRACEL=22, BRACER=23, BRACKETL=24, BRACKETR=25, COMMA=26, COLON=27, 
		VAR=28, FUNC=29, CONST=30, CLASS=31, OPERATOR=32, ENUM=33, INTTERFACE=34, 
		IMPORT=35, PACKAGE=36, RETURN=37, BREAK=38, CONTINUE=39, DEFER=40, AS=41, 
		IS=42, NEW=43, OP_TYPE=44, MODIFIER=45, THIS=46, SUPER=47, NULL=48, IF=49, 
		ELSE=50, WHILE=51, FOR=52, MATCH=53, IN=54, ID=55;
	public static final int
		RULE_file = 0, RULE_packageDec = 1, RULE_qualifiedName = 2, RULE_importDec = 3, 
		RULE_type = 4, RULE_classDec = 5, RULE_typeDecParams = 6, RULE_typeDecSupers = 7, 
		RULE_classBlock = 8, RULE_varDec = 9, RULE_funcDec = 10, RULE_funcBlock = 11, 
		RULE_bracedBlock = 12, RULE_funcParam = 13, RULE_mods = 14, RULE_expr = 15, 
		RULE_stmt = 16, RULE_ifStmt = 17, RULE_elseIfStmt = 18, RULE_elseStmt = 19, 
		RULE_returnStmt = 20, RULE_suffix = 21, RULE_var = 22, RULE_varAssignment = 23, 
		RULE_funcCall = 24;
	public static final String[] ruleNames = {
		"file", "packageDec", "qualifiedName", "importDec", "type", "classDec", 
		"typeDecParams", "typeDecSupers", "classBlock", "varDec", "funcDec", "funcBlock", 
		"bracedBlock", "funcParam", "mods", "expr", "stmt", "ifStmt", "elseIfStmt", 
		"elseStmt", "returnStmt", "suffix", "var", "varAssignment", "funcCall"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, "'='", "'?'", "'.'", null, null, null, 
		null, null, null, "'->'", null, null, "'[]'", "'=>'", "'('", "')'", "'{'", 
		"'}'", "'['", "']'", "','", "':'", "'var'", "'func'", "'const'", "'class'", 
		"'operator'", "'enum'", "'interface'", "'import'", "'package'", "'return'", 
		"'break'", "'continue'", "'defer'", "'as'", "'is'", "'new'", null, null, 
		"'this'", "'super'", "'null'", "'if'", "'else'", "'while'", "'for'", "'match'", 
		"'in'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "WS", "OCT_INT", "BIN_INT", "HEX_INT", "INT", "ASSIGN_OP", "QUESTION", 
		"DOT", "FLOAT", "DOUBLE", "STRING", "CHAR", "BOOL", "PRIMITIVE", "LAMBDA", 
		"COMPOUND_ASSIGN_OP", "OP", "ARRAY_DIM", "ARROW", "PARENL", "PARENR", 
		"BRACEL", "BRACER", "BRACKETL", "BRACKETR", "COMMA", "COLON", "VAR", "FUNC", 
		"CONST", "CLASS", "OPERATOR", "ENUM", "INTTERFACE", "IMPORT", "PACKAGE", 
		"RETURN", "BREAK", "CONTINUE", "DEFER", "AS", "IS", "NEW", "OP_TYPE", 
		"MODIFIER", "THIS", "SUPER", "NULL", "IF", "ELSE", "WHILE", "FOR", "MATCH", 
		"IN", "ID"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
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
	public ATN getATN() { return _ATN; }

	public AshParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FileContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(AshParser.EOF, 0); }
		public PackageDecContext packageDec() {
			return getRuleContext(PackageDecContext.class,0);
		}
		public List<ImportDecContext> importDec() {
			return getRuleContexts(ImportDecContext.class);
		}
		public ImportDecContext importDec(int i) {
			return getRuleContext(ImportDecContext.class,i);
		}
		public List<ClassDecContext> classDec() {
			return getRuleContexts(ClassDecContext.class);
		}
		public ClassDecContext classDec(int i) {
			return getRuleContext(ClassDecContext.class,i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_la = _input.LA(1);
			if (_la==PACKAGE) {
				{
				setState(50);
				packageDec();
				}
			}

			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(53);
				importDec();
				}
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(60); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(59);
				classDec();
				}
				}
				setState(62); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CLASS || _la==MODIFIER );
			setState(64);
			match(EOF);
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

	public static class PackageDecContext extends ParserRuleContext {
		public TerminalNode PACKAGE() { return getToken(AshParser.PACKAGE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public PackageDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packageDec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitPackageDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PackageDecContext packageDec() throws RecognitionException {
		PackageDecContext _localctx = new PackageDecContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_packageDec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(PACKAGE);
			setState(67);
			qualifiedName();
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

	public static class QualifiedNameContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(AshParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(AshParser.ID, i);
		}
		public List<TerminalNode> DOT() { return getTokens(AshParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(AshParser.DOT, i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			match(ID);
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(70);
				match(DOT);
				setState(71);
				match(ID);
				}
				}
				setState(76);
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

	public static class ImportDecContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(AshParser.IMPORT, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public ImportDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitImportDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportDecContext importDec() throws RecognitionException {
		ImportDecContext _localctx = new ImportDecContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_importDec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(IMPORT);
			setState(78);
			qualifiedName();
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

	public static class TypeContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode PRIMITIVE() { return getToken(AshParser.PRIMITIVE, 0); }
		public List<TerminalNode> ARRAY_DIM() { return getTokens(AshParser.ARRAY_DIM); }
		public TerminalNode ARRAY_DIM(int i) {
			return getToken(AshParser.ARRAY_DIM, i);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(80);
				qualifiedName();
				}
				break;
			case PRIMITIVE:
				{
				setState(81);
				match(PRIMITIVE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ARRAY_DIM) {
				{
				{
				setState(84);
				match(ARRAY_DIM);
				}
				}
				setState(89);
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

	public static class ClassDecContext extends ParserRuleContext {
		public ModsContext mods() {
			return getRuleContext(ModsContext.class,0);
		}
		public TerminalNode CLASS() { return getToken(AshParser.CLASS, 0); }
		public TerminalNode ID() { return getToken(AshParser.ID, 0); }
		public TypeDecParamsContext typeDecParams() {
			return getRuleContext(TypeDecParamsContext.class,0);
		}
		public TypeDecSupersContext typeDecSupers() {
			return getRuleContext(TypeDecSupersContext.class,0);
		}
		public ClassBlockContext classBlock() {
			return getRuleContext(ClassBlockContext.class,0);
		}
		public ClassDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitClassDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDecContext classDec() throws RecognitionException {
		ClassDecContext _localctx = new ClassDecContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_classDec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			mods();
			setState(91);
			match(CLASS);
			setState(92);
			match(ID);
			setState(94);
			_la = _input.LA(1);
			if (_la==PARENL) {
				{
				setState(93);
				typeDecParams();
				}
			}

			setState(97);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(96);
				typeDecSupers();
				}
			}

			setState(100);
			_la = _input.LA(1);
			if (_la==BRACEL) {
				{
				setState(99);
				classBlock();
				}
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

	public static class TypeDecParamsContext extends ParserRuleContext {
		public TerminalNode PARENL() { return getToken(AshParser.PARENL, 0); }
		public List<FuncParamContext> funcParam() {
			return getRuleContexts(FuncParamContext.class);
		}
		public FuncParamContext funcParam(int i) {
			return getRuleContext(FuncParamContext.class,i);
		}
		public TerminalNode PARENR() { return getToken(AshParser.PARENR, 0); }
		public List<TerminalNode> COMMA() { return getTokens(AshParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AshParser.COMMA, i);
		}
		public TypeDecParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDecParams; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitTypeDecParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDecParamsContext typeDecParams() throws RecognitionException {
		TypeDecParamsContext _localctx = new TypeDecParamsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_typeDecParams);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(PARENL);
			setState(103);
			funcParam();
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(104);
				match(COMMA);
				setState(105);
				funcParam();
				}
				}
				setState(110);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(111);
			match(PARENR);
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

	public static class TypeDecSupersContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(AshParser.COLON, 0); }
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(AshParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AshParser.COMMA, i);
		}
		public TypeDecSupersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDecSupers; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitTypeDecSupers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDecSupersContext typeDecSupers() throws RecognitionException {
		TypeDecSupersContext _localctx = new TypeDecSupersContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_typeDecSupers);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(COLON);
			setState(114);
			qualifiedName();
			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(115);
				match(COMMA);
				setState(116);
				qualifiedName();
				}
				}
				setState(121);
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

	public static class ClassBlockContext extends ParserRuleContext {
		public TerminalNode BRACEL() { return getToken(AshParser.BRACEL, 0); }
		public TerminalNode BRACER() { return getToken(AshParser.BRACER, 0); }
		public List<VarDecContext> varDec() {
			return getRuleContexts(VarDecContext.class);
		}
		public VarDecContext varDec(int i) {
			return getRuleContext(VarDecContext.class,i);
		}
		public List<FuncDecContext> funcDec() {
			return getRuleContexts(FuncDecContext.class);
		}
		public FuncDecContext funcDec(int i) {
			return getRuleContext(FuncDecContext.class,i);
		}
		public ClassBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitClassBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBlockContext classBlock() throws RecognitionException {
		ClassBlockContext _localctx = new ClassBlockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_classBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(BRACEL);
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << FUNC) | (1L << MODIFIER))) != 0)) {
				{
				setState(125);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
				case 1:
					{
					setState(123);
					varDec();
					}
					break;
				case 2:
					{
					setState(124);
					funcDec();
					}
					break;
				}
				}
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(130);
			match(BRACER);
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

	public static class VarDecContext extends ParserRuleContext {
		public ModsContext mods() {
			return getRuleContext(ModsContext.class,0);
		}
		public TerminalNode VAR() { return getToken(AshParser.VAR, 0); }
		public TerminalNode ID() { return getToken(AshParser.ID, 0); }
		public TerminalNode COLON() { return getToken(AshParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VarDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitVarDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDecContext varDec() throws RecognitionException {
		VarDecContext _localctx = new VarDecContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_varDec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			mods();
			setState(133);
			match(VAR);
			setState(134);
			match(ID);
			setState(137);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(135);
				match(COLON);
				setState(136);
				type();
				}
			}

			setState(141);
			_la = _input.LA(1);
			if (_la==ASSIGN_OP) {
				{
				setState(139);
				match(ASSIGN_OP);
				setState(140);
				expr(0);
				}
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

	public static class FuncDecContext extends ParserRuleContext {
		public ModsContext mods() {
			return getRuleContext(ModsContext.class,0);
		}
		public TerminalNode FUNC() { return getToken(AshParser.FUNC, 0); }
		public TerminalNode ID() { return getToken(AshParser.ID, 0); }
		public FuncBlockContext funcBlock() {
			return getRuleContext(FuncBlockContext.class,0);
		}
		public TerminalNode PARENL() { return getToken(AshParser.PARENL, 0); }
		public TerminalNode PARENR() { return getToken(AshParser.PARENR, 0); }
		public TerminalNode LAMBDA() { return getToken(AshParser.LAMBDA, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<FuncParamContext> funcParam() {
			return getRuleContexts(FuncParamContext.class);
		}
		public FuncParamContext funcParam(int i) {
			return getRuleContext(FuncParamContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(AshParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AshParser.COMMA, i);
		}
		public FuncDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcDec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitFuncDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncDecContext funcDec() throws RecognitionException {
		FuncDecContext _localctx = new FuncDecContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_funcDec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			mods();
			setState(144);
			match(FUNC);
			setState(145);
			match(ID);
			setState(157);
			_la = _input.LA(1);
			if (_la==PARENL) {
				{
				setState(146);
				match(PARENL);
				{
				setState(147);
				funcParam();
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(148);
					match(COMMA);
					setState(149);
					funcParam();
					}
					}
					setState(154);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				setState(155);
				match(PARENR);
				}
			}

			setState(161);
			_la = _input.LA(1);
			if (_la==LAMBDA) {
				{
				setState(159);
				match(LAMBDA);
				setState(160);
				type();
				}
			}

			setState(163);
			funcBlock();
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

	public static class FuncBlockContext extends ParserRuleContext {
		public BracedBlockContext bracedBlock() {
			return getRuleContext(BracedBlockContext.class,0);
		}
		public TerminalNode ASSIGN_OP() { return getToken(AshParser.ASSIGN_OP, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public FuncBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitFuncBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncBlockContext funcBlock() throws RecognitionException {
		FuncBlockContext _localctx = new FuncBlockContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_funcBlock);
		try {
			setState(171);
			switch (_input.LA(1)) {
			case BRACEL:
				enterOuterAlt(_localctx, 1);
				{
				setState(165);
				bracedBlock();
				}
				break;
			case ASSIGN_OP:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(166);
				match(ASSIGN_OP);
				setState(169);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
				case 1:
					{
					setState(167);
					expr(0);
					}
					break;
				case 2:
					{
					setState(168);
					stmt();
					}
					break;
				}
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class BracedBlockContext extends ParserRuleContext {
		public TerminalNode BRACEL() { return getToken(AshParser.BRACEL, 0); }
		public TerminalNode BRACER() { return getToken(AshParser.BRACER, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public BracedBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bracedBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitBracedBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BracedBlockContext bracedBlock() throws RecognitionException {
		BracedBlockContext _localctx = new BracedBlockContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_bracedBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(173);
			match(BRACEL);
			setState(177);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BRACEL) | (1L << RETURN) | (1L << IF) | (1L << ID))) != 0)) {
				{
				{
				setState(174);
				stmt();
				}
				}
				setState(179);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(180);
			match(BRACER);
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

	public static class FuncParamContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(AshParser.ID, 0); }
		public TerminalNode COLON() { return getToken(AshParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FuncParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcParam; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitFuncParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncParamContext funcParam() throws RecognitionException {
		FuncParamContext _localctx = new FuncParamContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_funcParam);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(ID);
			setState(183);
			match(COLON);
			setState(184);
			type();
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

	public static class ModsContext extends ParserRuleContext {
		public List<TerminalNode> MODIFIER() { return getTokens(AshParser.MODIFIER); }
		public TerminalNode MODIFIER(int i) {
			return getToken(AshParser.MODIFIER, i);
		}
		public ModsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mods; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitMods(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModsContext mods() throws RecognitionException {
		ModsContext _localctx = new ModsContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_mods);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MODIFIER) {
				{
				{
				setState(186);
				match(MODIFIER);
				}
				}
				setState(191);
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

	public static class ExprContext extends ParserRuleContext {
		public Token prefixOp;
		public ExprContext bracketed;
		public Token binaryOp;
		public Token postfixOp;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OP() { return getToken(AshParser.OP, 0); }
		public TerminalNode INT() { return getToken(AshParser.INT, 0); }
		public TerminalNode HEX_INT() { return getToken(AshParser.HEX_INT, 0); }
		public TerminalNode OCT_INT() { return getToken(AshParser.OCT_INT, 0); }
		public TerminalNode BIN_INT() { return getToken(AshParser.BIN_INT, 0); }
		public TerminalNode FLOAT() { return getToken(AshParser.FLOAT, 0); }
		public TerminalNode DOUBLE() { return getToken(AshParser.DOUBLE, 0); }
		public TerminalNode STRING() { return getToken(AshParser.STRING, 0); }
		public TerminalNode CHAR() { return getToken(AshParser.CHAR, 0); }
		public TerminalNode BOOL() { return getToken(AshParser.BOOL, 0); }
		public TerminalNode PARENL() { return getToken(AshParser.PARENL, 0); }
		public TerminalNode PARENR() { return getToken(AshParser.PARENR, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public TerminalNode QUESTION() { return getToken(AshParser.QUESTION, 0); }
		public TerminalNode COLON() { return getToken(AshParser.COLON, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(193);
				((ExprContext)_localctx).prefixOp = match(OP);
				setState(194);
				expr(5);
				}
				break;
			case 2:
				{
				setState(195);
				match(INT);
				}
				break;
			case 3:
				{
				setState(196);
				match(HEX_INT);
				}
				break;
			case 4:
				{
				setState(197);
				match(OCT_INT);
				}
				break;
			case 5:
				{
				setState(198);
				match(BIN_INT);
				}
				break;
			case 6:
				{
				setState(199);
				match(FLOAT);
				}
				break;
			case 7:
				{
				setState(200);
				match(DOUBLE);
				}
				break;
			case 8:
				{
				setState(201);
				match(STRING);
				}
				break;
			case 9:
				{
				setState(202);
				match(CHAR);
				}
				break;
			case 10:
				{
				setState(203);
				match(BOOL);
				}
				break;
			case 11:
				{
				setState(204);
				match(PARENL);
				setState(205);
				((ExprContext)_localctx).bracketed = expr(0);
				setState(206);
				match(PARENR);
				}
				break;
			case 12:
				{
				setState(208);
				var();
				}
				break;
			case 13:
				{
				setState(209);
				funcCall();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(225);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(223);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(212);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(213);
						((ExprContext)_localctx).binaryOp = match(OP);
						setState(214);
						expr(7);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(215);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(216);
						match(QUESTION);
						setState(217);
						expr(0);
						setState(218);
						match(COLON);
						setState(219);
						expr(2);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(221);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(222);
						((ExprContext)_localctx).postfixOp = match(OP);
						}
						break;
					}
					} 
				}
				setState(227);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public VarAssignmentContext varAssignment() {
			return getRuleContext(VarAssignmentContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public ReturnStmtContext returnStmt() {
			return getRuleContext(ReturnStmtContext.class,0);
		}
		public BracedBlockContext bracedBlock() {
			return getRuleContext(BracedBlockContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_stmt);
		try {
			setState(233);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(228);
				varAssignment();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(229);
				funcCall();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(230);
				ifStmt();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(231);
				returnStmt();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(232);
				bracedBlock();
				}
				break;
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

	public static class IfStmtContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(AshParser.IF, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BracedBlockContext bracedBlock() {
			return getRuleContext(BracedBlockContext.class,0);
		}
		public ElseIfStmtContext elseIfStmt() {
			return getRuleContext(ElseIfStmtContext.class,0);
		}
		public ElseStmtContext elseStmt() {
			return getRuleContext(ElseStmtContext.class,0);
		}
		public IfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmtContext ifStmt() throws RecognitionException {
		IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_ifStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			match(IF);
			setState(236);
			expr(0);
			setState(237);
			bracedBlock();
			setState(239);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(238);
				elseIfStmt();
				}
				break;
			}
			setState(242);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(241);
				elseStmt();
				}
				break;
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

	public static class ElseIfStmtContext extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(AshParser.ELSE, 0); }
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public ElseIfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseIfStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitElseIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseIfStmtContext elseIfStmt() throws RecognitionException {
		ElseIfStmtContext _localctx = new ElseIfStmtContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_elseIfStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(ELSE);
			setState(245);
			ifStmt();
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

	public static class ElseStmtContext extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(AshParser.ELSE, 0); }
		public BracedBlockContext bracedBlock() {
			return getRuleContext(BracedBlockContext.class,0);
		}
		public ElseStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitElseStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseStmtContext elseStmt() throws RecognitionException {
		ElseStmtContext _localctx = new ElseStmtContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_elseStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(ELSE);
			setState(248);
			bracedBlock();
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

	public static class ReturnStmtContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(AshParser.RETURN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitReturnStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStmtContext returnStmt() throws RecognitionException {
		ReturnStmtContext _localctx = new ReturnStmtContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_returnStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			match(RETURN);
			setState(252);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				setState(251);
				expr(0);
				}
				break;
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

	public static class SuffixContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(AshParser.DOT, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public SuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_suffix; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitSuffix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuffixContext suffix() throws RecognitionException {
		SuffixContext _localctx = new SuffixContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_suffix);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			match(DOT);
			setState(257);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(255);
				var();
				}
				break;
			case 2:
				{
				setState(256);
				funcCall();
				}
				break;
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

	public static class VarContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(AshParser.ID, 0); }
		public SuffixContext suffix() {
			return getRuleContext(SuffixContext.class,0);
		}
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			match(ID);
			setState(261);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				setState(260);
				suffix();
				}
				break;
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

	public static class VarAssignmentContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode ASSIGN_OP() { return getToken(AshParser.ASSIGN_OP, 0); }
		public TerminalNode COMPOUND_ASSIGN_OP() { return getToken(AshParser.COMPOUND_ASSIGN_OP, 0); }
		public VarAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varAssignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitVarAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarAssignmentContext varAssignment() throws RecognitionException {
		VarAssignmentContext _localctx = new VarAssignmentContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_varAssignment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(263);
			var();
			setState(264);
			_la = _input.LA(1);
			if ( !(_la==ASSIGN_OP || _la==COMPOUND_ASSIGN_OP) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(265);
			expr(0);
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

	public static class FuncCallContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(AshParser.ID, 0); }
		public TerminalNode PARENL() { return getToken(AshParser.PARENL, 0); }
		public TerminalNode PARENR() { return getToken(AshParser.PARENR, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public SuffixContext suffix() {
			return getRuleContext(SuffixContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(AshParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AshParser.COMMA, i);
		}
		public FuncCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcCall; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncCallContext funcCall() throws RecognitionException {
		FuncCallContext _localctx = new FuncCallContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_funcCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(ID);
			setState(268);
			match(PARENL);
			setState(277);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OCT_INT) | (1L << BIN_INT) | (1L << HEX_INT) | (1L << INT) | (1L << FLOAT) | (1L << DOUBLE) | (1L << STRING) | (1L << CHAR) | (1L << BOOL) | (1L << OP) | (1L << PARENL) | (1L << ID))) != 0)) {
				{
				setState(269);
				expr(0);
				setState(274);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(270);
					match(COMMA);
					setState(271);
					expr(0);
					}
					}
					setState(276);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(279);
			match(PARENR);
			setState(281);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(280);
				suffix();
				}
				break;
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 15:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 1);
		case 2:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\39\u011e\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\3\2\5\2\66\n\2\3\2\7\29\n\2\f\2\16\2<\13\2\3\2\6\2?\n\2\r\2"+
		"\16\2@\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\7\4K\n\4\f\4\16\4N\13\4\3\5\3\5"+
		"\3\5\3\6\3\6\5\6U\n\6\3\6\7\6X\n\6\f\6\16\6[\13\6\3\7\3\7\3\7\3\7\5\7"+
		"a\n\7\3\7\5\7d\n\7\3\7\5\7g\n\7\3\b\3\b\3\b\3\b\7\bm\n\b\f\b\16\bp\13"+
		"\b\3\b\3\b\3\t\3\t\3\t\3\t\7\tx\n\t\f\t\16\t{\13\t\3\n\3\n\3\n\7\n\u0080"+
		"\n\n\f\n\16\n\u0083\13\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\5\13\u008c\n"+
		"\13\3\13\3\13\5\13\u0090\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\7\f\u0099\n"+
		"\f\f\f\16\f\u009c\13\f\3\f\3\f\5\f\u00a0\n\f\3\f\3\f\5\f\u00a4\n\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\r\5\r\u00ac\n\r\5\r\u00ae\n\r\3\16\3\16\7\16\u00b2"+
		"\n\16\f\16\16\16\u00b5\13\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20\7\20\u00be"+
		"\n\20\f\20\16\20\u00c1\13\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00d5\n\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\7\21\u00e2\n\21\f\21"+
		"\16\21\u00e5\13\21\3\22\3\22\3\22\3\22\3\22\5\22\u00ec\n\22\3\23\3\23"+
		"\3\23\3\23\5\23\u00f2\n\23\3\23\5\23\u00f5\n\23\3\24\3\24\3\24\3\25\3"+
		"\25\3\25\3\26\3\26\5\26\u00ff\n\26\3\27\3\27\3\27\5\27\u0104\n\27\3\30"+
		"\3\30\5\30\u0108\n\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\7\32"+
		"\u0113\n\32\f\32\16\32\u0116\13\32\5\32\u0118\n\32\3\32\3\32\5\32\u011c"+
		"\n\32\3\32\2\3 \33\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60"+
		"\62\2\3\4\2\b\b\22\22\u0135\2\65\3\2\2\2\4D\3\2\2\2\6G\3\2\2\2\bO\3\2"+
		"\2\2\nT\3\2\2\2\f\\\3\2\2\2\16h\3\2\2\2\20s\3\2\2\2\22|\3\2\2\2\24\u0086"+
		"\3\2\2\2\26\u0091\3\2\2\2\30\u00ad\3\2\2\2\32\u00af\3\2\2\2\34\u00b8\3"+
		"\2\2\2\36\u00bf\3\2\2\2 \u00d4\3\2\2\2\"\u00eb\3\2\2\2$\u00ed\3\2\2\2"+
		"&\u00f6\3\2\2\2(\u00f9\3\2\2\2*\u00fc\3\2\2\2,\u0100\3\2\2\2.\u0105\3"+
		"\2\2\2\60\u0109\3\2\2\2\62\u010d\3\2\2\2\64\66\5\4\3\2\65\64\3\2\2\2\65"+
		"\66\3\2\2\2\66:\3\2\2\2\679\5\b\5\28\67\3\2\2\29<\3\2\2\2:8\3\2\2\2:;"+
		"\3\2\2\2;>\3\2\2\2<:\3\2\2\2=?\5\f\7\2>=\3\2\2\2?@\3\2\2\2@>\3\2\2\2@"+
		"A\3\2\2\2AB\3\2\2\2BC\7\2\2\3C\3\3\2\2\2DE\7&\2\2EF\5\6\4\2F\5\3\2\2\2"+
		"GL\79\2\2HI\7\n\2\2IK\79\2\2JH\3\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2M"+
		"\7\3\2\2\2NL\3\2\2\2OP\7%\2\2PQ\5\6\4\2Q\t\3\2\2\2RU\5\6\4\2SU\7\20\2"+
		"\2TR\3\2\2\2TS\3\2\2\2UY\3\2\2\2VX\7\24\2\2WV\3\2\2\2X[\3\2\2\2YW\3\2"+
		"\2\2YZ\3\2\2\2Z\13\3\2\2\2[Y\3\2\2\2\\]\5\36\20\2]^\7!\2\2^`\79\2\2_a"+
		"\5\16\b\2`_\3\2\2\2`a\3\2\2\2ac\3\2\2\2bd\5\20\t\2cb\3\2\2\2cd\3\2\2\2"+
		"df\3\2\2\2eg\5\22\n\2fe\3\2\2\2fg\3\2\2\2g\r\3\2\2\2hi\7\26\2\2in\5\34"+
		"\17\2jk\7\34\2\2km\5\34\17\2lj\3\2\2\2mp\3\2\2\2nl\3\2\2\2no\3\2\2\2o"+
		"q\3\2\2\2pn\3\2\2\2qr\7\27\2\2r\17\3\2\2\2st\7\35\2\2ty\5\6\4\2uv\7\34"+
		"\2\2vx\5\6\4\2wu\3\2\2\2x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2z\21\3\2\2\2{y\3"+
		"\2\2\2|\u0081\7\30\2\2}\u0080\5\24\13\2~\u0080\5\26\f\2\177}\3\2\2\2\177"+
		"~\3\2\2\2\u0080\u0083\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082"+
		"\u0084\3\2\2\2\u0083\u0081\3\2\2\2\u0084\u0085\7\31\2\2\u0085\23\3\2\2"+
		"\2\u0086\u0087\5\36\20\2\u0087\u0088\7\36\2\2\u0088\u008b\79\2\2\u0089"+
		"\u008a\7\35\2\2\u008a\u008c\5\n\6\2\u008b\u0089\3\2\2\2\u008b\u008c\3"+
		"\2\2\2\u008c\u008f\3\2\2\2\u008d\u008e\7\b\2\2\u008e\u0090\5 \21\2\u008f"+
		"\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\25\3\2\2\2\u0091\u0092\5\36\20"+
		"\2\u0092\u0093\7\37\2\2\u0093\u009f\79\2\2\u0094\u0095\7\26\2\2\u0095"+
		"\u009a\5\34\17\2\u0096\u0097\7\34\2\2\u0097\u0099\5\34\17\2\u0098\u0096"+
		"\3\2\2\2\u0099\u009c\3\2\2\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b"+
		"\u009d\3\2\2\2\u009c\u009a\3\2\2\2\u009d\u009e\7\27\2\2\u009e\u00a0\3"+
		"\2\2\2\u009f\u0094\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a3\3\2\2\2\u00a1"+
		"\u00a2\7\21\2\2\u00a2\u00a4\5\n\6\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3"+
		"\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6\5\30\r\2\u00a6\27\3\2\2\2\u00a7"+
		"\u00ae\5\32\16\2\u00a8\u00ab\7\b\2\2\u00a9\u00ac\5 \21\2\u00aa\u00ac\5"+
		"\"\22\2\u00ab\u00a9\3\2\2\2\u00ab\u00aa\3\2\2\2\u00ac\u00ae\3\2\2\2\u00ad"+
		"\u00a7\3\2\2\2\u00ad\u00a8\3\2\2\2\u00ae\31\3\2\2\2\u00af\u00b3\7\30\2"+
		"\2\u00b0\u00b2\5\"\22\2\u00b1\u00b0\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3"+
		"\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b6\3\2\2\2\u00b5\u00b3\3\2"+
		"\2\2\u00b6\u00b7\7\31\2\2\u00b7\33\3\2\2\2\u00b8\u00b9\79\2\2\u00b9\u00ba"+
		"\7\35\2\2\u00ba\u00bb\5\n\6\2\u00bb\35\3\2\2\2\u00bc\u00be\7/\2\2\u00bd"+
		"\u00bc\3\2\2\2\u00be\u00c1\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2"+
		"\2\2\u00c0\37\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c2\u00c3\b\21\1\2\u00c3\u00c4"+
		"\7\23\2\2\u00c4\u00d5\5 \21\7\u00c5\u00d5\7\7\2\2\u00c6\u00d5\7\6\2\2"+
		"\u00c7\u00d5\7\4\2\2\u00c8\u00d5\7\5\2\2\u00c9\u00d5\7\13\2\2\u00ca\u00d5"+
		"\7\f\2\2\u00cb\u00d5\7\r\2\2\u00cc\u00d5\7\16\2\2\u00cd\u00d5\7\17\2\2"+
		"\u00ce\u00cf\7\26\2\2\u00cf\u00d0\5 \21\2\u00d0\u00d1\7\27\2\2\u00d1\u00d5"+
		"\3\2\2\2\u00d2\u00d5\5.\30\2\u00d3\u00d5\5\62\32\2\u00d4\u00c2\3\2\2\2"+
		"\u00d4\u00c5\3\2\2\2\u00d4\u00c6\3\2\2\2\u00d4\u00c7\3\2\2\2\u00d4\u00c8"+
		"\3\2\2\2\u00d4\u00c9\3\2\2\2\u00d4\u00ca\3\2\2\2\u00d4\u00cb\3\2\2\2\u00d4"+
		"\u00cc\3\2\2\2\u00d4\u00cd\3\2\2\2\u00d4\u00ce\3\2\2\2\u00d4\u00d2\3\2"+
		"\2\2\u00d4\u00d3\3\2\2\2\u00d5\u00e3\3\2\2\2\u00d6\u00d7\f\b\2\2\u00d7"+
		"\u00d8\7\23\2\2\u00d8\u00e2\5 \21\t\u00d9\u00da\f\3\2\2\u00da\u00db\7"+
		"\t\2\2\u00db\u00dc\5 \21\2\u00dc\u00dd\7\35\2\2\u00dd\u00de\5 \21\4\u00de"+
		"\u00e2\3\2\2\2\u00df\u00e0\f\6\2\2\u00e0\u00e2\7\23\2\2\u00e1\u00d6\3"+
		"\2\2\2\u00e1\u00d9\3\2\2\2\u00e1\u00df\3\2\2\2\u00e2\u00e5\3\2\2\2\u00e3"+
		"\u00e1\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4!\3\2\2\2\u00e5\u00e3\3\2\2\2"+
		"\u00e6\u00ec\5\60\31\2\u00e7\u00ec\5\62\32\2\u00e8\u00ec\5$\23\2\u00e9"+
		"\u00ec\5*\26\2\u00ea\u00ec\5\32\16\2\u00eb\u00e6\3\2\2\2\u00eb\u00e7\3"+
		"\2\2\2\u00eb\u00e8\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ea\3\2\2\2\u00ec"+
		"#\3\2\2\2\u00ed\u00ee\7\63\2\2\u00ee\u00ef\5 \21\2\u00ef\u00f1\5\32\16"+
		"\2\u00f0\u00f2\5&\24\2\u00f1\u00f0\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f4"+
		"\3\2\2\2\u00f3\u00f5\5(\25\2\u00f4\u00f3\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5"+
		"%\3\2\2\2\u00f6\u00f7\7\64\2\2\u00f7\u00f8\5$\23\2\u00f8\'\3\2\2\2\u00f9"+
		"\u00fa\7\64\2\2\u00fa\u00fb\5\32\16\2\u00fb)\3\2\2\2\u00fc\u00fe\7\'\2"+
		"\2\u00fd\u00ff\5 \21\2\u00fe\u00fd\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff+"+
		"\3\2\2\2\u0100\u0103\7\n\2\2\u0101\u0104\5.\30\2\u0102\u0104\5\62\32\2"+
		"\u0103\u0101\3\2\2\2\u0103\u0102\3\2\2\2\u0104-\3\2\2\2\u0105\u0107\7"+
		"9\2\2\u0106\u0108\5,\27\2\u0107\u0106\3\2\2\2\u0107\u0108\3\2\2\2\u0108"+
		"/\3\2\2\2\u0109\u010a\5.\30\2\u010a\u010b\t\2\2\2\u010b\u010c\5 \21\2"+
		"\u010c\61\3\2\2\2\u010d\u010e\79\2\2\u010e\u0117\7\26\2\2\u010f\u0114"+
		"\5 \21\2\u0110\u0111\7\34\2\2\u0111\u0113\5 \21\2\u0112\u0110\3\2\2\2"+
		"\u0113\u0116\3\2\2\2\u0114\u0112\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0118"+
		"\3\2\2\2\u0116\u0114\3\2\2\2\u0117\u010f\3\2\2\2\u0117\u0118\3\2\2\2\u0118"+
		"\u0119\3\2\2\2\u0119\u011b\7\27\2\2\u011a\u011c\5,\27\2\u011b\u011a\3"+
		"\2\2\2\u011b\u011c\3\2\2\2\u011c\63\3\2\2\2$\65:@LTY`cfny\177\u0081\u008b"+
		"\u008f\u009a\u009f\u00a3\u00ab\u00ad\u00b3\u00bf\u00d4\u00e1\u00e3\u00eb"+
		"\u00f1\u00f4\u00fe\u0103\u0107\u0114\u0117\u011b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}