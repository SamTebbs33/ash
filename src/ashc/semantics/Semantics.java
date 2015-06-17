package ashc.semantics;

import java.util.*;

import ashc.grammar.Node.NodeExprs;
import ashc.grammar.Node.NodeType;
import ashc.load.*;
import ashc.semantics.Member.Field;
import ashc.semantics.Member.Function;
import ashc.semantics.Member.Type;
import ashc.semantics.Member.Variable;
import ashc.semantics.Scope.FuncScope;

/**
 * Ash
 * @author samtebbs, 15:09:18 - 23 May 2015
 */
public class Semantics {

    public static HashMap<QualifiedName, Type> types = new HashMap<QualifiedName, Type>();
    public static HashMap<String, QualifiedName> typeNameMap = new HashMap<String, QualifiedName>();
    public static Stack<Type> typeStack = new Stack<Type>();

    public static class TypeI {

	public String shortName;
	public int arrDims;
	public boolean optional;
	public LinkedList<TypeI> tupleTypes;

	public TypeI(final String shortName, final int arrDims, final boolean optional) {
	    this.shortName = shortName;
	    this.arrDims = arrDims;
	    this.optional = optional;
	    this.tupleTypes = new LinkedList<TypeI>();
	}
	
	public TypeI(NodeType type){
	    this(type.id, type.arrDims, type.optional);
	    for(NodeType nodeType : type.tupleTypes) tupleTypes.add(new TypeI(nodeType));
	}

	public TypeI(final EnumPrimitive primitive) {
	    shortName = primitive.ashName;
	}

	public TypeI(final EnumPrimitive primitive, final int arrDims) {
	    shortName = primitive.ashName;
	    this.arrDims = arrDims;
	}

	public static TypeI fromClass(final Class cls) {
	    String clsName = cls.getName();
	    int arrDims = clsName.length();
	    clsName = clsName.replace("[", "");
	    arrDims = arrDims - clsName.length();
	    final String shortName = clsName.substring(clsName.lastIndexOf('.') + 1);
	    if (clsName.charAt(0) == 'L') TypeImporter.loadClass(clsName.substring(1).replace(";", ""));
	    return new TypeI(shortName, arrDims, false);
	}

	@Override
	public boolean equals(final Object obj) {
	    if (obj instanceof TypeI) {
		final TypeI type = (TypeI) obj;
		return type.shortName.equals(shortName) && type.arrDims == arrDims;
	    }
	    return false;
	}

	@Override
	public String toString() {
	    if (isNull() || isVoid()) return shortName;
	    final StringBuffer arrBuffer = new StringBuffer();
	    for (int i = 0; i < arrDims; i++)
		arrBuffer.append("[]");
	    String id = shortName;
	    if(isTuple()){
		id = "(";
		for(TypeI tupleType : tupleTypes) id += tupleType.toString() + ",";
		id += ")";
	    }
	    return String.format("%s%s%s", id, arrBuffer.toString(), optional ? "?" : "");
	}

	public boolean isVoid() {
	    return !isTuple() ? shortName.equals("void") : false;
	}

	public boolean canBeAssignedTo(final TypeI exprType) {
	    if (exprType == null) return false;
	    if (equals(exprType)) return true;
	    // If the expr is null, and this is optional, and it has more than 0
	    // array dimensions
	    if (exprType.isNull() && optional && (!EnumPrimitive.isPrimitive(shortName) || arrDims > 0)) return true;
	    // If they are both numeric and the array dimensions are 0
	    if (EnumPrimitive.isNumeric(shortName) && EnumPrimitive.isNumeric(exprType.shortName) && arrDims == exprType.arrDims) return true;
	    return exprType.arrDims == arrDims && optional == exprType.optional && (exprType.isNull() && !EnumPrimitive.isNumeric(shortName) || Semantics.typeHasSuper(exprType.shortName, shortName));
	}

	public boolean isNull() {
	    return !isTuple() ? shortName.equals("null") : false;
	}
	
	public boolean isTuple(){
	    return tupleTypes != null && tupleTypes.size() > 0;
	}

    }

    public static boolean typeExists(final String typeName) {
	return typeName.equals("void") || EnumPrimitive.isPrimitive(typeName) || typeNameMap.containsKey(typeName);
    }

