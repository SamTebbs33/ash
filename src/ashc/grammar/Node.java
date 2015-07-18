package ashc.grammar;

import static ashc.codegen.GenNode.*;
import static ashc.error.AshError.*;
import static ashc.error.AshError.EnumError.*;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import org.objectweb.asm.*;

import ashc.codegen.*;
import ashc.codegen.GenNode.EnumInstructionOperand;
import ashc.codegen.GenNode.GenNodeArrayIndexLoad;
import ashc.codegen.GenNode.GenNodeBinary;
import ashc.codegen.GenNode.GenNodeConditionalJump;
import ashc.codegen.GenNode.GenNodeDouble;
import ashc.codegen.GenNode.GenNodeField;
import ashc.codegen.GenNode.GenNodeFieldLoad;
import ashc.codegen.GenNode.GenNodeFieldStore;
import ashc.codegen.GenNode.GenNodeFloat;
import ashc.codegen.GenNode.GenNodeFuncCall;
import ashc.codegen.GenNode.GenNodeFunction;
import ashc.codegen.GenNode.GenNodeIncrement;
import ashc.codegen.GenNode.GenNodeInt;
import ashc.codegen.GenNode.GenNodeIntOpcode;
import ashc.codegen.GenNode.GenNodeJump;
import ashc.codegen.GenNode.GenNodeLabel;
import ashc.codegen.GenNode.GenNodeLong;
import ashc.codegen.GenNode.GenNodeNew;
import ashc.codegen.GenNode.GenNodeNull;
import ashc.codegen.GenNode.GenNodeOpcode;
import ashc.codegen.GenNode.GenNodePrimitiveCast;
import ashc.codegen.GenNode.GenNodeReturn;
import ashc.codegen.GenNode.GenNodeString;
import ashc.codegen.GenNode.GenNodeThis;
import ashc.codegen.GenNode.GenNodeType;
import ashc.codegen.GenNode.GenNodeTypeOpcode;
import ashc.codegen.GenNode.GenNodeUnary;
import ashc.codegen.GenNode.GenNodeVarLoad;
import ashc.codegen.GenNode.GenNodeVarStore;
import ashc.codegen.GenNode.IGenNodeStmt;
import ashc.grammar.Lexer.Token;
import ashc.grammar.Lexer.UnexpectedTokenException;
import ashc.load.*;
import ashc.main.*;
import ashc.semantics.*;
import ashc.semantics.Member.EnumType;
import ashc.semantics.Member.Field;
import ashc.semantics.Member.Function;
import ashc.semantics.Member.Type;
import ashc.semantics.Member.Variable;
import ashc.semantics.Scope.FuncScope;
import ashc.semantics.Scope.PropertyScope;
import ashc.semantics.Semantics.Operation;
import ashc.semantics.Semantics.TypeI;
import ashc.semantics.Member.Type;
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

    public abstract void generate();

    public static interface IFuncStmt {
	public IGenNodeStmt genStmt = null;

	void generate();

	void analyse();
    }

    public static interface IConstruct {
	public boolean hasReturnStmt();
    }

    public static interface IExpression {
	public TypeI getExprType();

	public void registerScopedChecks();

	public void generate();

	public void analyse();
    }

    public static class NodeFile extends Node {

	@Override
	public String toString() {
	    return "NodeFile [pkg=" + pkg + ", imports=" + imports + ", typeDecs=" + typeDecs + "]";
	}

	public NodePackage pkg;
	public LinkedList<NodeImport> imports;
	public LinkedList<NodeTypeDec> typeDecs;
	public LinkedList<NodeAlias> aliases;

	public NodeFile(final NodePackage pkg2, final LinkedList<NodeImport> imports2, final LinkedList<NodeTypeDec> typeDecs2) {
	    super();
	}

	public NodeFile(final NodePackage pkg, final LinkedList<NodeImport> imports, final LinkedList<NodeAlias> aliases, final LinkedList<NodeTypeDec> typeDecs) {
	    this.pkg = pkg;
	    this.imports = imports;
	    this.typeDecs = typeDecs;
	    this.aliases = aliases;
	}

	@Override
	public void preAnalyse() {
	    final QualifiedName name = new QualifiedName("");
	    String packagePath = "";
	    if (pkg != null) {
		pkg.preAnalyse();
		final NodeQualifiedName pkgName = pkg.qualifiedName;
		for (final String section : pkgName.paths)
		    name.add(section);
		packagePath = name.toString().replace('.', File.separatorChar);
	    }
	    if(!AshCompiler.get().parentPath.equals(packagePath)) semanticError(this, -1, -1, PATH_DOES_NOT_MATCH_PACKAGE, packagePath);
	    Scope.setNamespace(name);
	    if (imports != null) for (final NodeImport i : imports)
		i.preAnalyse();
	    if (typeDecs != null) for (final NodeTypeDec t : typeDecs)
		t.preAnalyse();
	    if (aliases != null) for (final NodeAlias a : aliases)
		a.preAnalyse();
	}

	@Override
	public void analyse() {
	    if (typeDecs != null) for (final NodeTypeDec t : typeDecs)
		t.analyse();
	}

	@Override
	public void generate() {
	    for (final NodeTypeDec dec : typeDecs)
		dec.generate();
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

	@Override
	public void generate() {}

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

	@Override
	public void generate() {}

    }

    public static abstract class NodeTypeDec extends Node {

	public LinkedList<NodeModifier> mods;
	public NodeTypes types;
	public NodeArgs args;
	public Token id;
	public NodeTypes generics;

	public Type type;
	public LinkedList<Field> argFields = new LinkedList<Field>();
	public Function defConstructor;
	public GenNodeType genNodeType;

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
	    final QualifiedName name = Scope.getNamespace().copy();
	    name.add(id.data);

	    int modifiers = 0;
	    if (mods != null) for (final NodeModifier modNode : mods) {
		final int mod = modNode.asInt();
		// Check if the modifier has already been added, else add to
		// "modifiers"
		if (BitOp.and(modifiers, mod)) semanticError(this, line, column, DUPLICATE_MODIFIERS, modNode.mod);
		else modifiers |= mod;
	    }
	    type = new Type(name, modifiers, getType());

	    if (types != null) for (final NodeType nodeType : types.types) {
		if (nodeType.optional) semanticError(this, line, column, CANNOT_EXTEND_OPTIONAL_TYPE, nodeType.id);
		for (final NodeType generic : nodeType.generics.types)
		    type.addGeneric(nodeType.id, new TypeI(generic));
	    }
	    for (final NodeType generic : generics.types)
		type.generics.add(generic.id);

	    Semantics.addType(type);

	    // Create the default constructor and add fields supplied by the
	    // arguments
	    if (args != null) {
		defConstructor = new Function(Scope.getNamespace().copy().add(id.data), EnumModifier.PUBLIC.intVal);
		defConstructor.returnType = new TypeI(name.shortName, 0, false);
		for (final NodeType generic : generics.types)
		    defConstructor.generics.add(generic.id);
		for (final NodeArg arg : args.args) {
		    arg.preAnalyse();
		    if (!arg.errored) {
			final TypeI argType = new TypeI(arg.type);
			final Field field = new Field(Scope.getNamespace().copy().add(arg.id), EnumModifier.PUBLIC.intVal, argType, false, false);
			type.fields.add(field);
			argFields.add(field);
			defConstructor.parameters.add(argType);
		    }
		}
		type.addFunction(defConstructor);
	    }

	}

	@Override
	public void analyse() {
	    // Ensure the super-types are valid

	    if (types != null) {
		types.analyse();
		boolean hasSuperClass = false;
		for (final NodeType typeNode : types.types) {
		    final Optional<Type> typeOpt = Semantics.getType(typeNode.id);

		    if (!typeOpt.isPresent()) semanticError(line, column, TYPE_DOES_NOT_EXIST, typeNode.id);
		    else {
			final Type type = typeOpt.get();
			if (type.type == EnumType.CLASS) {
			    if (getType() == EnumType.CLASS) if (hasSuperClass) semanticError(this, line, column, CANNOT_EXTEND_MULTIPLE_CLASSES, typeNode.id);
			    else hasSuperClass = true;
			} else semanticError(this, line, column, CANNOT_EXTEND_TYPE, "an", getType().name().toLowerCase(), "a", "class", typeNode.id);
			if (BitOp.and(type.modifiers, Modifier.FINAL)) semanticError(this, line, column, CANNOT_EXTEND_FINAL_TYPE, typeNode.id);
			if ((type.type == EnumType.ENUM) && (getType() != EnumType.ENUM)) semanticError(this, line, column, CANNOT_EXTEND_TYPE, "a", "class", "an", "enum", typeNode.id);
		    }
		    if (!errored) type.supers.add(typeOpt.get());
		}
		if (!hasSuperClass) type.supers.addFirst(Semantics.getType("Object").get());

	    } else type.supers.addFirst(Semantics.getType("Object").get());
	    Semantics.enterType(type);
	}

	@Override
	public void generate() {
	    final String name = type.qualifiedName.toString();
	    final String superClass = type.supers.getFirst().qualifiedName.toBytecodeName();

	    // Build the interfaces array
	    String[] interfaces = null;
	    if (type.supers.size() > 1) {
		interfaces = new String[type.supers.size() - 1];
		for (int i = 1; i < type.supers.size(); i++)
		    interfaces[i - 1] = type.supers.get(i).qualifiedName.toString();
	    }
	    genNodeType = new GenNodeType(name, type.qualifiedName.shortName, superClass, interfaces, type.modifiers);
	    GenNode.addGenNodeType(genNodeType);

	    // Build the costructor
	    if (defConstructor != null) {
		final GenNodeFunction func = new GenNodeFunction("<init>", defConstructor.modifiers, "V");
		GenNode.addGenNodeFunction(func);
		func.params = defConstructor.parameters;
		int argID = 1;
		// Call the super class' constructor
		// TODO: Change so it calls the right constructor for classes that don't extend Object
		GenNode.addFuncStmt(new GenNodeThis());
		GenNode.addFuncStmt(new GenNodeFuncCall(superClass, "<init>", "()V", false, false, false, true));
		for (final Field field : argFields) {
		    genNodeType.addField(new GenNodeField(field));
		    GenNode.addFuncStmt(new GenNodeThis());
		    addFuncStmt(new GenNodeVar(field.id, field.type.toBytecodeName(), argID, null));
		    GenNode.addFuncStmt(new GenNodeVarLoad(field.type.getInstructionType(), argID));
		    GenNode.addFuncStmt(new GenNodeFieldStore(field.qualifiedName.shortName, field.enclosingType.qualifiedName.toBytecodeName(), field.type.toBytecodeName(), field.isStatic()));
		    argID++;
		}
		GenNode.addFuncStmt(new GenNodeReturn(EnumInstructionOperand.VOID));
		GenNode.exitGenNodeFunction();
	    }
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

	@Override
	public void generate() {}
    }

    public static class NodeClassDec extends NodeTypeDec {

	public NodeClassBlock block;

	public NodeClassDec(final int line, final int column) {
	    super(line, column);
	}

	public NodeClassDec(final int line, final int column, final LinkedList<NodeModifier> mods, final NodeTypes types, final NodeArgs args, final Token id, final NodeClassBlock block, final NodeTypes generics) {
	    super(line, column, mods, types, args, id);
	    this.block = block;
	    this.generics = generics;
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
	public void generate() {
	    super.generate();
	    block.generate();
	    GenNode.exitGenNodeType();
	}

	@Override
	protected EnumType getType() {
	    return EnumType.CLASS;
	}
    }

    public static class NodeEnumDec extends NodeTypeDec {

	// Code-gen:
	// http://osdir.com/ml/java.objectweb.asm/2008-12/msg00036.html

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

	public NodeArg(final int line, final int column, final String id, final NodeType type, final IExpression defExpr) {
	    super(line, column);
	    this.id = id;
	    this.type = type;
	    this.defExpr = defExpr;
	}

	@Override
	public void analyse() {
	    type.analyse();
	}

	@Override
	public void generate() {}

    }

    public static class NodeArgs extends Node {
	public LinkedList<NodeArg> args = new LinkedList<NodeArg>();
	public boolean hasDefExpr;

	public void add(final NodeArg arg) {
	    args.add(arg);
	}

	@Override
	public void preAnalyse() {
	    for (final NodeArg arg : args)
		if (arg.defExpr != null) {
		    hasDefExpr = true;
		    break;
		}
	}

	@Override
	public void analyse() {
	    final int size = args.size();
	    for (int i = 0; i < size; i++) {
		boolean hasDupes = false;
		final NodeArg arg1 = args.get(i);
		// Only the last parameter can have a default value
		if (arg1.defExpr != null) if (i != (size - 1)) semanticError(this, arg1.line, arg1.column, PARAM_DEF_EXPR_NOT_LAST);
		for (int j = i + 1; j < size; j++) {
		    final NodeArg arg2 = args.get(j);
		    if (arg1.id.equals(arg2.id)) {
			hasDupes = true;
			semanticError(this, line, column, DUPLICATE_ARGUMENTS, arg1.id);
			break;
		    }
		}
		if (hasDupes) continue;
	    }
	}

	@Override
	public void generate() {}
    }

    public static class NodeTypes extends Node {
	public LinkedList<NodeType> types = new LinkedList<Node.NodeType>();

	public void add(final NodeType type) {
	    types.add(type);
	}

	@Override
	public void analyse() {
	    int i = 0;
	    for (final NodeType type : types) {
		type.analyse();
		int j = 0;
		for (final NodeType type2 : types)
		    if ((i++ != j++) && type.id.equals(type2.id)) semanticError(this, line, column, DUPLICATE_TYPES, type.id);
	    }
	}

	@Override
	public void generate() {}

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
	    /*boolean hasConstructor = false;
	    for (final NodeFuncDec funcDec : funcDecs) {
		funcDec.preAnalyse();
		if (funcDec.isConstructor) hasConstructor = true;
	    }
	    if (!hasConstructor) {
		// Give the class a default constructor if a constructor is not
		// already declared
		final LinkedList<NodeModifier> mods = new LinkedList<NodeModifier>();
		mods.add(new NodeModifier(0, 0, "public"));
		funcDecs.add(new NodeFuncDec(0, 0, mods, Semantics.currentType().qualifiedName.shortName, new NodeArgs(), null, new NodeFuncBlock(), new NodeTypes(), false));
		funcDecs.getLast().preAnalyse();
	    }*/
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

	@Override
	public void generate() {
	    for (final NodeFuncDec funcDec : funcDecs) {
		if (!funcDec.isConstructor) funcDec.generate();
		else funcDec.generateConstructor(varDecs);
	    }
	    for (final NodeVarDec dec : varDecs) dec.generate();
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

	@Override
	public void generate() {}

    }

    public static class NodeEnumInstance extends Node {
	public NodeExprs exprs;
	public String id;

	public NodeEnumInstance(final int line, final int column, final Token id, final NodeExprs exprs) {
	    super(line, column);
	    this.exprs = exprs;
	    this.id = id.data;
	}

	@Override
	public void generate() {}
    }

    public static class NodeType extends Node {

	@Override
	public String toString() {
	    return "NodeType [id=" + id + ", arrDims=" + arrDims + ", optional=" + optional + ", tupleTypes=" + tupleTypes + ", generics=" + generics + "]";
	}

	public String id;
	public int arrDims;
	public boolean optional;
	public LinkedList<NodeType> tupleTypes = new LinkedList<NodeType>();
	public NodeTypes generics = new NodeTypes();

	public NodeType(final int line, final int column, final String data) {
	    super(line, column);
	    id = data;
	}

	public NodeType() {}

	@Override
	public void analyse() {
	    if (tupleTypes.size() == 0) {
		if (!Semantics.typeExists(id)) semanticError(this, line, column, TYPE_DOES_NOT_EXIST, id);
		else if (!id.equals("void")) {
		    final Optional<Type> typeOpt = Semantics.getType(id);
		    if (!typeOpt.isPresent() && !EnumPrimitive.isPrimitive(id)) {
			for (final String generic : Semantics.currentType().generics)
			    if (id.equals(generic)) return;
			semanticError(this, line, column, TYPE_DOES_NOT_EXIST, id);
		    } else if (!EnumPrimitive.isPrimitive(id)) if (generics.types.size() > typeOpt.get().generics.size()) semanticError(this, line, column, TOO_MANY_GENERICS);
		}
		if (EnumPrimitive.isPrimitive(id) && optional && arrDims == 0) semanticError(this, line, column, PRIMTIVE_CANNOT_BE_OPTIONAL, id);
	    } else for (int i = 0; i < tupleTypes.size(); i++) tupleTypes.get(i).analyse();
	    
	}

	@Override
	public void generate() {}

    }

    public static class NodeQualifiedName extends Node {

	LinkedList<String> paths = new LinkedList<String>();
	public String shortName;

	public NodeQualifiedName(final int line, final int column) {
	    super(line, column);
	}

	public String toDirString() {
	    final StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < (paths.size() - 1); i++)
		sb.append(paths.get(i) + "/");
	    sb.append(paths.get(paths.size() - 1));
	    return sb.toString();
	}

	@Override
	public String toString() {
	    final StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < (paths.size() - 1); i++)
		sb.append(paths.get(i) + ".");
	    sb.append(paths.get(paths.size() - 1));
	    return sb.toString();
	}

	public void add(final String data) {
	    paths.add(data);
	    shortName = data;
	}

	@Override
	public void generate() {}

    }

    public static class NodeFuncDec extends Node {

	public LinkedList<NodeModifier> mods;
	public String id;
	public NodeArgs args;
	public NodeType type, throwsType;
	public NodeFuncBlock block;
	public NodeTypes generics;

	private TypeI returnType;
	private Function func;
	private boolean isMutFunc, isConstructor;
	private GenNodeFunction genNodeFunc;

	public NodeFuncDec(final int line, final int column, final LinkedList<NodeModifier> mods, final String id, final NodeArgs args, final NodeType type, final NodeType throwsType, final NodeFuncBlock block, final NodeTypes types) {
	    super(line, column);
	    this.mods = mods;
	    this.id = id;
	    this.args = args;
	    this.type = type;
	    this.throwsType = throwsType;
	    this.block = block;
	    generics = types;
	    this.block.inFunction = true;
	}

	public NodeFuncDec(final int line, final int columnStart, final LinkedList<NodeModifier> mods2, final String data, final NodeArgs args2, final NodeType throwsType2, final NodeFuncBlock block2, final NodeTypes nodeTypes, final boolean isMutFunc) {
	    this(line, columnStart, mods2, data, args2, null, throwsType2, block2, nodeTypes);
	    this.isMutFunc = true;
	}

	@Override
	public void preAnalyse() {
	    final QualifiedName name = Scope.getNamespace().copy();
	    name.add(id);
	    int modifiers = 0;
	    for (final NodeModifier mod : mods){
		if(mod.asInt() == EnumModifier.STATIC.intVal && isMutFunc) semanticError(this, line, column, MUT_FUNC_IS_STATIC, id);
		else modifiers |= mod.asInt();
	    }
	    func = new Function(name, modifiers);
	    for (final NodeType generic : generics.types)
		func.generics.add(generic.id);

	    this.isConstructor = id.equals(Semantics.currentType().qualifiedName.shortName);
	    args.preAnalyse();
	    if (args.hasDefExpr) func.hasDefExpr = true;

	    // We need to push a new scope and add the parameters as variables
	    // so that return type inference works
	    Scope.push(new FuncScope(returnType, isMutFunc, BitOp.and(modifiers, EnumModifier.STATIC.intVal)));
	    for (final NodeArg arg : args.args)
		Semantics.addVar(new Variable(arg.id, new TypeI(arg.type)));

	    if (!isMutFunc && !isConstructor) {
		if (!type.id.equals("void")) returnType = new TypeI(type);
		else returnType = TypeI.getVoidType();
		returnType = Semantics.filterNullType(returnType);
	    } else returnType = new TypeI(Semantics.currentType().qualifiedName.shortName, 0, false);
	    func.returnType = returnType;

	    Scope.getFuncScope().returnType = returnType;
	    Scope.pop();

	    for (final NodeArg arg : args.args)
		func.parameters.add(new TypeI(arg.type));
	    if (!Semantics.funcExists(func)) Semantics.addFunc(func);
	    else semanticError(this, line, column, FUNC_ALREADY_EXISTS, id);
	}

	@Override
	public void analyse() {
	    args.analyse();
	    if (id.equals("init")) isConstructor = true;
	    if (type != null) type.analyse();
	    if (throwsType != null) {
		throwsType.analyse();
		final Optional<Type> type = Semantics.getType(throwsType.id);
		if (type.isPresent()) if (!type.get().hasSuper(new QualifiedName("").add("java").add("lang").add("Throwable"))) semanticError(this, line, column, TYPE_DOES_NOT_EXTEND, throwsType.id, "java.lang.Throwable");
	    }
	    Scope.push(new FuncScope(returnType, isMutFunc, BitOp.and(func.modifiers, EnumModifier.STATIC.intVal)));
	    for (final NodeArg arg : args.args)
		Semantics.addVar(new Variable(arg.id, new TypeI(arg.type)));
	    block.analyse();
	    // If the return type is not "void", all code paths must have a
	    // return statement
	    if (!returnType.isVoid() && !isMutFunc && !isConstructor) if (!block.hasReturnStmt()) semanticError(this, line, column, NOT_ALL_PATHS_HAVE_RETURN);
	    Scope.pop();
	}

	public void generateConstructor(final LinkedList<NodeVarDec> varDecs) {
	    String name = "<init>", type = "V";
	    genNodeFunc = new GenNodeFunction(name, func.modifiers, type);
	    //TODO: Generate constructor calls if this function is a constructor
	    genNodeFunc.params = func.parameters;
	    GenNode.addGenNodeFunction(genNodeFunc);
	    block.generate();
	    for(IFuncStmt stmt : block.stmts){
		if(stmt instanceof NodeFuncCall){
		    // If this constructor calls another class constructor, we don't need to initialise the class' fields here
		    if(((NodeFuncCall)stmt).isThisCall){
			GenNode.addFuncStmt(new GenNodeReturn());
			GenNode.exitGenNodeFunction();
			return;
		    }
		}
	    }
	    // We have to initlialse the class' fields to their default values
	    // in the constructor
	    for (final NodeVarDec dec : varDecs) {
		IExpression expr = null;
		if (dec instanceof NodeVarDecImplicit) expr = ((NodeVarDecImplicit) dec).expr;
		else expr = ((NodeVarDecExplicit) dec).expr;

		if (expr == null) expr = dec.var.type.getDefaultValue();

		((Node) expr).generate();
		genNodeFunc.stmts.add(new GenNodeFieldStore(dec.var.id, dec.var.enclosingType.qualifiedName.toBytecodeName(), dec.var.type.toBytecodeName(), dec.var.isStatic()));
	    }
	    GenNode.addFuncStmt(new GenNodeReturn());
	    GenNode.exitGenNodeFunction();
	}

	@Override
	public void generate() {
	    String name = Operator.filterOperators(id), type = returnType.toBytecodeName();
	    genNodeFunc = new GenNodeFunction(name, func.modifiers, type);
	    //TODO: Generate constructor calls if this function is a constructor
	    genNodeFunc.params = func.parameters;
	    GenNode.addGenNodeFunction(genNodeFunc);
	    int argID = 0;
	    for(TypeI arg : func.parameters){
		GenNode.addFuncStmt(new GenNodeVar("arg"+argID, arg.toBytecodeName(), argID + (func.isStatic() ? 0 : 1), null)); //TODO: generics
	    }
	    block.generate();
	    if(isMutFunc) {
		addFuncStmt(new GenNodeThis());
		addFuncStmt(new GenNodeReturn(EnumInstructionOperand.REFERENCE));
	    }
	    GenNode.exitGenNodeFunction();
	}

    }

    public static class NodeVarDec extends Node implements IFuncStmt {
	public LinkedList<NodeModifier> mods;
	public String keyword;
	public String id;
	public NodeFuncBlock getBlock, setBlock;
	public Field var;

	public NodeVarDec(final int line, final int column, final LinkedList<NodeModifier> mods, final String keyword, final String id) {
	    super(line, column);
	    this.mods = mods;
	    this.keyword = keyword;
	    this.id = id;
	    
	}

	protected void analyseProperty(final TypeI type) {
	    if ((getBlock != null) || (setBlock != null)) if (keyword.equals("const")) semanticError(this, line, column, CONST_VAR_IS_PROPERTY, id);
	    else if (Scope.getFuncScope() != null) semanticError(this, line, column, PROPERTY_IN_FUNC, id);
	    if (getBlock != null) {
		Scope.push(new PropertyScope(var));
		getBlock.analyse();
		Scope.pop();
	    }
	    if (setBlock != null) {
		Scope.push(new PropertyScope(var));
		Variable newVar = new Variable("new", var.type);
		newVar.localID = var.isStatic() ? 0 : 1;
		Scope.getScope().addVar(newVar);
		setBlock.analyse();
		Scope.pop();
	    }
	}

	@Override
	public void analyse() {
	    // Only check if this var exists if we're in a scope, since variable
	    // declarations in types are already handled
	    if ((Scope.getScope() != null) && Semantics.varExists(id)) semanticError(this, line, column, VAR_ALREADY_EXISTS, id);
	}

	@Override
	public void generate() {
	    if (getBlock != null) {
		final GenNodeFunction getFunc = new GenNodeFunction("$get" + id, var.modifiers, var.type.toBytecodeName());
		GenNode.addGenNodeFunction(getFunc);
		getBlock.generate();
		GenNode.exitGenNodeFunction();
	    }
	    if (setBlock != null) {
		final GenNodeFunction setFunc = new GenNodeFunction("$set" + id, var.modifiers, var.type.toBytecodeName());
		setFunc.params.add(var.type);
		GenNode.addGenNodeFunction(setFunc);
		addFuncStmt(new GenNodeVar("new", var.type.toBytecodeName(), var.isStatic() ? 0 : 1, null));
		setBlock.generate();
		GenNode.exitGenNodeFunction();
	    }
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
	    for (final NodeModifier mod : mods){
		modifiers |= mod.asInt();
	    }
	    var = new Field(name, modifiers, new TypeI(type), setBlock != null, getBlock != null);
	    if (!Semantics.fieldExists(var)) Semantics.addField(var);
	    else semanticError(this, line, column, FIELD_ALREADY_EXISTS, id);
	}

	@Override
	public void analyse() {
	    super.analyse();
	    type.analyse();
	    if (expr != null) ((Node) expr).analyse();
	    typeI = new TypeI(type);
	    //if (!errored) Semantics.addVar(new Variable(id, typeI));

	    if (expr == null) {
		if (!type.optional && !(EnumPrimitive.isPrimitive(type.id) && (type.arrDims == 0))) semanticError(this, line, column, MISSING_ASSIGNMENT);
	    } else {
		final TypeI exprType = expr.getExprType();
		if (exprType != null) if (!typeI.canBeAssignedTo(exprType)) semanticError(this, line, column, CANNOT_ASSIGN, exprType, typeI.toString());
	    }
	    if(var == null){ 
		var = new Variable(id, typeI);
		for (final NodeModifier mod : mods) var.modifiers |= mod.asInt();
	    	Semantics.addVar((Variable) var);
	    }
	    analyseProperty(typeI);
	}

	@Override
	public void generate() {
	    if(!var.isLocal){
		GenNode.addGenNodeField(new GenNodeField(var));
	    }
	    else GenNode.addFuncStmt(new GenNodeVar(var.id, var.type.toBytecodeName(), var.localID, null)); //TODO: generics
	    // Variable initialisation is handled by the class block
	    super.generate();
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
	    expr.analyse();
	    final TypeI exprType = expr.getExprType();
	    var = new Field(name, modifiers, exprType, setBlock != null, getBlock != null);
	    if (!Semantics.fieldExists(var)) Semantics.addField(var);
	    else semanticError(this, line, column, FIELD_ALREADY_EXISTS, id);
	}

	@Override
	public void analyse() {
	    super.analyse();
	    expr.analyse();
		if (!((Node) expr).errored) {
		    final TypeI type = Semantics.filterNullType(expr.getExprType());
		    if(var == null){
			var = new Variable(id, type);
			Semantics.addVar((Variable) var);
		    }
		    analyseProperty(type);
		}
	}

	@Override
	public void generate() {
	    if(!var.isLocal){
		GenNode.addGenNodeField(new GenNodeField(var));
		// Field initialisation is handled by the class block, so there's no need to do it here
	    }else{
		String type = var.type.toBytecodeName();
		GenNode.addFuncStmt(new GenNodeVar(var.id, type, var.localID, null)); //TODO: generics
		expr.generate();
		addFuncStmt(new GenNodeVarStore(var.type.getInstructionType(), var.localID));
	    }
	    super.generate();
	}

    }

    public static class NodeFuncBlock extends Node {

	public IExpression singleLineExpr;
	public TypeI funcReturnType;
	LinkedList<IFuncStmt> stmts = new LinkedList<IFuncStmt>();
	public IFuncStmt singleLineStmt;
	public boolean inFunction = false;

	public void add(final IFuncStmt funcStmt) {
	    stmts.add(funcStmt);
	}

	public boolean hasReturnStmt() {
	    if (singleLineExpr != null) return true;
	    for (final IFuncStmt stmt : stmts)
		if (stmt instanceof NodeReturn) return true;
		else if (stmt instanceof IConstruct) if (((IConstruct) stmt).hasReturnStmt()) return true;
	    return false;
	}

	@Override
	public void analyse() {
	    funcReturnType = Scope.getFuncScope().returnType;
	    if (singleLineExpr != null) {
		singleLineExpr.analyse();
		final FuncScope scope = Scope.getFuncScope();
		final TypeI singleLineExprType = singleLineExpr.getExprType();
		if (scope.returnType.isVoid()) semanticError(this, ((Node) singleLineExpr).line, ((Node) singleLineExpr).column, RETURN_EXPR_IN_VOID_FUNC);
		else if (!scope.returnType.canBeAssignedTo(singleLineExprType)) semanticError(this, ((Node) singleLineExpr).line, ((Node) singleLineExpr).column, CANNOT_ASSIGN, scope.returnType.toString(), singleLineExprType.toString());
	    } else if (singleLineStmt != null) ((Node) singleLineStmt).analyse();
	    else for (final IFuncStmt stmt : stmts)
		((Node) stmt).analyse();
	}

	@Override
	public void generate() {
	    if (singleLineExpr != null) {
		((Node) singleLineExpr).generate();
		if(inFunction) addFuncStmt(new GenNodeReturn(funcReturnType.getInstructionType()));
	    } else if (singleLineStmt != null) {
		((Node) singleLineStmt).generate();
		if(inFunction) addFuncStmt(new GenNodeReturn(EnumInstructionOperand.VOID));
	    } else {
		for (final IFuncStmt stmt : stmts)
		    ((Node) stmt).generate();
		// Add a return statement if the func is void
		if (inFunction && funcReturnType != null && funcReturnType.isVoid()) addFuncStmt(new GenNodeReturn(EnumInstructionOperand.VOID));
	    }
	}

	public boolean lastIsReturn() {
	    if (stmts.size() > 0) return stmts.getLast() instanceof NodeReturn;
	    return singleLineExpr != null;
	}

    }

    public static class NodePrefix extends Node implements IFuncStmt, IExpression {
	
	public boolean isTypeName = false;

	public NodePrefix(final int line, final int column) {
	    super(line, column);
	}

	@Override
	public TypeI getExprType() {
	    return null;
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {}

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

	@Override
	public void generate() {
	    for(IExpression expr : exprs) expr.generate();
	}

    }

    public static class NodeFuncCall extends NodePrefix {
	public boolean isThisCall, isSuperCall;
	public String id;
	public NodeExprs args;
	public NodePrefix prefix;
	public boolean unwrapped;

	public Function func;
	public TypeI prefixType;

	public NodeFuncCall(final int line, final int column, final String id, final NodeExprs args, final NodePrefix prefix, final boolean unwrapped, boolean isThisCall, boolean isSuperCall) {
	    super(line, column);
	    this.args = args;
	    this.prefix = prefix;
	    this.unwrapped = unwrapped;
	    this.id = id;
	    this.isThisCall = isThisCall;
	    this.isSuperCall = isSuperCall;
	}

	@Override
	public String toString() {
	    return "NodeFuncCall [id=" + id + ", args=" + args + ", prefix=" + prefix + "]";
	}

	@Override
	public TypeI getExprType() {
	    TypeI result;
	    if (prefix == null) {
		final TypeI funcType = func.returnType.copy();
		// Fill in the function's generics based on the arguments
		int i = 0;
		for(String g : func.generics){
		    boolean foundGeneric = false;
		    int paramIndex = 0;
		    for(TypeI t : func.parameters){
			if(t.shortName.equals(g)){
			    funcType.genericTypes.set(i, args.exprs.get(paramIndex).getExprType());
			    foundGeneric = true;
			    break;
			}
			paramIndex++;
		    }
		    i++;
		}
		result = funcType;
	    } else {
		prefixType = prefix.getExprType();
		final TypeI funcType = func.returnType.copy();
		// Transfer generics for non-tuple types
		if (!funcType.isTuple() && !funcType.isPrimitive()) {
		    final Optional<Type> typeOpt = Semantics.getType(funcType.shortName);
		    if (!typeOpt.isPresent()) {
			final TypeI genericType = Semantics.getGenericType(funcType.shortName, prefixType);
			if (genericType == null) semanticError(this, line, column, TYPE_DOES_NOT_EXIST, funcType.shortName);
			else return genericType;
		    }
		}
		result = funcType;
	    }
	    if (unwrapped) return Semantics.checkUnwrappedOptional(result, this, this);
	    return result;
	}

	@Override
	public void analyse() {
	    args.analyse();
	    if(prefix != null) prefix.analyse();
	    // If it's a "this" call, change the id to the name of the current type
	    // If it's a "super" call, change the id to the name of the super type
	    if(this.isThisCall) this.id = Semantics.currentType().qualifiedName.shortName;
	    else if(this.isSuperCall) this.id = Semantics.currentType().getSuperClass().qualifiedName.shortName;
	    if (prefix == null) func = Semantics.getFunc(id, args);
	    else {
		prefixType = prefix.getExprType();
		func = Semantics.getFunc(id, prefixType, args);
		if ((func != null) && !func.isVisible()) semanticError(this, line, column, FUNC_IS_NOT_VISIBLE, func.qualifiedName.shortName);
	    }

	    if (func == null) {
		if (prefixType == null) semanticError(this, line, column, CONSTRUCTOR_DOES_NOT_EXIST, id);
		else semanticError(this, line, column, FUNC_DOES_NOT_EXIST, id, prefixType);
	    } else{
		if(prefix == null) if(Scope.inFuncScope() && Scope.getFuncScope().isStatic && !func.isStatic() && !func.isConstructor())
		    semanticError(line, column, NON_STATIC_FUNC_USED_IN_STATIC_CONTEXT, func.qualifiedName.shortName);
		if (!func.isVisible()) semanticError(this, line, column, FUNC_IS_NOT_VISIBLE, func.qualifiedName.shortName);
	    }
	}

	@Override
	public void generate() {
	    final StringBuffer sb = new StringBuffer("(");
	    for (final TypeI type : func.parameters)
		sb.append(type.toBytecodeName());
	    sb.append(")" + (func.isConstructor() ? "V" : func.returnType.toBytecodeName()));
	    // Create a new object for cosntructor calls
	    // However, don't do this if we're calling "this" or "super"
	    if(func.isConstructor() && !isThisCall && !isSuperCall){
		addFuncStmt(new GenNodeNew(func.enclosingType.qualifiedName.toBytecodeName()));
		addFuncStmt(new GenNodeOpcode(Opcodes.DUP));
	    }else if(prefix == null && !func.isStatic()) addFuncStmt(new GenNodeThis());
	    else if(prefix != null) prefix.generate();
	    for(IExpression expr : args.exprs) expr.generate();
	    String name = func.isConstructor() ? "<init>" : Operator.filterOperators(func.qualifiedName.shortName);
	    addFuncStmt(new GenNodeFuncCall(func.enclosingType.qualifiedName.toBytecodeName(), name, sb.toString(), func.enclosingType.type == EnumType.INTERFACE, BitOp.and(func.modifiers, EnumModifier.PRIVATE.intVal), BitOp.and(func.modifiers, EnumModifier.STATIC.intVal), func.isConstructor()));
	}

    }

    public static class NodeVariable extends NodePrefix {

	public String id;
	public NodePrefix prefix;
	public boolean unwrapped;
	public Field var;
	public TypeI prefixType;
	public boolean isSelf;

	public NodeVariable(final int line, final int column, final String id, final NodePrefix prefix, final boolean unwrapped) {
	    super(line, column);
	    this.id = id;
	    this.prefix = prefix;
	    this.unwrapped = unwrapped;
	}

	@Override
	public TypeI getExprType() {
	    if (isTypeName) return new TypeI(id, 0, false);
	    TypeI result = var.type;
	    int i = 0;
		if(var.enclosingType != null){
		    for (final String generic : var.enclosingType.generics)
			if (generic.equals(var.type.shortName)){
			    result = prefixType.genericTypes.get(i);
			    break;
			}
			else i++;
		}
	    if (unwrapped) return Semantics.checkUnwrappedOptional(result, this, this);
	    return result;
	}

	@Override
	public void analyse() {
	    if(id.equals("self") && Scope.inPropertyScope()){
		isSelf = true;
		PropertyScope scope = Scope.getPropertyScope();
		var = scope.field;
	    }else if(id.equals("new") && Scope.inPropertyScope()){
		var = Scope.getPropertyScope().vars.get(0);
	    }else if (prefix == null){
		// Check if this is a type name rather than a variable
		Optional<Type> typeOpt = Semantics.getType(id);
		if(typeOpt.isPresent()){
		    isTypeName = true;
		    return;
		}
		var = Semantics.getVar(id);
	    }else {
		prefix.analyse();
		prefixType = prefix.getExprType();
		var = Semantics.getVar(id, prefixType);
	    }
	    if (var == null) semanticError(this, line, column, VAR_DOES_NOT_EXIST, id);
	    else if (!var.isVisible()) semanticError(this, line, column, VAR_IS_NOT_VISIBLE, var.qualifiedName.shortName);
	    else if(!var.isLocal && Scope.inFuncScope() && Scope.getFuncScope().isStatic && !var.isStatic() && prefix == null) semanticError(this, line, column, NON_STATIC_VAR_USED_IN_STATIC_CONTEXT, var.qualifiedName.shortName);
	}

	@Override
	public void generate() {
	    if(var == null || isTypeName) return;
	    if(prefix != null){
		prefix.generate();
		if(!var.isGetProperty || isSelf){
		    String enclosingType = var.enclosingType.qualifiedName.toBytecodeName(), varType = var.type.toBytecodeName();
		    if(prefixType.isTuple()){
			enclosingType = "Tuple" + prefixType.tupleTypes.size();
			varType = "Ljava/lang/Object;";
		    }
		    addFuncStmt(new GenNodeFieldLoad(var.qualifiedName.shortName, enclosingType, varType, BitOp.and(var.modifiers, EnumModifier.STATIC.intVal)));
		    if(prefixType.isTuple()){
			addFuncStmt(new GenNodeTypeOpcode(Opcodes.CHECKCAST, var.type.toBytecodeName()));
		    }
		}
		else generateGetFuncCall(var);
	    }else{
		if(var.isLocal){
		    addFuncStmt(new GenNodeVarLoad(var.type.getInstructionType(), var.localID));
		}
		else{
		    if(!var.isStatic()) addFuncStmt(new GenNodeThis());
		    if(!var.isGetProperty || isSelf) addFuncStmt(new GenNodeFieldLoad(var.qualifiedName.shortName, var.enclosingType.qualifiedName.toBytecodeName(), var.type.toBytecodeName(), BitOp.and(var.modifiers, EnumModifier.STATIC.intVal)));
		    else generateGetFuncCall(var);
		}
	    }
	}

	private void generateGetFuncCall(Field var) {
	    String enclosingType = var.enclosingType.qualifiedName.toBytecodeName();
	    String getFuncName = "$get" + var.qualifiedName.shortName;
	    String signature = "()" + var.type.toBytecodeName();
	    GenNode.addFuncStmt(new GenNodeFuncCall(enclosingType, getFuncName, signature, false, var.isPrivate(), var.isStatic(), false));
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

	@Override
	public void analyse() {
	    expr.analyse();
	    final TypeI exprType = expr.getExprType();
	    var.analyse();
	    if(var.errored) errored = true;
	    if (var.var != null) if (!var.var.type.canBeAssignedTo(expr.getExprType())) semanticError(this, line, column, CANNOT_ASSIGN, var.var.type, exprType);
	}

	@Override
	public void generate() {
	    if(var.prefix != null){
		var.prefix.generate();
		expr.generate();
		// If the variable is a property with a set block, we have to call the set function and assign the variable to the result
		if(var.var.isSetProperty){
		    generateSetFuncCall(var.var);
		}
		addFuncStmt(new GenNodeFieldStore(var.var.qualifiedName.shortName, var.var.enclosingType.qualifiedName.toBytecodeName(), var.var.type.toBytecodeName(), var.var.isStatic()));
	    }else{
		GenNode node = null;
		if(var.var.isLocal){
		    expr.generate();
		    node = new GenNodeVarStore(var.var.type.getInstructionType(), var.var.localID);
		}else{
		    if(!var.var.isStatic()) addFuncStmt(new GenNodeThis());
		    expr.generate();
		    // If the variable is a property with a set block, we have to call the set function and assign the variable to the result
		    if(var.var.isSetProperty){
			generateSetFuncCall(var.var);
		    }
		    node = new GenNodeFieldStore(var.var.qualifiedName.shortName, var.var.enclosingType.qualifiedName.toBytecodeName(), var.var.type.toBytecodeName(), var.var.isStatic());
		}
		addFuncStmt(node);
	    }
	    
	}

	private void generateSetFuncCall(Field var) {
	    String setFuncName = "$set" + var.qualifiedName.shortName;
	    String enclosingType = var.enclosingType.qualifiedName.toBytecodeName();
	    String varType = var.type.toBytecodeName();
	    String signature = "(" + varType + ")" + varType;
	    addFuncStmt(new GenNodeFuncCall(enclosingType, setFuncName, signature, false, var.isPrivate(), var.isStatic(), false));
	}

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("NodeVarAssign [var=");
	    builder.append(var);
	    builder.append(", assignOp=");
	    builder.append(assignOp);
	    builder.append(", expr=");
	    builder.append(expr);
	    builder.append("]");
	    return builder.toString();
	}

    }

    public static class NodeReturn extends Node implements IFuncStmt {
	public IExpression expr;
	public TypeI exprType;

	public NodeReturn(final int line, final int column, final IExpression expr) {
	    super(line, column);
	    this.expr = expr;
	}

	@Override
	public void analyse() {
	    final FuncScope scope = Scope.getFuncScope();
	    if (scope.isMutFunc) semanticError(this, line, column, RETURN_IN_MUT_FUNC);
	    if (expr != null) {
		expr.analyse();
		exprType = expr.getExprType();
		if (scope.returnType.isVoid()) semanticError(this, line, column, RETURN_EXPR_IN_VOID_FUNC);
		else if (!scope.returnType.canBeAssignedTo(exprType)) semanticError(this, line, column, CANNOT_ASSIGN, scope.returnType.toString(), exprType.toString());
	    } else if (!scope.returnType.isVoid()) semanticError(this, line, column, RETURN_VOID_IN_NONVOID_FUNC);

	}

	@Override
	public void generate() {
	    ((Node) expr).generate();
	    addFuncStmt(new GenNodeReturn(exprType.getInstructionType()));
	}

    }

    public static class NodeInteger extends Node implements IExpression {
	public int val;

	public NodeInteger(final int line, final int column, final int i) {
	    super(line, column);
	    val = i;
	}

	@Override
	public String toString() {
	    return "NodeInteger [val=" + val + "]";
	}

	@Override
	public TypeI getExprType() {
	    return new TypeI(EnumPrimitive.INT);
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    addFuncStmt(new GenNodeInt(val));
	}

    }

    public static class NodeLong extends Node implements IExpression {
	public long val;

	public NodeLong(final int line, final int column, final long val) {
	    super(line, column);
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

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    addFuncStmt(new GenNodeLong(val));
	}

    }

    public static class NodeFloat extends Node implements IExpression {
	public float val;

	public NodeFloat(final int line, final int column, final float val) {
	    super(line, column);
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

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    addFuncStmt(new GenNodeFloat(val));
	}

    }

    public static class NodeDouble extends Node implements IExpression {
	public double val;

	public NodeDouble(final int line, final int column, final double val) {
	    super(line, column);
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

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    addFuncStmt(new GenNodeDouble(val));
	}

    }

    public static class NodeString extends Node implements IExpression {
	public String val;

	public NodeString(final int line, final int columnStart, final String val) {
	    super(line, columnStart);
	    this.val = val;
	}

	@Override
	public void analyse() {
	    int i = 0;
	    boolean inVarName = false, inExpr = false;
	    String varName = "";
	    while (i < val.length()) {
		final char ch = val.charAt(i);
		if (ch == '\\') i++;
		else if (ch == '$') inVarName = true;
		else if ((ch == '{') && inVarName) inExpr = true;
		else if ((ch == '}') && inExpr) {
		    inExpr = false;
		    inVarName = false;
		    Parser parser = null;
		    try {
			parser = new Parser(new Lexer(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(varName.getBytes())))));
			parser.lineOffset = line - 2;
			parser.columnOffset = column + i;
			final IExpression expr = parser.parseExpression();
			if (expr != null) ((Node) expr).analyse();
		    } catch (final IOException e) {
			e.printStackTrace();
		    } catch (final UnexpectedTokenException e) {
			if (parser != null) parser.handleException(e);
		    }
		} else if (ch == ' ') {
		    if (inVarName && !inExpr) {
			inVarName = false;
			if (!Semantics.varExists(varName)) semanticError(this, line, column, VAR_DOES_NOT_EXIST, varName);
			varName = "";
		    }
		} else if (inVarName) varName += ch;
		i++;
	    }
	    if (inExpr) semanticError(this, line, column, EXPECTED_STRING_INTERP_TERMINATOR);
	    else if (inVarName && !Semantics.varExists(varName)) semanticError(this, line, column, VAR_DOES_NOT_EXIST, varName);
	}

	@Override
	public String toString() {
	    return "NodeString [val=" + val + "]";
	}

	@Override
	public TypeI getExprType() {
	    return new TypeI("String", 0, false);
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    addFuncStmt(new GenNodeString(val));
	}

    }

    public static class NodeBool extends Node implements IExpression {
	public boolean val;

	public NodeBool(final int line, final int column, final boolean val) {
	    super(line, column);
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

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    addFuncStmt(new GenNodeInt(val ? 1 : 0));
	}

    }

    public static class NodeChar extends Node implements IExpression {
	public char val;

	public NodeChar(final int line, final int column, final char val) {
	    super(line, column);
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

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    addFuncStmt(new GenNodeInt(val));
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
	public void analyse() {
	    expr.analyse();
	    exprTrue.analyse();
	    exprFalse.analyse();
	}

	@Override
	public String toString() {
	    return "NodeTernary [expr=" + expr + ", exprTrue=" + exprTrue + ", exprFalse=" + exprFalse + "]";
	}

	@Override
	public TypeI getExprType() {
	    return Semantics.getPrecedentType(exprTrue.getExprType(), exprFalse.getExprType());
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    final Label lbl0 = new Label(), lbl1 = new Label();
	    addFuncStmt(new GenNodeConditionalJump(expr, lbl0));
	    ((Node) exprTrue).generate();
	    addFuncStmt(new GenNodeJump(lbl1));
	    addFuncStmt(new GenNodeLabel(lbl0));
	    ((Node) exprFalse).generate();
	    addFuncStmt(new GenNodeLabel(lbl1));
	}

    }

    public static class NodeElvis extends Node implements IExpression {

	public IExpression exprOptional, exprNotNull;

	public NodeElvis(final int line, final int column, final IExpression exprOptional, final IExpression exprNotNull) {
	    super(line, column);
	    this.exprOptional = exprOptional;
	    this.exprNotNull = exprNotNull;
	}

	@Override
	public void analyse() {
	    ((Node) exprOptional).analyse();
	    ((Node) exprNotNull).analyse();
	}

	@Override
	public TypeI getExprType() {
	    final TypeI exprOptionalType = exprOptional.getExprType(), exprNotNullType = exprNotNull.getExprType();
	    if (!exprOptionalType.optional) semanticError(this, line, column, ELVIS_EXPR_NOT_OPTIONAL, exprOptionalType.toString());
	    if (exprNotNullType.optional) semanticError(this, line, column, ELVIS_EXPR_IS_OPTIONAL, exprNotNullType.toString());
	    if (EnumPrimitive.isPrimitive(exprOptionalType.shortName)) semanticError(this, line, column, ELVIS_EXPR_IS_PRIMITIVE, exprOptional);
	    // Set the types as not optional to stop incompatibility errors
	    // thrown by the next method call
	    exprOptionalType.copy().optional = false;
	    exprNotNullType.copy().optional = false;
	    final TypeI type = Semantics.getPrecedentType(exprOptionalType, exprNotNullType).setOptional(false);
	    return type;
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    exprOptional.generate();
	    final Label lbl = new Label(), lbl2 = new Label();
	    addFuncStmt(new GenNodeConditionalJump(Opcodes.IFNONNULL, lbl));
	    exprOptional.generate();
	    addFuncStmt(new GenNodeJump(lbl2));
	    addFuncStmt(new GenNodeLabel(lbl));
	    exprNotNull.generate();
	    addFuncStmt(new GenNodeLabel(lbl2));
	}

    }

    public static class NodeBinary extends Node implements IExpression {
	public IExpression expr1, expr2;
	public Operator operator;
	public Function operatorOverloadFunc;
	public TypeI exprType1, exprType2;

	public NodeBinary(final int line, final int columnStart, final IExpression expr1, final String operator, final IExpression expr2) {
	    super(line, columnStart);
	    this.expr1 = expr1;
	    this.expr2 = expr2;
	    this.operator = new Operator(operator);
	}

	@Override
	public void analyse() {
	    ((Node) expr1).analyse();
	    ((Node) expr2).analyse();
	    if (((Node) expr1).errored || ((Node) expr1).errored) errored = true;
	    else {
		exprType1 = expr1.getExprType();
		exprType2 = expr2.getExprType();
	    }
	}

	@Override
	public String toString() {
	    return "NodeBinary [expr1=" + expr1 + ", expr2=" + expr2 + ", operator=" + operator + "]";
	}

	@Override
	public TypeI getExprType() {
	    if (errored) return null;

	    // Returning an array here is messy, but the best way I can
	    // think of returning both a TypeI and Function, rather than using a
	    // new class
	    final Object[] operation = Semantics.getOperationType(exprType1, exprType2, operator);
	    if (operation == null) semanticError(this, line, column, OPERATOR_CANNOT_BE_APPLIED_TO_TYPES, operator.opStr, exprType1, exprType2);
	    operatorOverloadFunc = (Function) operation[1];
	    return (TypeI) operation[0];

	}

	@Override
	public void registerScopedChecks() {
	    if ((expr2 instanceof NodeNull) && operator.opStr.equals("!=") && (expr1 instanceof NodeVariable)) {
		final NodeVariable varExpr = (NodeVariable) expr1;
		if (varExpr.var != null) Scope.getScope().nullChecks.add(varExpr.var);
	    }
	}

	@Override
	public void generate() {
	    if(operatorOverloadFunc == null){
		// Simple binary expression on primitive types
		EnumInstructionOperand precedentType = Semantics.getPrecedentType(exprType1, exprType2).getInstructionType();
		
		// Generate the expressions and add the necessary casts
		expr1.generate();
		if(exprType1.getInstructionType() != precedentType){
		    addFuncStmt(new GenNodePrimitiveCast(exprType1.getInstructionType(), precedentType));
		}
		expr2.generate();
		if(exprType2.getInstructionType() != precedentType){
		    addFuncStmt(new GenNodePrimitiveCast(exprType2.getInstructionType(), precedentType));
		}
		addFuncStmt(new GenNodeBinary(operator, precedentType));
	    }else{
		// Operator-overloaded binary expression
		String enclosingType = operatorOverloadFunc.enclosingType.qualifiedName.toBytecodeName();
		boolean interfaceFunc = operatorOverloadFunc.enclosingType.type == EnumType.INTERFACE, privateFunc = BitOp.and(operatorOverloadFunc.modifiers, EnumModifier.PRIVATE.intVal);
		String parameterType = null;
		if(operatorOverloadFunc.enclosingType.qualifiedName.shortName.equals(exprType1.shortName)){
		    // expression 1 is the reference from which the method should be called
		    expr1.generate();
		    // expr2 is the parameter to the method
		    expr2.generate();
		    parameterType = exprType2.toBytecodeName();
		}else{
		    // expression 2 is the reference from which the method should be called
		    expr2.generate();
		    // expr1 is the parameter to the method
		    expr1.generate();
		    parameterType = exprType1.toBytecodeName();
		}
		String name = Operator.filterOperators(operator.opStr);
		addFuncStmt(new GenNodeFuncCall(enclosingType, name, "("+parameterType+")"+operatorOverloadFunc.returnType.toBytecodeName(), interfaceFunc, privateFunc, false, false));
	    }
	}
    }

    public static class NodeUnary extends Node implements IExpression, IFuncStmt {
	public IExpression expr;
	public Operator operator;
	public boolean prefix;
	public Function overloadFunc;
	public TypeI type;

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
	public void analyse() {
	    ((Node) expr).analyse();
	}

	@Override
	public TypeI getExprType() {
	    if (!((NodeVariable) expr).errored) {
		final TypeI exprType = expr.getExprType();
		Operation op = Semantics.getOperationType(exprType, operator);
		if (op == null) semanticError(this, line, column, OPERATOR_CANNOT_BE_APPLIED_TO_TYPE, operator.opStr, exprType);
		else{
		    overloadFunc = op.overloadFunc;
		    type = op.type;
		}
	    }
	    return type;
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    if(overloadFunc == null){
		expr.generate();
		addFuncStmt(new GenNodeUnary(type.getInstructionType(), operator, prefix));
	    }else{
		expr.generate();
		String enclosingType = overloadFunc.enclosingType.qualifiedName.toBytecodeName(), returnType = overloadFunc.returnType.toBytecodeName();
		boolean privateFunc = BitOp.and(overloadFunc.modifiers, EnumModifier.PRIVATE.intVal), interfaceFunc = overloadFunc.enclosingType.type == EnumType.INTERFACE;
		String name = Operator.filterOperators(operator.opStr);
		addFuncStmt(new GenNodeFuncCall(enclosingType, name, "()" + returnType, interfaceFunc, privateFunc, false, false));
	    }
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

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    addFuncStmt(new GenNodeThis());
	}
    }

    public static class NodeSelf extends Node implements IExpression {
	
	public Field field;

	public NodeSelf(final int line, final int columnStart) {
	    super(line, columnStart);
	}

	@Override
	public TypeI getExprType() {
	    if (Scope.getPropertyScope() != null){
		PropertyScope scope = Scope.getPropertyScope();
		field = scope.field;
		return scope.returnType;
	    }
	    else semanticError(this, line, column, CANNOT_USE_SELF);
	    return null;
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    addFuncStmt(new GenNodeThis());
	    addFuncStmt(new GenNodeFieldLoad(field.qualifiedName.shortName, field.enclosingType.qualifiedName.toBytecodeName(), field.type.toBytecodeName(), BitOp.and(field.modifiers, EnumModifier.STATIC.intVal)));
	}

    }

    public static class NodeNull extends Node implements IExpression {

	@Override
	public TypeI getExprType() {
	    return new TypeI("null", 0, true);
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    addFuncStmt(new GenNodeNull());
	}

    }

    public static class NodeArray extends Node implements IExpression {

	public NodeExprs exprs = new NodeExprs();
	public TypeI arrayType, elementType;

	public NodeArray(final int line, final int column, NodeExprs nodeExprs) {
	    super(line, column);
	    this.exprs = nodeExprs;
	}

	public void add(final IExpression expr) {
	    exprs.add(expr);
	}

	@Override
	public void preAnalyse() {
	    exprs.preAnalyse();
	}

	@Override
	public void analyse() {
	    exprs.analyse();
	}

	@Override
	public TypeI getExprType() {
	    final LinkedList<IExpression> exprList = exprs.exprs;
	    // Just infer an Object array
	    if (exprList.size() == 0) return TypeI.getObjectType().copy().setArrDims(1);
	    // Infer the precedent type within this array expression
	    elementType = exprList.getFirst().getExprType();
	    for (int i = 1; i < exprList.size(); i++)
		elementType = Semantics.getPrecedentType(elementType, exprList.get(i).getExprType());
	    arrayType = elementType.copy().setArrDims(elementType.arrDims+1);
	    // expression
	    return arrayType;
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    int len = exprs.exprs.size();
	    int arrayStoreOpcode = Opcodes.AASTORE;
	    EnumInstructionOperand type = elementType.getInstructionType();
	    GenNode arrayCreateNode = null;
	    switch(type){
		case ARRAY:
		    arrayCreateNode = new GenNodeTypeOpcode(Opcodes.ANEWARRAY, elementType.toBytecodeName());
		    arrayStoreOpcode = Opcodes.AASTORE;
		    break;
		case BOOL:
		    arrayCreateNode = new GenNodeIntOpcode(Opcodes.NEWARRAY, Opcodes.T_BOOLEAN);
		    arrayStoreOpcode = Opcodes.BASTORE;
		    break;
		case BYTE:
		    arrayCreateNode = new GenNodeIntOpcode(Opcodes.NEWARRAY, Opcodes.T_BYTE);
		    arrayStoreOpcode = Opcodes.BASTORE;
		    break;
		case CHAR:
		    arrayCreateNode = new GenNodeIntOpcode(Opcodes.NEWARRAY, Opcodes.T_CHAR);
		    arrayStoreOpcode = Opcodes.CASTORE;
		    break;
		case SHORT:
		    arrayCreateNode = new GenNodeIntOpcode(Opcodes.NEWARRAY, Opcodes.T_SHORT);
		    arrayStoreOpcode = Opcodes.SASTORE;
		    break;
		case INT:
		    arrayCreateNode = new GenNodeIntOpcode(Opcodes.NEWARRAY, Opcodes.T_INT);
		    arrayStoreOpcode = Opcodes.IASTORE;
		    break;
		case DOUBLE:
		    arrayCreateNode = new GenNodeIntOpcode(Opcodes.NEWARRAY, Opcodes.T_DOUBLE);
		    arrayStoreOpcode = Opcodes.DASTORE;
		    break;
		case FLOAT:
		    arrayCreateNode = new GenNodeIntOpcode(Opcodes.NEWARRAY, Opcodes.T_FLOAT);
		    arrayStoreOpcode = Opcodes.FASTORE;
		    break;
		case LONG:
		    arrayCreateNode = new GenNodeIntOpcode(Opcodes.NEWARRAY, Opcodes.T_LONG);
		    arrayStoreOpcode = Opcodes.LASTORE;
		    break;
		case REFERENCE:
		    arrayCreateNode = new GenNodeTypeOpcode(Opcodes.ANEWARRAY, Semantics.getType(elementType.shortName).get().qualifiedName.toBytecodeName());
		    arrayStoreOpcode = Opcodes.AASTORE;
		    break;
		    
	    }
	    addFuncStmt(new GenNodeInt(len));
	    addFuncStmt(arrayCreateNode);
	    int index = 0;
	    for(IExpression expr : exprs.exprs){
		addFuncStmt(new GenNodeOpcode(Opcodes.DUP));
		addFuncStmt(new GenNodeInt(index++));
		expr.generate();
		addFuncStmt(new GenNodeOpcode(arrayStoreOpcode));
	    }
	}

    }

    public static class NodeTupleExpr extends Node implements IExpression {

	public NodeExprs exprs = new NodeExprs();
	public TypeI type;

	public NodeTupleExpr(final int line, final int column) {
	    super(line, column);
	}

	@Override
	public void preAnalyse() {
	    exprs.preAnalyse();
	}

	@Override
	public void analyse() {
	    exprs.analyse();
	}

	@Override
	public TypeI getExprType() {
	    // Just infer an Object array
	    if (exprs.exprs.size() == 0) return TypeI.getObjectType();
	    type = new TypeI("", 0, false);
	    char name = 'a';
	    for (final IExpression expr : exprs.exprs){
		type.tupleTypes.add(expr.getExprType().copy().setTupleName(String.valueOf(name++)));
	    }
	    return type;
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    GenNode.addFuncStmt(new GenNodeNew("Tuple"+exprs.exprs.size()));
	    GenNode.addFuncStmt(new GenNodeOpcode(Opcodes.DUP));
	    exprs.generate();
	    StringBuilder signature = new StringBuilder("(");
	    for(int i = 0; i < type.tupleTypes.size(); i++) signature.append("Ljava/lang/Object;");
	    signature.append(")V");
	    GenNode.addFuncStmt(new GenNodeFuncCall("Tuple"+exprs.exprs.size(), "<init>", signature.toString(), false, false, false, true));
	}

    }

    public static class NodeIf extends Node implements IFuncStmt, IConstruct {
	public IExpression expr;
	public NodeFuncBlock block;
	public NodeIf elseStmt;
	public boolean isElse;

	public NodeIf(final int line, final int column, final IExpression expr, final NodeFuncBlock block) {
	    super(line, column);
	    this.expr = expr;
	    this.block = block;
	}

	@Override
	public void analyse() {
	    Scope.push(new Scope());
	    if (expr != null) {
		((Node) expr).analyse();
		if (!((Node) expr).errored) {
		    final TypeI exprType = expr.getExprType();
		    if ((EnumPrimitive.getPrimitive(exprType.shortName) != EnumPrimitive.BOOL) || exprType.isArray()) semanticError(this, line, column, EXPECTED_BOOL_EXPR, exprType);
		}
		expr.registerScopedChecks();
	    }
	    block.analyse();
	    Scope.pop();
	    if (elseStmt != null) elseStmt.analyse();
	}

	@Override
	public boolean hasReturnStmt() {
	    if (block.hasReturnStmt()) if (isElse) return true;
	    else if (elseStmt.hasReturnStmt()) return true;
	    return false;
	}

	@Override
	public void generate() {
	    generate(new Label(), new Label());
	}

	public void generate(final Label endLabel, Label thisLabel) {
	    // Create a label that corresponds to this else-statement
	    if (thisLabel != null) addFuncStmt(new GenNodeLabel(thisLabel));

	    // Create a new label to jump to in case the condition fails, this
	    // represents the next else-statement
	    thisLabel = new Label();
	    if (expr != null) addFuncStmt(new GenNodeConditionalJump(expr, thisLabel));
	    block.generate();
	    // Jump to the end of the if-else block if the last statement was
	    // not a return statement
	    if ((expr != null) && !block.lastIsReturn()) addFuncStmt(new GenNodeJump(endLabel));

	    if (elseStmt == null) // If this is the end of the block, place the
				  // label
		addFuncStmt(new GenNodeLabel(endLabel));
	    else // If we're not at the end of the block, then generate the next
		 // block
		elseStmt.generate(endLabel, thisLabel);

	}

    }

    public static class NodeWhile extends Node implements IFuncStmt, IConstruct {
	public IExpression expr;
	public NodeFuncBlock block;

	public NodeWhile(final int line, final int column, final IExpression expr, final NodeFuncBlock block) {
	    super(line, column);
	    this.expr = expr;
	    this.block = block;
	}

	@Override
	public void analyse() {
	    ((Node) expr).analyse();
	    if (!((Node) expr).errored) {
		final TypeI exprType = expr.getExprType();
		if (EnumPrimitive.getPrimitive(exprType.shortName) == EnumPrimitive.BOOL && !exprType.isArray());
		else semanticError(this, line, column, EXPECTED_BOOL_EXPR, exprType);
	    }
	    block.analyse();
	}

	@Override
	public boolean hasReturnStmt() {
	    return false;
	}

	@Override
	public void generate() {
	    Label lbl0 = new Label(), lbl1 = new Label();
	    addFuncStmt(new GenNodeLabel(lbl1));
	    addFuncStmt(new GenNodeConditionalJump(expr, lbl0));
	    block.generate();
	    addFuncStmt(new GenNodeJump(lbl1));
	    addFuncStmt(new GenNodeLabel(lbl0));
	}

    }

    public static abstract class NodeFor extends Node implements IFuncStmt, IConstruct {
	public IExpression expr;
	public NodeFuncBlock block;
	
	public TypeI exprType;

	public NodeFor(final int line, final int column, final IExpression expr, final NodeFuncBlock block) {
	    super(line, column);
	    this.expr = expr;
	    this.block = block;
	}

	@Override
	public void analyse() {
	    ((Node) expr).analyse();
	    if (!((Node) expr).errored) {
		exprType = expr.getExprType();
		if (EnumPrimitive.getPrimitive(exprType.shortName) == EnumPrimitive.BOOL) if (!exprType.isArray()) return;
		semanticError(this, line, column, EXPECTED_BOOL_EXPR, exprType);
	    }
	    Scope.push(new Scope());
	    block.analyse();
	    Scope.pop();
	}

	@Override
	public boolean hasReturnStmt() {
	    return false;
	}

    }

    public static class NodeForNormal extends NodeFor implements IFuncStmt {
	public IFuncStmt initStmt, endStmt;

	public NodeForNormal(final int line, final int column, final IFuncStmt initStmt, final IExpression condition, final IFuncStmt endStmt, final NodeFuncBlock block) {
	    super(line, column, condition, block);
	    this.initStmt = initStmt;
	    this.endStmt = endStmt;
	}

	@Override
	public void analyse() {
	    if (initStmt != null){
		initStmt.analyse();
		if(((Node)initStmt).errored){
		    errored = true;
		}
	    }
	    if (endStmt != null){
		endStmt.analyse();
		if(((Node)endStmt).errored){
		    errored = true;
		}
	    }
	    block.analyse();
	    super.analyse();
	}

	@Override
	public void generate() {
	    if(initStmt != null) initStmt.generate();
	    Label lbl0 = new Label(), lbl1 = new Label();
	    addFuncStmt(new GenNodeLabel(lbl0));
	    addFuncStmt(new GenNodeConditionalJump(expr, lbl1));
	    block.generate();
	    if(endStmt != null) endStmt.generate();
	    addFuncStmt(new GenNodeJump(lbl0));
	    addFuncStmt(new GenNodeLabel(lbl1));
	}

    }

    public static class NodeForIn extends NodeFor implements IFuncStmt {

	public String varId;
	public Variable var;

	public NodeForIn(final int line, final int column, final String data, final IExpression parseExpression, final NodeFuncBlock block) {
	    super(line, column, parseExpression, block);
	    varId = data;
	    expr = parseExpression;
	}

	@Override
	public void analyse() {
	    // The only types that can be iterated over are arrays and those
	    // that implement java.lang.Iterable
	    exprType = expr.getExprType();
	    TypeI varType = TypeI.getObjectType();
	    if (exprType.isTuple()) semanticError(this, line, column, CANNOT_ITERATE_TYPE, exprType);
	    if (exprType.isArray()) {
		exprType.copy().arrDims--;
		varType = exprType;
	    } else if (exprType.isRange()) varType = Semantics.getGeneric(exprType.genericTypes, 0);
	    else {
		final Optional<Type> type = Semantics.getType(exprType.shortName);
		if (type.isPresent()) if (type.get().hasSuper(new QualifiedName("java").add("lang").add("Iterable"))) {

		    // Extract the generic used when implementing Iterable
		    final LinkedList<TypeI> list = type.get().getGenerics("Iterable");
		    if ((list == null) || list.isEmpty()) varType = TypeI.getObjectType();
		    else varType = list.get(0);
		} else semanticError(this, line, column, CANNOT_ITERATE_TYPE, exprType);
	    }
	    Scope.push(new Scope());
	    var = new Variable(varId, varType);
	    Scope.getFuncScope().locals += exprType.isArray() ? 3 : 1; // We have to reserve some local variables for the iterator instance
	    Semantics.addVar(var);
	    block.analyse();
	    Scope.pop();
	}

	@Override
	public void generate() {
	    expr.generate();
	    int iteratorVarID = var.localID + 1; // Get the varID that we reserved in the analyse() method
	    if(!exprType.isArray()){
		String enclosingType = Semantics.getType(exprType.shortName).get().qualifiedName.toBytecodeName();
		GenNode.addFuncStmt(new GenNodeFuncCall(enclosingType, "iterator", "()Ljava/util/Iterator;", false, false, false, false));
		GenNode.addFuncStmt(new GenNodeVarStore(EnumInstructionOperand.REFERENCE, iteratorVarID));
		
		// Check if the iterator has a next value
		Label lbl0 = new Label(), lbl1 = new Label();
		GenNode.addFuncStmt(new GenNodeLabel(lbl0));
		GenNode.addFuncStmt(new GenNodeVarLoad(EnumInstructionOperand.REFERENCE, iteratorVarID));
		GenNode.addFuncStmt(new GenNodeFuncCall("java/util/Iterator", "hasNext", "()Z", true, false, false, false));
		GenNode.addFuncStmt(new GenNodeJump(Opcodes.IFEQ, lbl1));
		
		// Get the iterator's next value and jump to the conditional branch
		GenNode.addFuncStmt(new GenNodeFuncCall("java/util/Iterator", "next", "()Ljava/lang/Object;", true, false, false, false));
		GenNode.addFuncStmt(new GenNodeVarStore(EnumInstructionOperand.REFERENCE, var.localID));
		block.generate();
		GenNode.addFuncStmt(new GenNodeJump(lbl0));
		GenNode.addFuncStmt(new GenNodeLabel(lbl1));
	    }else{
		EnumInstructionOperand elementType = exprType.copy().setArrDims(exprType.arrDims-1).getInstructionType();
		int indexVarID = iteratorVarID + 1, lengthVarID = indexVarID + 1;
		// Cache the array reference to a local variable so we don't have to re-generate the expression when accessing an index
		GenNode.addFuncStmt(new GenNodeOpcode(Opcodes.DUP));
		GenNode.addFuncStmt(new GenNodeVarStore(EnumInstructionOperand.ARRAY, iteratorVarID));
		// Save the array length to a local variable
		GenNode.addFuncStmt(new GenNodeOpcode(Opcodes.ARRAYLENGTH));
		GenNode.addFuncStmt(new GenNodeVarStore(EnumInstructionOperand.INT, lengthVarID));
		// Save the first index (0) to a local variable
		GenNode.addFuncStmt(new GenNodeInt(0));
		GenNode.addFuncStmt(new GenNodeVarStore(EnumInstructionOperand.INT, indexVarID));
		
		Label lbl0 = new Label(), lbl1 = new Label();
		// This is the label to which we will jump when the iteration is finished
		GenNode.addFuncStmt(new GenNodeLabel(lbl0));
		// Load the current index, the array length and then compare them. Jump if the index is no less than the array length
		GenNode.addFuncStmt(new GenNodeVarLoad(EnumInstructionOperand.INT, indexVarID));
		GenNode.addFuncStmt(new GenNodeVarLoad(EnumInstructionOperand.INT, lengthVarID));
		GenNode.addFuncStmt(new GenNodeConditionalJump(Opcodes.IF_ICMPLT, lbl1));
		
		// Load the element at the index of the array into the variable specified in the source code
		GenNode.addFuncStmt(new GenNodeVarLoad(EnumInstructionOperand.ARRAY, iteratorVarID));
		GenNode.addFuncStmt(new GenNodeVarLoad(EnumInstructionOperand.INT, indexVarID));
		GenNode.addFuncStmt(new GenNodeArrayIndexLoad(elementType));
		GenNode.addFuncStmt(new GenNodeVarStore(elementType, var.localID));
		block.generate();
		// Increment the index and jump back to the evaluation label
		GenNode.addFuncStmt(new GenNodeIncrement(indexVarID, 1));
		GenNode.addFuncStmt(new GenNodeJump(lbl0));
		GenNode.addFuncStmt(new GenNodeLabel(lbl1));
	    }
	}

    }

    public static class NodeTupleType extends Node {
	public NodeType type;
	public String name;

	public NodeTupleType(final int line, final int column, final NodeType type) {
	    super(line, column);
	    this.type = type;
	}

	@Override
	public void analyse() {
	    type.analyse();
	}

	@Override
	public void generate() {}

    }

    public static class NodeTupleExprArg extends Node {
	public IExpression expr;
	public String name;

	public NodeTupleExprArg(final int line, final int column) {
	    super(line, column);
	}

	@Override
	public void analyse() {
	    ((Node) expr).analyse();
	}

	@Override
	public void generate() {}
    }

    public static class NodeUnwrapOptional extends Node implements IExpression {

	public IExpression expr;

	public NodeUnwrapOptional(final int line, final int column, final IExpression expr) {
	    super(line, column);
	    this.expr = expr;
	}

	@Override
	public void analyse() {
	    ((Node) expr).analyse();
	}

	@Override
	public TypeI getExprType() {
	    final TypeI exprType = expr.getExprType();
	    return Semantics.checkUnwrappedOptional(exprType, this, expr);
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    ((Node) expr).generate();
	}

    }

    public static class NodeIs extends Node implements IExpression {
	public IExpression expr;
	public NodeType nodeType;
	public Type type;

	public NodeIs(final int line, final int column, final IExpression expr, final NodeType type) {
	    super(line, column);
	    this.expr = expr;
	    nodeType = type;
	}

	@Override
	public void analyse() {
	    expr.analyse();
	    nodeType.analyse();
	    if (!nodeType.errored && !((Node) expr).errored) type = Semantics.getType(nodeType.id).get();
	    else errored = true;
	}

	@Override
	public TypeI getExprType() {
	    return new TypeI(EnumPrimitive.BOOL);
	}

	@Override
	public void registerScopedChecks() {
	    if (!errored) if ((expr instanceof NodeVariable) && !((NodeVariable) expr).errored) {
		final Field field = ((NodeVariable) expr).var;
		if (field != null) Scope.getScope().castChecks.put(field, type);
	    }
	}

	@Override
	public void generate() {
	    expr.generate();
	    addFuncStmt(new GenNodeTypeOpcode(Opcodes.INSTANCEOF, type.qualifiedName.toBytecodeName()));
	}

    }

    public static class NodeAs extends Node implements IExpression {
	public IExpression expr;
	public NodeType nodeType;
	public Type type;

	public NodeAs(final int line, final int column, final IExpression expr, final NodeType nodeType) {
	    super(line, column);
	    this.expr = expr;
	    this.nodeType = nodeType;
	}

	@Override
	public TypeI getExprType() {
	    return new TypeI(nodeType);
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void analyse() {
	    ((Node) expr).analyse();
	    nodeType.analyse();
	    if ((expr instanceof NodeVariable) && !((NodeVariable) expr).errored) {
		final Field field = ((NodeVariable) expr).var;
		if (field != null) if (!Scope.getScope().hasCastCheck(field, Semantics.getType(nodeType.id).get())) semanticWarning(line, column, UNCHECKED_CAST, field.qualifiedName.shortName, nodeType.id);
	    }
	}

	@Override
	public void generate() {}

    }

    public static class NodeAlias extends Node {
	public String alias;
	public NodeType type;

	public NodeAlias(final int line, final int column, final String alias, final NodeType type) {
	    super(line, column);
	    this.alias = alias;
	    this.type = type;
	}

	@Override
	public void preAnalyse() {
	    type.analyse();
	    if (Semantics.typeExists(alias)) semanticError(this, line, column, TYPE_ALREADY_EXISTS, alias);
	    else if (Semantics.aliases.containsKey(alias)) semanticError(this, line, column, ALIAS_ALREADY_EXISTS, alias);
	    else if (!type.errored) Semantics.addAlias(alias, Semantics.getType(type.id).get());
	}

	@Override
	public void generate() {}

    }

    public static class NodeRange extends Node implements IExpression {
	public IExpression start, end;
	public boolean exclusiveEnd;

	public NodeRange(final int line, final int column, final IExpression start, final IExpression end, final boolean exclusiveEnd) {
	    super(line, column);
	    this.start = start;
	    this.end = end;
	    this.exclusiveEnd = exclusiveEnd;
	}

	@Override
	public void analyse() {
	    ((Node) start).analyse();
	    ((Node) end).analyse();
	}

	@Override
	public TypeI getExprType() {
	    TypeI startType = null, endType = null;
	    if (!((Node) start).errored) {
		startType = start.getExprType();
		if (!startType.isPrimitive()) semanticError(this, line, column, EXPECTED_PRIMITIVE_TYPE, startType);
		else if (!startType.isNumeric()) semanticError(this, line, column, EXPECTED_NUMERIC_TYPE, startType);
	    } else errored = true;

	    if (!((Node) end).errored) {
		endType = end.getExprType();
		if (!endType.isPrimitive()) semanticError(this, line, column, EXPECTED_PRIMITIVE_TYPE, endType);
		else if (!endType.isNumeric()) semanticError(this, line, column, EXPECTED_NUMERIC_TYPE, endType);
	    } else errored = true;

	    if (!errored) {
		final TypeI result = new TypeI("Range", 0, false);
		result.genericTypes.add(Semantics.getPrecedentType(startType, endType));
		return result;
	    } else return null;
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    // Create a new Range object
	    addFuncStmt(new GenNodeNew("ash.lang.Range"));
	    addFuncStmt(new GenNodeOpcode(Opcodes.DUP));
	    // Call Range's constructor
	    start.generate();
	    end.generate();
	    addFuncStmt(new GenNodeFuncCall("java.lang.Range", "<init>", "(II)V", false, false, false, true));
	}

    }

    public static class NodeMatch extends Node implements IFuncStmt {
	public IExpression expr;
	public LinkedList<NodeMatchCase> matchCases = new LinkedList<NodeMatchCase>();

	public NodeMatch(final int line, final int column, final IExpression expr) {
	    super(line, column);
	    this.expr = expr;
	}

	@Override
	public void analyse() {
	    ((Node) expr).analyse();
	    if (!((Node) expr).errored) {
		final TypeI exprType = expr.getExprType();
		for (final NodeMatchCase matchCase : matchCases) {
		    matchCase.analyse();
		    if (!matchCase.isTerminatingCase && !matchCase.errored) for (final IExpression expr : matchCase.exprs) {
			TypeI caseType = expr.getExprType();
			// The generic used for ranges must be extracted
			if (caseType.isRange()) caseType = Semantics.getGeneric(caseType.genericTypes, 0);
			if (!exprType.canBeAssignedTo(caseType)) semanticError(this, matchCase.line, matchCase.column, INCOMPATIBLE_TYPES, exprType, caseType);
		    }
		}
	    }
	}

	public void add(final NodeMatchCase matchCase) {
	    matchCases.add(matchCase);
	}

	@Override
	public void generate() {
	    // TODO
	}

    }

    public static class NodeMatchCase extends Node {
	public LinkedList<IExpression> exprs = new LinkedList<IExpression>();
	public NodeFuncBlock block;
	public boolean isTerminatingCase;

	public NodeMatchCase(final int line, final int column, final IExpression expr, final NodeFuncBlock block) {
	    super(line, column);
	    exprs.add(expr);
	    this.block = block;
	    if (expr == null) isTerminatingCase = true;
	}

	@Override
	public void analyse() {
	    if (!isTerminatingCase) for (final IExpression expr : exprs) {
		((Node) expr).analyse();
		if (((Node) expr).errored) errored = true;
	    }
	    block.analyse();
	    if (block.errored) errored = true;
	}

	@Override
	public void generate() {
	    // TODO
	}

    }

    public static class NodeArrayAccess extends Node implements IExpression {
	public IExpression accessExpr;
	public IExpression expr;
	TypeI exprType, accessType;
	Function overloadFunc;

	public NodeArrayAccess(final int line, final int column, final IExpression expr, final IExpression accessExpr) {
	    super(line, column);
	    this.expr = expr;
	    this.accessExpr = accessExpr;
	}

	@Override
	public void analyse() {
	    ((Node) expr).analyse();

	    ((Node) accessExpr).analyse();
	    if (((Node) accessExpr).errored || ((Node) expr).errored) errored = true;
	}

	@Override
	public TypeI getExprType() {
	    if (!errored) {
		accessType = accessExpr.getExprType();
		exprType = expr.getExprType();
		if (exprType.isPrimitive() || exprType.isTuple() || exprType.isVoid()) semanticError(this, line, column, OPERATOR_CANNOT_BE_APPLIED_TO_TYPES, "[]", exprType, accessType);
		else if (exprType.isArray()){
		    if(accessType.isValidArrayAccessor()) return exprType.copy().setArrDims(exprType.arrDims - 1);
		    else semanticError(this, line, column, ARRAY_INDEX_NOT_NUMERIC, accessType);
		}else {
		    // Find an operator overload
		    final LinkedList<TypeI> params = new LinkedList<TypeI>();
		    params.add(accessExpr.getExprType());
		    overloadFunc = Semantics.getType(exprType.shortName).get().getFunc("[]", params);
		    if (overloadFunc == null) semanticError(this, line, column, FUNC_DOES_NOT_EXIST, "[]", exprType);
		    else if (!overloadFunc.isVisible()) semanticError(this, line, column, FUNC_IS_NOT_VISIBLE, "[]");
		    else return overloadFunc.returnType;
		}
	    }
	    return null;
	}

	@Override
	public void registerScopedChecks() {}

	@Override
	public void generate() {
	    expr.generate();
	    accessExpr.generate();
	    if(overloadFunc == null){
		GenNode.addFuncStmt(new GenNodeArrayIndexLoad(exprType.getInstructionType()));
	    }else{
		String enclosingType = overloadFunc.enclosingType.qualifiedName.toBytecodeName(), signature = "("+accessType.toBytecodeName()+")"+overloadFunc.returnType.toBytecodeName();
		boolean interfaceFunc = overloadFunc.enclosingType.type == EnumType.INTERFACE, privateFunc = BitOp.and(overloadFunc.modifiers, EnumModifier.PRIVATE.intVal);
		GenNode.addFuncStmt(new GenNodeFuncCall(enclosingType, "[]", signature, interfaceFunc, privateFunc, false, false));
	    }
	}

    }

}
