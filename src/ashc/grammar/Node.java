package ashc.grammar;

import static ashc.error.Error.*;
import static ashc.error.Error.EnumError.*;

import java.lang.reflect.*;
import java.util.*;

import ashc.grammar.Lexer.Token;
import ashc.grammar.Node.NodeFuncBlock;
import ashc.load.*;
import ashc.semantics.*;
import ashc.semantics.Member.EnumType;
import ashc.semantics.Member.Field;
import ashc.semantics.Member.Function;
import ashc.semantics.Member.Type;
import ashc.semantics.Member.Variable;
import ashc.semantics.Scope.*;
import ashc.semantics.Semantics.TypeI;
import ashc.util.*;

/**
 * Ash
 *
 * @author samtebbs, 22:26:10 - 20 May 2015
 */
public abstract class Node {

    public int line, column;
    public boolean errored;

    public Node(final int line, final int column) {
	this.line = line;
	this.column = column;
    }

    public Node() {

    }

    public void preAnalyse() {}

    public void analyse() {}

    public static interface IFuncStmt {

    }

    public static interface IExpression {

	TypeI getExprType();

    }

    public static class NodeFile extends Node {

	@Override
	public String toString() {
	    return "NodeFile [pkg=" + pkg + ", imports=" + imports + ", typeDecs=" + typeDecs + "]";
	}

	public NodePackage pkg;
	public LinkedList<NodeImport> imports;
	public LinkedList<NodeTypeDec> typeDecs;

	public NodeFile() {
	    super();
	}

	public NodeFile(final NodePackage pkg, final LinkedList<NodeImport> imports, final LinkedList<NodeTypeDec> typeDecs) {
	    this.pkg = pkg;
	    this.imports = imports;
	    this.typeDecs = typeDecs;
	}

	@Override
	public void preAnalyse() {
	    final QualifiedName name = new QualifiedName("");
	    if (pkg != null) {
		pkg.preAnalyse();
		final NodeQualifiedName pkgName = pkg.qualifiedName;
		for (final String section : pkgName.paths)
		    name.add(section);
	    }
	    Scope.namespace = name;
	    if (imports != null) for (final NodeImport i : imports)
		i.preAnalyse();
	    if (typeDecs != null) for (final NodeTypeDec t : typeDecs)
		t.preAnalyse();
	}

	@Override
	public void analyse() {
	    if (typeDecs != null) for (final NodeTypeDec t : typeDecs)
		t.analyse();
	}

    }

    public static class NodePackage extends Node {

	NodeQualifiedName qualifiedName;

	public NodePackage(final int line, final int column, final NodeQualifiedName qualifiedName) {
	    super(line, column);
	    this.qualifiedName = qualifiedName;
	}

	@Override
	public void preAnalyse() {}

    }

    public static class NodeImport extends Node {

	NodeQualifiedName qualifiedName;

	public NodeImport(final int line, final int column, final NodeQualifiedName qualifiedName) {
	    super(line, column);
	    this.qualifiedName = qualifiedName;
	}

	@Override
	public void preAnalyse() {
	    // Test if the type has already been imported
	    if (Semantics.typeExists(qualifiedName.shortName)) semanticError(line, column, TYPE_ALREADY_IMPORTED, qualifiedName.shortName);
	    else TypeImporter.loadClass(qualifiedName.toString());
	}

    }

    public static abstract class NodeTypeDec extends Node {

	public LinkedList<NodeModifier> mods;
	public NodeTypes types;
	public NodeArgs args;
	public Token id;

	public Type type;

	public NodeTypeDec(final int line, final int column) {
	    super(line, column);
	}

	public NodeTypeDec(final int line, final int column, final LinkedList<NodeModifier> mods) {
	    super(line, column);
	    this.mods = mods;
	}

	public NodeTypeDec(final int line, final int column, final LinkedList<NodeModifier> mods, final NodeTypes types, final NodeArgs args, final Token id) {
	    this(line, column, mods);
	    this.types = types;
	    this.args = args;
	    this.id = id;
	}