    public static boolean typeHasSuper(final String type, final String superType) {
	final Optional<Type> t = getType(type), superT = getType(superType);
	if (t.isPresent() && superT.isPresent()) return t.get().hasSuper(superT.get().qualifiedName);
	return false;
    }

    public static void addType(final Type type) {
	types.put(type.qualifiedName, type);
	enterType(type);
	bindName(type.qualifiedName);
    }

    public static void exitType() {
	typeStack.pop();
    }

    public static void bindName(final String shortName, final QualifiedName qualifiedName) {
	typeNameMap.put(shortName, qualifiedName);
    }

    public static void bindName(final QualifiedName qualifiedName) {
	bindName(qualifiedName.shortName, qualifiedName);
    }

    public static boolean bindingExists(final String shortName) {
	return typeNameMap.containsKey(shortName);
    }

    public static Optional<Type> getType(final String id) {
	final QualifiedName typeName = typeNameMap.get(id);
	return typeName != null ? Optional.of(types.get(typeName)) : Optional.empty();
    }

    public static boolean funcExists(final Function func) {
	return typeStack.peek().functions.contains(func);
    }

    public static void addFunc(final Function func) {
	typeStack.peek().functions.add(func);
    }

    public static boolean fieldExists(final Field field) {
	for (final Field f : typeStack.peek().fields)
	    if (f.equals(field)) return true;
	return false;
    }

    public static void addField(final Field field) {
	typeStack.peek().fields.add(field);
    }

    public static TypeI getPrecedentType(final TypeI type1, final TypeI type2) {
	if (type1.equals(type2)) return type1;
	final String name1 = type1.shortName, name2 = type2.shortName;

	if (name1.equals("String") && type1.arrDims == 0 || name2.equals("String") && type2.arrDims == 0) return new TypeI("String", 0, false);

	for (final EnumPrimitive p : EnumPrimitive.values())
	    if (p.ashName.equals(name1) || p.ashName.equals(name2)) return new TypeI(p);

	return null;
    }

    public static Variable getVar(final String id, final TypeI type) {
	if (type.arrDims > 0 && id.equals("length")) return new Variable("length", new TypeI(EnumPrimitive.INT));
	else {
	    final Optional<Type> t = getType(type.shortName);
	    if (t.isPresent()) return t.get().getField(id);
	}
	return null;
    }

    public static Variable getVar(final String id) {
	// Look in scope
	for (final Variable var : Scope.getScope().vars)
	    if (var.id.equals(id)) return var;
	// Else, look in the current type
	return getVar(id, new TypeI(typeStack.peek().qualifiedName.shortName, 0, false));
    }

    public static TypeI getFuncType(final String id, final TypeI type, final NodeExprs args) {
	if (type.arrDims > 0 && id.equals("toString()")) return new TypeI("String", 0, false);
	else {
	    final Optional<Type> t = getType(type.shortName);
	    if (t.isPresent()) return t.get().getFuncType(id, args);
	}
	return null;
    }

    public static TypeI getFuncType(final String id, final NodeExprs args) {
	return getFuncType(id, new TypeI(typeStack.peek().qualifiedName.shortName, 0, false), args);
    }

    public static Function getFunc(final String id, final TypeI type, final NodeExprs args) {
	final Optional<Type> t = getType(type.shortName);
	if (t.isPresent()) return t.get().getFunc(id, args);
	return null;
    }

    public static Function getFunc(final String id, final NodeExprs args) {
	return getFunc(id, new TypeI(typeStack.peek().qualifiedName.shortName, 0, false), args);
    }

    public static void enterType(final Type type) {
	typeStack.push(type);
    }

    public static boolean varExists(final String id) {
	Scope scope = Scope.getScope();
	if (scope != null && scope.hasVar(id)) return true;
	for (final Field field : typeStack.peek().fields){
	    System.out.println(field);
	    if (field.qualifiedName.shortName.equals(id)) return true;
	}
	return false;
    }

    public static void addVar(final Variable variable) {
	if (Scope.getScope() instanceof FuncScope) 
	    // We are in a function, so add a local variable to the function's scope
	    Scope.getScope().addVar(variable);
    }

}
