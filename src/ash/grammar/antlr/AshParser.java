// Generated from Ash.g by ANTLR 4.5.3
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
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		STRING=1, WS=2, OCT_INT=3, BIN_INT=4, HEX_INT=5, INT=6, ASSIGN_OP=7, QUESTION=8, 
		DOT=9, FLOAT=10, DOUBLE=11, CHAR=12, BOOL=13, PRIMITIVE=14, LAMBDA=15, 
		COMPOUND_ASSIGN_OP=16, OP=17, ARRAY_DIM=18, ARROW=19, PARENL=20, PARENR=21, 
		BRACEL=22, BRACER=23, BRACKETL=24, BRACKETR=25, COMMA=26, COLON=27, VAR=28, 
		FUNC=29, CONST=30, CLASS=31, OPERATOR=32, ENUM=33, INTTERFACE=34, IMPORT=35, 
		PACKAGE=36, RETURN=37, BREAK=38, CONTINUE=39, DEFER=40, AS=41, IS=42, 
		NEW=43, OP_TYPE=44, MODIFIER=45, THIS=46, SUPER=47, NULL=48, IF=49, ELSE=50, 
		WHILE=51, FOR=52, MATCH=53, IN=54, ID=55;
	public static final int
		RULE_typeDef = 0, RULE_file = 1, RULE_packageDec = 2, RULE_qualifiedName = 3, 
		RULE_importDec = 4, RULE_aliasedImport = 5, RULE_multiImport = 6, RULE_type = 7, 
		RULE_classDec = 8, RULE_params = 9, RULE_typeDecSupers = 10, RULE_classBlock = 11, 
		RULE_varDec = 12, RULE_funcDec = 13, RULE_funcBlock = 14, RULE_bracedBlock = 15, 
		RULE_funcParam = 16, RULE_mods = 17, RULE_expr = 18, RULE_stmt = 19, RULE_ifStmt = 20, 
		RULE_elseIfStmt = 21, RULE_elseStmt = 22, RULE_returnStmt = 23, RULE_suffix = 24, 
		RULE_var = 25, RULE_varAssignment = 26, RULE_funcCall = 27;
	public static final String[] ruleNames = {
		"typeDef", "file", "packageDec", "qualifiedName", "importDec", "aliasedImport", 
		"multiImport", "type", "classDec", "params", "typeDecSupers", "classBlock", 
		"varDec", "funcDec", "funcBlock", "bracedBlock", "funcParam", "mods", 
		"expr", "stmt", "ifStmt", "elseIfStmt", "elseStmt", "returnStmt", "suffix", 
		"var", "varAssignment", "funcCall"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, "'='", "'?'", "'.'", null, null, 
		null, null, null, "'->'", null, null, "'[]'", "'=>'", "'('", "')'", "'{'", 
		"'}'", "'['", "']'", "','", "':'", "'var'", "'func'", "'const'", "'class'", 
		"'operator'", "'enum'", "'interface'", "'import'", "'package'", "'return'", 
		"'break'", "'continue'", "'defer'", "'as'", "'is'", "'new'", null, null, 
		"'this'", "'super'", "'null'", "'if'", "'else'", "'while'", "'for'", "'match'", 
		"'in'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "STRING", "WS", "OCT_INT", "BIN_INT", "HEX_INT", "INT", "ASSIGN_OP", 
		"QUESTION", "DOT", "FLOAT", "DOUBLE", "CHAR", "BOOL", "PRIMITIVE", "LAMBDA", 
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
	public static class TypeDefContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(AshParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitTypeDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDefContext typeDef() throws RecognitionException {
		TypeDefContext _localctx = new TypeDefContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_typeDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(COLON);
			setState(57);
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
		enterRule(_localctx, 2, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			_la = _input.LA(1);
			if (_la==PACKAGE) {
				{
				setState(59);
				packageDec();
				}
			}

			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(62);
				importDec();
				}
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(69); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(68);
				classDec();
				}
				}
				setState(71); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CLASS || _la==MODIFIER );
			setState(73);
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
		enterRule(_localctx, 4, RULE_packageDec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(PACKAGE);
			setState(76);
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
		enterRule(_localctx, 6, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(ID);
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(79);
				match(DOT);
				setState(80);
				match(ID);
				}
				}
				setState(85);
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
		public AliasedImportContext aliasedImport() {
			return getRuleContext(AliasedImportContext.class,0);
		}
		public MultiImportContext multiImport() {
			return getRuleContext(MultiImportContext.class,0);
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
		enterRule(_localctx, 8, RULE_importDec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			match(IMPORT);
			setState(87);
			qualifiedName();
			setState(90);
			switch (_input.LA(1)) {
			case AS:
				{
				setState(88);
				aliasedImport();
				}
				break;
			case COMMA:
				{
				setState(89);
				multiImport();
				}
				break;
			case CLASS:
			case IMPORT:
			case MODIFIER:
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class AliasedImportContext extends ParserRuleContext {
		public TerminalNode AS() { return getToken(AshParser.AS, 0); }
		public TerminalNode ID() { return getToken(AshParser.ID, 0); }
		public AliasedImportContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aliasedImport; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitAliasedImport(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AliasedImportContext aliasedImport() throws RecognitionException {
		AliasedImportContext _localctx = new AliasedImportContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_aliasedImport);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			match(AS);
			setState(93);
			match(ID);
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

	public static class MultiImportContext extends ParserRuleContext {
		public List<TerminalNode> COMMA() { return getTokens(AshParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AshParser.COMMA, i);
		}
		public List<TerminalNode> ID() { return getTokens(AshParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(AshParser.ID, i);
		}
		public MultiImportContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiImport; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitMultiImport(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiImportContext multiImport() throws RecognitionException {
		MultiImportContext _localctx = new MultiImportContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_multiImport);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(95);
				match(COMMA);
				setState(96);
				match(ID);
				}
				}
				setState(99); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==COMMA );
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
		public TerminalNode ID() { return getToken(AshParser.ID, 0); }
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
		enterRule(_localctx, 14, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(101);
				match(ID);
				}
				break;
			case 2:
				{
				setState(102);
				qualifiedName();
				}
				break;
			case 3:
				{
				setState(103);
				match(PRIMITIVE);
				}
				break;
			}
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ARRAY_DIM) {
				{
				{
				setState(106);
				match(ARRAY_DIM);
				}
				}
				setState(111);
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
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
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
		enterRule(_localctx, 16, RULE_classDec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			mods();
			setState(113);
			match(CLASS);
			setState(114);
			match(ID);
			setState(116);
			_la = _input.LA(1);
			if (_la==PARENL) {
				{
				setState(115);
				params();
				}
			}

			setState(119);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(118);
				typeDecSupers();
				}
			}

			setState(122);
			_la = _input.LA(1);
			if (_la==BRACEL) {
				{
				setState(121);
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

	public static class ParamsContext extends ParserRuleContext {
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
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AshVisitor ) return ((AshVisitor<? extends T>)visitor).visitParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_params);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			match(PARENL);
			setState(125);
			funcParam();
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(126);
				match(COMMA);
				setState(127);
				funcParam();
				}
				}
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(133);
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
		enterRule(_localctx, 20, RULE_typeDecSupers);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			match(COLON);
			setState(136);
			qualifiedName();
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(137);
				match(COMMA);
				setState(138);
				qualifiedName();
				}
				}
				setState(143);
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
		enterRule(_localctx, 22, RULE_classBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(BRACEL);
			setState(149);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << FUNC) | (1L << MODIFIER))) != 0)) {
				{
				setState(147);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
				case 1:
					{
					setState(145);
					varDec();
					}
					break;
				case 2:
					{
					setState(146);
					funcDec();
					}
					break;
				}
				}
				setState(151);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(152);
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
		public TypeDefContext typeDef() {
			return getRuleContext(TypeDefContext.class,0);
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
		enterRule(_localctx, 24, RULE_varDec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			mods();
			setState(155);
			match(VAR);
			setState(156);
			match(ID);
			setState(158);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(157);
				typeDef();
				}
			}

			setState(162);
			_la = _input.LA(1);
			if (_la==ASSIGN_OP) {
				{
				setState(160);
				match(ASSIGN_OP);
				setState(161);
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
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public TerminalNode LAMBDA() { return getToken(AshParser.LAMBDA, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
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
		enterRule(_localctx, 26, RULE_funcDec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			mods();
			setState(165);
			match(FUNC);
			setState(166);
			match(ID);
			setState(168);
			_la = _input.LA(1);
			if (_la==PARENL) {
				{
				setState(167);
				params();
				}
			}

			setState(172);
			_la = _input.LA(1);
			if (_la==LAMBDA) {
				{
				setState(170);
				match(LAMBDA);
				setState(171);
				type();
				}
			}

			setState(174);
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
		enterRule(_localctx, 28, RULE_funcBlock);
		try {
			setState(182);
			switch (_input.LA(1)) {
			case BRACEL:
				enterOuterAlt(_localctx, 1);
				{
				setState(176);
				bracedBlock();
				}
				break;
			case ASSIGN_OP:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(177);
				match(ASSIGN_OP);
				setState(180);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
				case 1:
					{
					setState(178);
					expr(0);
					}
					break;
				case 2:
					{
					setState(179);
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
		enterRule(_localctx, 30, RULE_bracedBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			match(BRACEL);
			setState(188);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BRACEL) | (1L << VAR) | (1L << RETURN) | (1L << MODIFIER) | (1L << IF) | (1L << ID))) != 0)) {
				{
				{
				setState(185);
				stmt();
				}
				}
				setState(190);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(191);
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

	public static class FuncParamContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(AshParser.ID, 0); }
		public TypeDefContext typeDef() {
			return getRuleContext(TypeDefContext.class,0);
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
		enterRule(_localctx, 32, RULE_funcParam);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(ID);
			setState(194);
			typeDef();
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
		enterRule(_localctx, 34, RULE_mods);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MODIFIER) {
				{
				{
				setState(196);
				match(MODIFIER);
				}
				}
				setState(201);
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
		public ExprContext bracketed;
		public Token prefixOp;
		public Token list;
		public Token binaryOp;
		public Token postfixOp;
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
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OP() { return getToken(AshParser.OP, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public TerminalNode BRACEL() { return getToken(AshParser.BRACEL, 0); }
		public TerminalNode BRACER() { return getToken(AshParser.BRACER, 0); }
		public List<TerminalNode> COMMA() { return getTokens(AshParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AshParser.COMMA, i);
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
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(203);
				match(INT);
				}
				break;
			case 2:
				{
				setState(204);
				match(HEX_INT);
				}
				break;
			case 3:
				{
				setState(205);
				match(OCT_INT);
				}
				break;
			case 4:
				{
				setState(206);
				match(BIN_INT);
				}
				break;
			case 5:
				{
				setState(207);
				match(FLOAT);
				}
				break;
			case 6:
				{
				setState(208);
				match(DOUBLE);
				}
				break;
			case 7:
				{
				setState(209);
				match(STRING);
				}
				break;
			case 8:
				{
				setState(210);
				match(CHAR);
				}
				break;
			case 9:
				{
				setState(211);
				match(BOOL);
				}
				break;
			case 10:
				{
				setState(212);
				match(PARENL);
				setState(213);
				((ExprContext)_localctx).bracketed = expr(0);
				setState(214);
				match(PARENR);
				}
				break;
			case 11:
				{
				setState(216);
				((ExprContext)_localctx).prefixOp = match(OP);
				setState(217);
				expr(6);
				}
				break;
			case 12:
				{
				setState(218);
				var();
				}
				break;
			case 13:
				{
				setState(219);
				funcCall();
				}
				break;
			case 14:
				{
				setState(220);
				match(BRACEL);
				setState(229);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << OCT_INT) | (1L << BIN_INT) | (1L << HEX_INT) | (1L << INT) | (1L << FLOAT) | (1L << DOUBLE) | (1L << CHAR) | (1L << BOOL) | (1L << OP) | (1L << PARENL) | (1L << BRACEL) | (1L << ID))) != 0)) {
					{
					setState(221);
					expr(0);
					setState(226);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(222);
						((ExprContext)_localctx).list = match(COMMA);
						setState(223);
						expr(0);
						}
						}
						setState(228);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(231);
				match(BRACER);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(247);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(245);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(234);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(235);
						((ExprContext)_localctx).binaryOp = match(OP);
						setState(236);
						expr(8);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(237);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(238);
						match(QUESTION);
						setState(239);
						expr(0);
						setState(240);
						match(COLON);
						setState(241);
						expr(2);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(243);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(244);
						((ExprContext)_localctx).postfixOp = match(OP);
						}
						break;
					}
					} 
				}
				setState(249);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
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
		public VarDecContext varDec() {
			return getRuleContext(VarDecContext.class,0);
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
		enterRule(_localctx, 38, RULE_stmt);
		try {
			setState(256);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(250);
				varAssignment();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(251);
				funcCall();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(252);
				ifStmt();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(253);
				returnStmt();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(254);
				bracedBlock();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(255);
				varDec();
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
		enterRule(_localctx, 40, RULE_ifStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			match(IF);
			setState(259);
			expr(0);
			setState(260);
			bracedBlock();
			setState(262);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(261);
				elseIfStmt();
				}
				break;
			}
			setState(265);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				setState(264);
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
		enterRule(_localctx, 42, RULE_elseIfStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(ELSE);
			setState(268);
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
		enterRule(_localctx, 44, RULE_elseStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			match(ELSE);
			setState(271);
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
		enterRule(_localctx, 46, RULE_returnStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			match(RETURN);
			setState(275);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(274);
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
		enterRule(_localctx, 48, RULE_suffix);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			match(DOT);
			setState(280);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(278);
				var();
				}
				break;
			case 2:
				{
				setState(279);
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
		enterRule(_localctx, 50, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			match(ID);
			setState(284);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(283);
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
		enterRule(_localctx, 52, RULE_varAssignment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			var();
			setState(287);
			_la = _input.LA(1);
			if ( !(_la==ASSIGN_OP || _la==COMPOUND_ASSIGN_OP) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(288);
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
		enterRule(_localctx, 54, RULE_funcCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			match(ID);
			setState(291);
			match(PARENL);
			setState(300);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << OCT_INT) | (1L << BIN_INT) | (1L << HEX_INT) | (1L << INT) | (1L << FLOAT) | (1L << DOUBLE) | (1L << CHAR) | (1L << BOOL) | (1L << OP) | (1L << PARENL) | (1L << BRACEL) | (1L << ID))) != 0)) {
				{
				setState(292);
				expr(0);
				setState(297);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(293);
					match(COMMA);
					setState(294);
					expr(0);
					}
					}
					setState(299);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(302);
			match(PARENR);
			setState(304);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				setState(303);
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
		case 18:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 1);
		case 2:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\39\u0135\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\2\3\3\5\3?\n\3\3\3"+
		"\7\3B\n\3\f\3\16\3E\13\3\3\3\6\3H\n\3\r\3\16\3I\3\3\3\3\3\4\3\4\3\4\3"+
		"\5\3\5\3\5\7\5T\n\5\f\5\16\5W\13\5\3\6\3\6\3\6\3\6\5\6]\n\6\3\7\3\7\3"+
		"\7\3\b\3\b\6\bd\n\b\r\b\16\be\3\t\3\t\3\t\5\tk\n\t\3\t\7\tn\n\t\f\t\16"+
		"\tq\13\t\3\n\3\n\3\n\3\n\5\nw\n\n\3\n\5\nz\n\n\3\n\5\n}\n\n\3\13\3\13"+
		"\3\13\3\13\7\13\u0083\n\13\f\13\16\13\u0086\13\13\3\13\3\13\3\f\3\f\3"+
		"\f\3\f\7\f\u008e\n\f\f\f\16\f\u0091\13\f\3\r\3\r\3\r\7\r\u0096\n\r\f\r"+
		"\16\r\u0099\13\r\3\r\3\r\3\16\3\16\3\16\3\16\5\16\u00a1\n\16\3\16\3\16"+
		"\5\16\u00a5\n\16\3\17\3\17\3\17\3\17\5\17\u00ab\n\17\3\17\3\17\5\17\u00af"+
		"\n\17\3\17\3\17\3\20\3\20\3\20\3\20\5\20\u00b7\n\20\5\20\u00b9\n\20\3"+
		"\21\3\21\7\21\u00bd\n\21\f\21\16\21\u00c0\13\21\3\21\3\21\3\22\3\22\3"+
		"\22\3\23\7\23\u00c8\n\23\f\23\16\23\u00cb\13\23\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\24\7\24\u00e3\n\24\f\24\16\24\u00e6\13\24\5\24\u00e8\n"+
		"\24\3\24\5\24\u00eb\n\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\7\24\u00f8\n\24\f\24\16\24\u00fb\13\24\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\5\25\u0103\n\25\3\26\3\26\3\26\3\26\5\26\u0109\n\26\3\26\5"+
		"\26\u010c\n\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\5\31\u0116\n\31"+
		"\3\32\3\32\3\32\5\32\u011b\n\32\3\33\3\33\5\33\u011f\n\33\3\34\3\34\3"+
		"\34\3\34\3\35\3\35\3\35\3\35\3\35\7\35\u012a\n\35\f\35\16\35\u012d\13"+
		"\35\5\35\u012f\n\35\3\35\3\35\5\35\u0133\n\35\3\35\2\3&\36\2\4\6\b\n\f"+
		"\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668\2\3\4\2\t\t\22\22\u0150"+
		"\2:\3\2\2\2\4>\3\2\2\2\6M\3\2\2\2\bP\3\2\2\2\nX\3\2\2\2\f^\3\2\2\2\16"+
		"c\3\2\2\2\20j\3\2\2\2\22r\3\2\2\2\24~\3\2\2\2\26\u0089\3\2\2\2\30\u0092"+
		"\3\2\2\2\32\u009c\3\2\2\2\34\u00a6\3\2\2\2\36\u00b8\3\2\2\2 \u00ba\3\2"+
		"\2\2\"\u00c3\3\2\2\2$\u00c9\3\2\2\2&\u00ea\3\2\2\2(\u0102\3\2\2\2*\u0104"+
		"\3\2\2\2,\u010d\3\2\2\2.\u0110\3\2\2\2\60\u0113\3\2\2\2\62\u0117\3\2\2"+
		"\2\64\u011c\3\2\2\2\66\u0120\3\2\2\28\u0124\3\2\2\2:;\7\35\2\2;<\5\20"+
		"\t\2<\3\3\2\2\2=?\5\6\4\2>=\3\2\2\2>?\3\2\2\2?C\3\2\2\2@B\5\n\6\2A@\3"+
		"\2\2\2BE\3\2\2\2CA\3\2\2\2CD\3\2\2\2DG\3\2\2\2EC\3\2\2\2FH\5\22\n\2GF"+
		"\3\2\2\2HI\3\2\2\2IG\3\2\2\2IJ\3\2\2\2JK\3\2\2\2KL\7\2\2\3L\5\3\2\2\2"+
		"MN\7&\2\2NO\5\b\5\2O\7\3\2\2\2PU\79\2\2QR\7\13\2\2RT\79\2\2SQ\3\2\2\2"+
		"TW\3\2\2\2US\3\2\2\2UV\3\2\2\2V\t\3\2\2\2WU\3\2\2\2XY\7%\2\2Y\\\5\b\5"+
		"\2Z]\5\f\7\2[]\5\16\b\2\\Z\3\2\2\2\\[\3\2\2\2\\]\3\2\2\2]\13\3\2\2\2^"+
		"_\7+\2\2_`\79\2\2`\r\3\2\2\2ab\7\34\2\2bd\79\2\2ca\3\2\2\2de\3\2\2\2e"+
		"c\3\2\2\2ef\3\2\2\2f\17\3\2\2\2gk\79\2\2hk\5\b\5\2ik\7\20\2\2jg\3\2\2"+
		"\2jh\3\2\2\2ji\3\2\2\2ko\3\2\2\2ln\7\24\2\2ml\3\2\2\2nq\3\2\2\2om\3\2"+
		"\2\2op\3\2\2\2p\21\3\2\2\2qo\3\2\2\2rs\5$\23\2st\7!\2\2tv\79\2\2uw\5\24"+
		"\13\2vu\3\2\2\2vw\3\2\2\2wy\3\2\2\2xz\5\26\f\2yx\3\2\2\2yz\3\2\2\2z|\3"+
		"\2\2\2{}\5\30\r\2|{\3\2\2\2|}\3\2\2\2}\23\3\2\2\2~\177\7\26\2\2\177\u0084"+
		"\5\"\22\2\u0080\u0081\7\34\2\2\u0081\u0083\5\"\22\2\u0082\u0080\3\2\2"+
		"\2\u0083\u0086\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0087"+
		"\3\2\2\2\u0086\u0084\3\2\2\2\u0087\u0088\7\27\2\2\u0088\25\3\2\2\2\u0089"+
		"\u008a\7\35\2\2\u008a\u008f\5\b\5\2\u008b\u008c\7\34\2\2\u008c\u008e\5"+
		"\b\5\2\u008d\u008b\3\2\2\2\u008e\u0091\3\2\2\2\u008f\u008d\3\2\2\2\u008f"+
		"\u0090\3\2\2\2\u0090\27\3\2\2\2\u0091\u008f\3\2\2\2\u0092\u0097\7\30\2"+
		"\2\u0093\u0096\5\32\16\2\u0094\u0096\5\34\17\2\u0095\u0093\3\2\2\2\u0095"+
		"\u0094\3\2\2\2\u0096\u0099\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2"+
		"\2\2\u0098\u009a\3\2\2\2\u0099\u0097\3\2\2\2\u009a\u009b\7\31\2\2\u009b"+
		"\31\3\2\2\2\u009c\u009d\5$\23\2\u009d\u009e\7\36\2\2\u009e\u00a0\79\2"+
		"\2\u009f\u00a1\5\2\2\2\u00a0\u009f\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a4"+
		"\3\2\2\2\u00a2\u00a3\7\t\2\2\u00a3\u00a5\5&\24\2\u00a4\u00a2\3\2\2\2\u00a4"+
		"\u00a5\3\2\2\2\u00a5\33\3\2\2\2\u00a6\u00a7\5$\23\2\u00a7\u00a8\7\37\2"+
		"\2\u00a8\u00aa\79\2\2\u00a9\u00ab\5\24\13\2\u00aa\u00a9\3\2\2\2\u00aa"+
		"\u00ab\3\2\2\2\u00ab\u00ae\3\2\2\2\u00ac\u00ad\7\21\2\2\u00ad\u00af\5"+
		"\20\t\2\u00ae\u00ac\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0"+
		"\u00b1\5\36\20\2\u00b1\35\3\2\2\2\u00b2\u00b9\5 \21\2\u00b3\u00b6\7\t"+
		"\2\2\u00b4\u00b7\5&\24\2\u00b5\u00b7\5(\25\2\u00b6\u00b4\3\2\2\2\u00b6"+
		"\u00b5\3\2\2\2\u00b7\u00b9\3\2\2\2\u00b8\u00b2\3\2\2\2\u00b8\u00b3\3\2"+
		"\2\2\u00b9\37\3\2\2\2\u00ba\u00be\7\30\2\2\u00bb\u00bd\5(\25\2\u00bc\u00bb"+
		"\3\2\2\2\u00bd\u00c0\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf"+
		"\u00c1\3\2\2\2\u00c0\u00be\3\2\2\2\u00c1\u00c2\7\31\2\2\u00c2!\3\2\2\2"+
		"\u00c3\u00c4\79\2\2\u00c4\u00c5\5\2\2\2\u00c5#\3\2\2\2\u00c6\u00c8\7/"+
		"\2\2\u00c7\u00c6\3\2\2\2\u00c8\u00cb\3\2\2\2\u00c9\u00c7\3\2\2\2\u00c9"+
		"\u00ca\3\2\2\2\u00ca%\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cc\u00cd\b\24\1\2"+
		"\u00cd\u00eb\7\b\2\2\u00ce\u00eb\7\7\2\2\u00cf\u00eb\7\5\2\2\u00d0\u00eb"+
		"\7\6\2\2\u00d1\u00eb\7\f\2\2\u00d2\u00eb\7\r\2\2\u00d3\u00eb\7\3\2\2\u00d4"+
		"\u00eb\7\16\2\2\u00d5\u00eb\7\17\2\2\u00d6\u00d7\7\26\2\2\u00d7\u00d8"+
		"\5&\24\2\u00d8\u00d9\7\27\2\2\u00d9\u00eb\3\2\2\2\u00da\u00db\7\23\2\2"+
		"\u00db\u00eb\5&\24\b\u00dc\u00eb\5\64\33\2\u00dd\u00eb\58\35\2\u00de\u00e7"+
		"\7\30\2\2\u00df\u00e4\5&\24\2\u00e0\u00e1\7\34\2\2\u00e1\u00e3\5&\24\2"+
		"\u00e2\u00e0\3\2\2\2\u00e3\u00e6\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5"+
		"\3\2\2\2\u00e5\u00e8\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e7\u00df\3\2\2\2\u00e7"+
		"\u00e8\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00eb\7\31\2\2\u00ea\u00cc\3"+
		"\2\2\2\u00ea\u00ce\3\2\2\2\u00ea\u00cf\3\2\2\2\u00ea\u00d0\3\2\2\2\u00ea"+
		"\u00d1\3\2\2\2\u00ea\u00d2\3\2\2\2\u00ea\u00d3\3\2\2\2\u00ea\u00d4\3\2"+
		"\2\2\u00ea\u00d5\3\2\2\2\u00ea\u00d6\3\2\2\2\u00ea\u00da\3\2\2\2\u00ea"+
		"\u00dc\3\2\2\2\u00ea\u00dd\3\2\2\2\u00ea\u00de\3\2\2\2\u00eb\u00f9\3\2"+
		"\2\2\u00ec\u00ed\f\t\2\2\u00ed\u00ee\7\23\2\2\u00ee\u00f8\5&\24\n\u00ef"+
		"\u00f0\f\3\2\2\u00f0\u00f1\7\n\2\2\u00f1\u00f2\5&\24\2\u00f2\u00f3\7\35"+
		"\2\2\u00f3\u00f4\5&\24\4\u00f4\u00f8\3\2\2\2\u00f5\u00f6\f\7\2\2\u00f6"+
		"\u00f8\7\23\2\2\u00f7\u00ec\3\2\2\2\u00f7\u00ef\3\2\2\2\u00f7\u00f5\3"+
		"\2\2\2\u00f8\u00fb\3\2\2\2\u00f9\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa"+
		"\'\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fc\u0103\5\66\34\2\u00fd\u0103\58\35"+
		"\2\u00fe\u0103\5*\26\2\u00ff\u0103\5\60\31\2\u0100\u0103\5 \21\2\u0101"+
		"\u0103\5\32\16\2\u0102\u00fc\3\2\2\2\u0102\u00fd\3\2\2\2\u0102\u00fe\3"+
		"\2\2\2\u0102\u00ff\3\2\2\2\u0102\u0100\3\2\2\2\u0102\u0101\3\2\2\2\u0103"+
		")\3\2\2\2\u0104\u0105\7\63\2\2\u0105\u0106\5&\24\2\u0106\u0108\5 \21\2"+
		"\u0107\u0109\5,\27\2\u0108\u0107\3\2\2\2\u0108\u0109\3\2\2\2\u0109\u010b"+
		"\3\2\2\2\u010a\u010c\5.\30\2\u010b\u010a\3\2\2\2\u010b\u010c\3\2\2\2\u010c"+
		"+\3\2\2\2\u010d\u010e\7\64\2\2\u010e\u010f\5*\26\2\u010f-\3\2\2\2\u0110"+
		"\u0111\7\64\2\2\u0111\u0112\5 \21\2\u0112/\3\2\2\2\u0113\u0115\7\'\2\2"+
		"\u0114\u0116\5&\24\2\u0115\u0114\3\2\2\2\u0115\u0116\3\2\2\2\u0116\61"+
		"\3\2\2\2\u0117\u011a\7\13\2\2\u0118\u011b\5\64\33\2\u0119\u011b\58\35"+
		"\2\u011a\u0118\3\2\2\2\u011a\u0119\3\2\2\2\u011b\63\3\2\2\2\u011c\u011e"+
		"\79\2\2\u011d\u011f\5\62\32\2\u011e\u011d\3\2\2\2\u011e\u011f\3\2\2\2"+
		"\u011f\65\3\2\2\2\u0120\u0121\5\64\33\2\u0121\u0122\t\2\2\2\u0122\u0123"+
		"\5&\24\2\u0123\67\3\2\2\2\u0124\u0125\79\2\2\u0125\u012e\7\26\2\2\u0126"+
		"\u012b\5&\24\2\u0127\u0128\7\34\2\2\u0128\u012a\5&\24\2\u0129\u0127\3"+
		"\2\2\2\u012a\u012d\3\2\2\2\u012b\u0129\3\2\2\2\u012b\u012c\3\2\2\2\u012c"+
		"\u012f\3\2\2\2\u012d\u012b\3\2\2\2\u012e\u0126\3\2\2\2\u012e\u012f\3\2"+
		"\2\2\u012f\u0130\3\2\2\2\u0130\u0132\7\27\2\2\u0131\u0133\5\62\32\2\u0132"+
		"\u0131\3\2\2\2\u0132\u0133\3\2\2\2\u01339\3\2\2\2\'>CIU\\ejovy|\u0084"+
		"\u008f\u0095\u0097\u00a0\u00a4\u00aa\u00ae\u00b6\u00b8\u00be\u00c9\u00e4"+
		"\u00e7\u00ea\u00f7\u00f9\u0102\u0108\u010b\u0115\u011a\u011e\u012b\u012e"+
		"\u0132";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}