	@Override
	public void preAnalyse() {
	    // Ensure that the type being declared doesn't already exist
	    if (Semantics.bindingExists(id.data)) semanticError(this, line, column, TYPE_ALREADY_EXISTS, id.data);
	    final QualifiedName name = Scope.getNamespace();
	    name.add(id.data);

	    int modifiers = 0;
	    if (mods != null) for (final NodeModifier modNode : mods) {
		final int mod = modNode.asInt();
		// Check if the modifier has already been added, else add to
		// "modifiers"
		if (BitOp.and(modifiers, mod)) semanticError(this, line, column, DUPLICATE_MODIFIERS, modNode.mod);
		else modifiers |= mod;
	    }

	    if (types != null) for (final NodeType type : types.types)
		if (type.optional) semanticError(this, line, column, CANNOT_EXTEND_OPTIONAL_TYPE, type.id);
	    type = new Type(name, modifiers, getType());
	    Semantics.addType(type);
	}

	@Override
	public void analyse() {
	    // Ensure the super-types are valid
	    if (types != null) {
		boolean hasSuperClass = false;
		for (final NodeType typeNode : types.types) {
		    final Optional<Type> typeOpt = Semantics.getType(typeNode.id);

		    if (!typeOpt.isPresent()) semanticError(line, column, TYPE_DOES_NOT_EXIST, typeNode.id);
		    else {
			final Type type = typeOpt.get();
			if (type.type == EnumType.CLASS) if (getType() == EnumType.CLASS) {
			    if (hasSuperClass) semanticError(this, line, column, CANNOT_EXTEND_MULTIPLE_CLASSES, typeNode.id);
			    else hasSuperClass = true;
			} else semanticError(this, line, column, CANNOT_EXTEND_TYPE, "an", getType().name().toLowerCase(), "a", "class", typeNode.id);
			if (BitOp.and(type.modifiers, Modifier.FINAL)) semanticError(this, line, column, CANNOT_EXTEND_FINAL_TYPE, typeNode.id);
			if (type.type == EnumType.ENUM && getType() != EnumType.ENUM) semanticError(this, line, column, CANNOT_EXTEND_TYPE, "a", "class", "an", "enum", typeNode.id);
		    }
		    if (!errored) type.supers.add(typeOpt.get());
		}
	    }
	    Semantics.enterType(type);
	}

	protected abstract EnumType getType();

    }

    public static class NodeModifier extends Node {
	public String mod;

	public NodeModifier(final int line, final int column, final String mod) {
	    this.mod = mod;
	}

	public int asInt() {
	    for (final EnumModifier modifier : EnumModifier.values())
		if (modifier.name().equalsIgnoreCase(mod)) return modifier.intVal;
	    return 0;
	}
    }

    public static class NodeClassDec extends NodeTypeDec {

	NodeClassBlock block;

	public NodeClassDec(final int line, final int column) {
	    super(line, column);
	}

	public NodeClassDec(final int line, final int column, final LinkedList<NodeModifier> mods, final NodeTypes types, final NodeArgs args, final Token id, final NodeClassBlock block) {
	    super(line, column, mods, types, args, id);
	    this.block = block;
	}

	@Override
	public void preAnalyse() {
	    super.preAnalyse();
	    block.preAnalyse();
	    Semantics.exitType();
	}

	@Override
	public void analyse() {
	    super.analyse();
	    block.analyse();
	    Semantics.exitType();
	}

	@Override
	protected EnumType getType() {
	    return EnumType.CLASS;
	}
    }

    public static class NodeEnumDec extends NodeTypeDec {

	LinkedList<NodeModifier> mods;
	Token id;
	NodeArgs args;
	NodeEnumBlock block;

	public NodeEnumDec(final int line, final int column, final LinkedList<NodeModifier> mods, final Token id, final NodeArgs args, final NodeEnumBlock block) {
	    super(line, column);
	    this.mods = mods;
	    this.args = args;
	    this.id = id;
	    this.block = block;
	}

	@Override
	public void preAnalyse() {
	    super.preAnalyse();
	    block.preAnalyse();
	    Semantics.exitType();
	}

	@Override
	protected EnumType getType() {
	    return EnumType.ENUM;
	}

    }

    public static class NodeInterfaceDec extends NodeTypeDec {

	NodeClassBlock block;

	public NodeInterfaceDec(final int line, final int column, final LinkedList<NodeModifier> mods, final NodeTypes types, final NodeArgs args, final Token id, final NodeClassBlock block) {
	    super(line, column, mods, types, args, id);
	    this.block = block;
	}

	@Override
	public void preAnalyse() {
	    super.preAnalyse();
	    block.preAnalyse();
	    Semantics.exitType();
	}

	@Override
	protected EnumType getType() {
	    return EnumType.INTERFACE;
	}

	@Override
	public void analyse() {
	    super.analyse();
	    block.analyse();
	    Semantics.exitType();
	}

    }

    public static class NodeArg extends Node {
	public String id;
	public NodeType type;
	public IExpression defExpr;

	public NodeArg(final int line, final int column, final String id, final NodeType type, IExpression defExpr) {
	    super(line, column);
	    this.id = id;
	    this.type = type;
	    this.defExpr = defExpr;
	}

	@Override
	public void analyse() {
	    type.analyse();
	}

    }

    public static class NodeArgs extends Node {
	public LinkedList<NodeArg> args = new LinkedList<NodeArg>();

	public void add(final NodeArg arg) {
	    args.add(arg);
	}

	@Override
	public void analyse() {
	    for (int i = 0; i < args.size(); i++) {
		boolean hasDupes = false;
		for (int j = 0; j < args.size(); j++)
		    if (i != j) if (args.get(i).id.equals(args.get(j).id)) {
			hasDupes = true;
			semanticError(this, line, column, DUPLICATE_ARGUMENTS, args.get(i).id);
			break;
		    }
		if (hasDupes) continue;
	    }
	}
    }

    public static class NodeTypes extends Node {
	LinkedList<NodeType> types = new LinkedList<Node.NodeType>();

	public void add(final NodeType type) {
	    types.add(type);
	}

	@Override
	public void analyse() {
	    for (final NodeType type : types) {
		type.analyse();
		for (final NodeType type2 : types)
		    if (type.id.equals(type2.id)) semanticError(this, line, column, DUPLICATE_TYPES, type.id);
	    }
	}

    }

    public static class NodeClassBlock extends Node {
	public LinkedList<NodeFuncDec> funcDecs = new LinkedList<NodeFuncDec>();
	public LinkedList<NodeVarDec> varDecs = new LinkedList<NodeVarDec>();

	public void add(final NodeVarDec parseVarDec) {
	    varDecs.add(parseVarDec);
	}

	public void add(final NodeFuncDec funcDec) {
	    funcDecs.add(funcDec);
	}

	@Override
	public void preAnalyse() {
	    for (final NodeFuncDec funcDec : funcDecs)
		funcDec.preAnalyse();
	    for (final NodeVarDec varDec : varDecs)
		varDec.preAnalyse();
	}

	@Override
	public void analyse() {
	    for (final NodeVarDec varDec : varDecs)
		varDec.analyse();
	    for (final NodeFuncDec funcDec : funcDecs)
		funcDec.analyse();
	}

    }

    public static class NodeEnumBlock extends Node {
	public LinkedList<NodeEnumInstance> instances;
	public NodeClassBlock block;

	public NodeEnumBlock(final int line, final int column, final LinkedList<NodeEnumInstance> instances, final NodeClassBlock block) {
	    super(line, column);
	    this.instances = instances;
	    this.block = block;
	}

    }

    public static class NodeEnumInstance extends Node {
	public NodeExprs exprs;
	public String id;

	public NodeEnumInstance(final int line, final int column, final Token id, final NodeExprs exprs) {
	    super(line, column);
	    this.exprs = exprs;
	    this.id = id.data;
	}
    }

    public static class NodeType extends Node {

	public String id;
	public int arrDims;
	public boolean optional;
	public LinkedList<NodeType> tupleTypes = new LinkedList<NodeType>();

	public NodeType(final String data) {
	    id = data;
	}

	public NodeType() {}

	@Override
	public void analyse() {
	    if(tupleTypes.size() == 0){
		if (!Semantics.typeExists(id)) semanticError(this, line, column, TYPE_DOES_NOT_EXIST, id);
		if (EnumPrimitive.isPrimitive(id) && optional) semanticError(this, line, column, PRIMTIVE_CANNOT_BE_OPTIONAL, id);
	    }else for(NodeType typeNode : tupleTypes) typeNode.analyse();
	}

    }

    public static class NodeQualifiedName extends Node {

	LinkedList<String> paths = new LinkedList<String>();
	public String shortName;

	public NodeQualifiedName(final int line, final int column) {
	    super(line, column);
	}

	public String toDirString() {
	    final StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < paths.size() - 1; i++)
		sb.append(paths.get(i) + "/");
	    sb.append(paths.get(paths.size() - 1));
	    return sb.toString();
	}

	@Override
	public String toString() {
	    final StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < paths.size() - 1; i++)
		sb.append(paths.get(i) + ".");
	    sb.append(paths.get(paths.size() - 1));
	    return sb.toString();
	}

	public void add(final String data) {
	    paths.add(data);
	    shortName = data;
	}

    }

    public static class NodeFuncDec extends Node {

	public LinkedList<NodeModifier> mods;
	public String id;
	public NodeArgs args;
	public NodeType type, throwsType;
	public NodeFuncBlock block;

	public NodeFuncDec(final int line, final int column, final LinkedList<NodeModifier> mods, final String id, final NodeArgs args, final NodeType type, final NodeType throwsType, final NodeFuncBlock block) {
	    super(line, column);
	    this.mods = mods;
	    this.id = id;
	    this.args = args;
	    this.type = type;
	    this.throwsType = throwsType;
	    this.block = block;
	}

	@Override
	public void preAnalyse() {
	    final QualifiedName name = Scope.getNamespace().copy();
	    name.add(id);
	    int modifiers = 0;
	    for (final NodeModifier mod : mods)
		modifiers |= mod.asInt();
	    final Function func = new Function(name, modifiers);
	    func.returnType = type != null ? new TypeI(type) : null;
	    for (final NodeArg arg : args.args)
		func.parameters.add(new TypeI(arg.type));
	    if (!Semantics.funcExists(func)) Semantics.addFunc(func);
	    else semanticError(this, line, column, FUNC_ALREADY_EXISTS, id);
	}

	@Override
	public void analyse() {
	    args.analyse();
	    type.analyse();
	    if (throwsType != null) {
		throwsType.analyse();
		final Optional<Type> type = Semantics.getType(throwsType.id);
		if (type.isPresent()) if (!type.get().hasSuper(new QualifiedName("").add("java").add("lang").add("Throwable"))) semanticError(this, line, column, TYPE_DOES_NOT_EXTEND, throwsType.id, "java.lang.Throwable");
	    }
	    Scope.push(new FuncScope(new TypeI(type)));
	    for(NodeArg arg : args.args) Scope.getScope().addVar(new Variable(arg.id, new TypeI(arg.type)));
	    block.analyse();
	    Scope.pop();
	}

    }

    public static class NodeVarDec extends Node implements IFuncStmt {
	public LinkedList<NodeModifier> mods;
	public String keyword;
	public String id;
	public NodeFuncBlock getBlock, setBlock;

	public NodeVarDec(final int line, final int column, final LinkedList<NodeModifier> mods, final String keyword, final String id) {
	    super(line, column);
	    this.mods = mods;
	    this.keyword = keyword;
	    this.id = id;
	}

	@Override
	public void analyse() {
	    // Only check if this var exists if we're in a scope, since variable declarations in types are already handled
	    if (Scope.getScope() != null && Semantics.varExists(id)) semanticError(this, line, column, VAR_ALREADY_EXISTS, id);
	}

    }

    public static class NodeVarDecExplicit extends NodeVarDec {
	public NodeType type;
	public IExpression expr;

	public TypeI typeI;

	public NodeVarDecExplicit(final int line, final int column, final LinkedList<NodeModifier> mods, final String keyword, final String id, final NodeType type, final IExpression expr) {
	    super(line, column, mods, keyword, id);
	    this.type = type;
	    this.expr = expr;
	}

	@Override
	public void preAnalyse() {
	    final QualifiedName name = Semantics.typeStack.peek().qualifiedName.copy();
	    name.add(id);
	    int modifiers = 0;
	    for (final NodeModifier mod : mods)
		modifiers |= mod.asInt();
	    final Field field = new Field(name, modifiers, new TypeI(type));
	    if (!Semantics.fieldExists(field)) Semantics.addField(field);
	    else semanticError(this, line, column, FIELD_ALREADY_EXISTS, id);
	}

	@Override
	public void analyse() {
	    super.analyse();
	    type.analyse();
	    if (expr != null) ((Node) expr).analyse();
	    typeI = new TypeI(type);
	    if (!errored && Scope.getScope() != null) Scope.getScope().addVar(new Variable(id, typeI));

	    if (expr == null) {
		if (!type.optional && !(EnumPrimitive.isPrimitive(type.id) && type.arrDims == 0)) semanticError(this, line, column, MISSING_ASSIGNMENT);
	    } else {
		final TypeI exprType = expr.getExprType();
		if (exprType != null) if (!typeI.canBeAssignedTo(exprType)) semanticError(this, line, column, CANNOT_ASSIGN, exprType, typeI.toString());
	    }
	    Semantics.addVar(new Variable(id, typeI));
	}

    }

    public static class NodeVarDecImplicit extends NodeVarDec {
	public IExpression expr;

	public NodeVarDecImplicit(final int line, final int column, final LinkedList<NodeModifier> mods, final String keyword, final String id, final IExpression expr) {
	    super(line, column, mods, keyword, id);
	    this.expr = expr;
	}

	@Override
	public void preAnalyse() {
	    final QualifiedName name = Semantics.typeStack.peek().qualifiedName.copy();
	    name.add(id);
	    int modifiers = 0;
	    for (final NodeModifier mod : mods)
		modifiers |= mod.asInt();
	    final Field field = new Field(name, modifiers, expr.getExprType());
	    if (!Semantics.fieldExists(field)) Semantics.addField(field);
	    else semanticError(this, line, column, FIELD_ALREADY_EXISTS, id);
	}

	@Override
	public void analyse() {
	    super.analyse();
	    if (expr != null) ((Node) expr).analyse();
	    if (!errored) Semantics.addVar(new Variable(id, expr.getExprType()));
	}

    }

    public static class NodeFuncBlock extends Node {

	public IExpression singleLineExpr;
	LinkedList<IFuncStmt> stmts = new LinkedList<IFuncStmt>();

	public void add(final IFuncStmt funcStmt) {
	    stmts.add(funcStmt);
	}

	@Override
	public void analyse() {
	    if (singleLineExpr != null) {
		final FuncScope scope = (FuncScope) Scope.getScope();
		final TypeI exprType = singleLineExpr.getExprType();
		if (scope.returnType.isVoid()) semanticError(this, line, column, RETURN_EXPR_IN_VOID_FUNC);
		else if (!scope.returnType.canBeAssignedTo(exprType)) semanticError(this, line, column, CANNOT_ASSIGN, scope.returnType.toString(), exprType.toString());
	    }
	    for (final IFuncStmt stmt : stmts)
		((Node) stmt).analyse();
	}

    }

    public static class NodePrefix extends Node implements IFuncStmt,
	    IExpression {

	public NodePrefix(final int line, final int column) {
	    super(line, column);
	}

	@Override
	public TypeI getExprType() {
	    return null;
	}

    }

    public static class NodeExprs extends Node {
	public LinkedList<IExpression> exprs = new LinkedList<IExpression>();

	public void add(final IExpression expr) {
	    exprs.add(expr);
	}

	@Override
	public void analyse() {
	    for (final IExpression expr : exprs)
		((Node) expr).analyse();
	}

    }

    public static class NodeFuncCall extends NodePrefix {
	public String id;
	public NodeExprs args;
	public NodePrefix prefix;

	public NodeFuncCall(final int line, final int column, final String id, final NodeExprs args, final NodePrefix prefix) {
	    super(line, column);
	    this.id = id;
	    this.args = args;
	    this.prefix = prefix;
	}

	@Override
	public String toString() {
	    return "NodeFuncCall [id=" + id + ", args=" + args + ", prefix=" + prefix + "]";
	}

	@Override
	public TypeI getExprType() {
	    if (prefix == null) return Semantics.getFuncType(id, args);
	    else {
		final TypeI type = prefix.getExprType();
		return Semantics.getFuncType(id, type, args);
	    }
	}

	@Override
	public void analyse() {
	    Function func = null;
	    args.analyse();
	    if (prefix == null) func = Semantics.getFunc(id, args);
	    else func = Semantics.getFunc(id, prefix.getExprType(), args);
	    if (func == null) semanticError(this, line, column, FUNC_DOES_NOT_EXIST, id);
	}

    }

    public static class NodeVariable extends NodePrefix {

	public String id;
	public NodePrefix prefix;
	public NodeExprs exprs = new NodeExprs();

	public NodeVariable(final int line, final int column, final String id, final NodePrefix prefix) {
	    super(line, column);
	    this.id = id;
	    this.prefix = prefix;
	}

	@Override
	public String toString() {
	    return "NodeVariable [id=" + id + ", prefix=" + prefix + "]";
	}

	@Override
	public TypeI getExprType() {
	    Variable var = null;
	    if (prefix == null) {
		final Optional<Type> type = Semantics.getType(id);
		if (type.isPresent()) return new TypeI(type.get().qualifiedName.shortName, 0, false);
		else var = Semantics.getVar(id);
	    } else {
		final TypeI type = prefix.getExprType();
		var = Semantics.getVar(id, type);
	    }
	    if (var == null) {
		semanticError(this, line, column, VAR_DOES_NOT_EXIST, id);
		return null;
	    } else return var.type;
	}

	@Override
	public void analyse() {
	    Variable var = null;
	    if (prefix == null) var = Semantics.getVar(id);
	    else {
		final TypeI type = prefix.getExprType();
		var = Semantics.getVar(id, type);
	    }
	    if (var != null) {
		final TypeI varType = var.type;
		if (exprs.exprs.size() > varType.arrDims) semanticError(this, line, column, TOO_MANY_ARRAY_ACCESSES, exprs.exprs.size() + "", varType.arrDims + "", id);
		if (!errored) for (final IExpression expr : exprs.exprs) {
		    final TypeI indexType = expr.getExprType();
		    if (!EnumPrimitive.validForArrayIndex(indexType)) semanticError(this, line, column, ARRAY_INDEX_NOT_NUMERIC, indexType.toString());
		}
	    }
	}

    }

    public static class NodeVarAssign extends Node implements IFuncStmt {
	public NodeVariable var;
	public String assignOp;
	public IExpression expr;

	public NodeVarAssign(final int line, final int column, final NodeVariable var, final String assignOp, final IExpression expr) {
	    super(line, column);
	    this.var = var;
	    this.assignOp = assignOp;
	    this.expr = expr;
	}
    }

    public static class NodeReturn extends Node implements IFuncStmt {
	public IExpression expr;

	public NodeReturn(final int line, final int column, final IExpression expr) {
	    super(line, column);
	    this.expr = expr;
	}

    }

    public static class NodeInteger extends Node implements IExpression {
	public int val;

	public NodeInteger(final int val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeInteger [val=" + val + "]";
	}

	@Override
	public TypeI getExprType() {
	    return new TypeI(EnumPrimitive.INT);
	}

    }

    public static class NodeLong extends Node implements IExpression {
	public long val;

	public NodeLong(final long val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeLong [val=" + val + "]";
	}

	@Override
	public TypeI getExprType() {
	    return new TypeI(EnumPrimitive.LONG);
	}

    }

    public static class NodeFloat extends Node implements IExpression {
	public float val;

	public NodeFloat(final float val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeFloat [val=" + val + "]";
	}

	@Override
	public TypeI getExprType() {
	    return new TypeI(EnumPrimitive.FLOAT);
	}

    }

    public static class NodeDouble extends Node implements IExpression {
	public double val;

	public NodeDouble(final double val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeDouble [val=" + val + "]";
	}

	@Override
	public TypeI getExprType() {
	    return new TypeI(EnumPrimitive.DOUBLE);
	}

    }

    public static class NodeString extends Node implements IExpression {
	public String val;

	public NodeString(final String val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeString [val=" + val + "]";
	}

	@Override
	public TypeI getExprType() {
	    return new TypeI("String", 0, false);
	}

    }

    public static class NodeBool extends Node implements IExpression {
	public boolean val;

	public NodeBool(final boolean val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeBool [val=" + val + "]";
	}

	@Override
	public TypeI getExprType() {
	    return new TypeI(EnumPrimitive.BOOL);
	}

    }

    public static class NodeChar extends Node implements IExpression {
	public char val;

	public NodeChar(final char val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeChar [val=" + val + "]";
	}

	@Override
	public TypeI getExprType() {
	    return new TypeI(EnumPrimitive.CHAR);
	}

    }

    public static class NodeTernary extends Node implements IExpression {
	public IExpression expr, exprTrue, exprFalse;

	public NodeTernary(final IExpression expr, final IExpression exprTrue, final IExpression exprFalse) {
	    this.expr = expr;
	    this.exprTrue = exprTrue;
	    this.exprFalse = exprFalse;
	}

	@Override
	public String toString() {
	    return "NodeTernary [expr=" + expr + ", exprTrue=" + exprTrue + ", exprFalse=" + exprFalse + "]";
	}

	@Override
	public TypeI getExprType() {
	    return Semantics.getPrecedentType(exprTrue.getExprType(), exprFalse.getExprType());
	}

    }

    public static class NodeBinary extends Node implements IExpression {
	public IExpression expr1, expr2;
	public Operator operator;

	public NodeBinary(final IExpression expr1, final String operator, final IExpression expr2) {
	    this.expr1 = expr1;
	    this.expr2 = expr2;
	    this.operator = new Operator(operator);
	}

	@Override
	public String toString() {
	    return "NodeBinary [expr1=" + expr1 + ", expr2=" + expr2 + ", operator=" + operator + "]";
	}

	@Override
	public TypeI getExprType() {
	    return operator.operation.primitive == null ? Semantics.getPrecedentType(expr1.getExprType(), expr2.getExprType()) : new TypeI(operator.operation.primitive);
	}
    }

    public static class NodeUnary extends Node implements IExpression {
	public IExpression expr;
	public Operator operator;
	public boolean prefix;

	public NodeUnary(final int line, final int column, final IExpression expr, final String operator, final boolean prefix) {
	    super(line, column);
	    this.expr = expr;
	    this.operator = new Operator(operator);
	    this.prefix = prefix;
	}

	@Override
	public String toString() {
	    return "NodeUnary [expr=" + expr + ", operator=" + operator + ", prefix=" + prefix + "]";
	}

	@Override
	public TypeI getExprType() {
	    return expr.getExprType();
	}

    }

    public static class NodeThis extends Node implements IExpression {
	public NodeThis(final int line, final int column) {
	    super(line, column);
	}

	@Override
	public TypeI getExprType() {
	    return new TypeI(Semantics.typeStack.peek().qualifiedName.shortName, 0, false);
	}
    }
    
    public static class NodeSelf extends Node implements IExpression {

	public NodeSelf(int line, int columnStart) {
	    super(line, columnStart);
	}

	@Override
	public TypeI getExprType() {
	    if(Scope.getScope() instanceof PropertyScope) return ((PropertyScope)Scope.getScope()).varType;
	    else semanticError(this, line, column, CANNOT_USE_SELF);
	    return null;
	}
	
    }

    public static class NodeNull extends Node implements IExpression {

	@Override
	public TypeI getExprType() {
	    return new TypeI("null", 0, true);
	}

    }
    
    public static class NodeArray extends Node implements IExpression {
	
	public NodeExprs exprs = new NodeExprs();

	public NodeArray(int line, int column) {
	    super(line, column);
	}
	
	public void add(IExpression expr){
	    exprs.add(expr);
	}

	@Override
	public TypeI getExprType() {
	    LinkedList<IExpression> exprList = exprs.exprs;
	    // Just infer an Object array
	    if(exprList.size() == 0){
		return new TypeI("Object", 1, false);
	    }
	    // Infer the precedent type within this array expression
	    TypeI type = exprList.getFirst().getExprType();
	    for(int i = 1; i < exprList.size(); i++) type = Semantics.getPrecedentType(type, exprList.get(i).getExprType());
	    type.arrDims += 1; // Add one to show that this is an array expression
	    return type;
	}
	
    }
    
    public static class NodeTupleExpr extends Node implements IExpression {
	
	public NodeExprs exprs = new NodeExprs();

	public NodeTupleExpr(int line, int column) {
	    super(line, column);
	}
	
	public void add(IExpression expr){
	    exprs.add(expr);
	}

	@Override
	public TypeI getExprType() {
	    LinkedList<IExpression> exprList = exprs.exprs;
	    // Just infer an Object array
	    if(exprList.size() == 0){
		return new TypeI("Object", 1, false);
	    }
	    TypeI type = new TypeI("", 0, false);
	    for(IExpression expr : exprList) type.tupleTypes.add(expr.getExprType());
	    return type;
	}
	
    }

}
