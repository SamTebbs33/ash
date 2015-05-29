package ashc.semantics;

import java.util.*;

import ashc.grammar.Node.NodeExprs;
import ashc.load.*;
import ashc.semantics.Member.Field;
import ashc.semantics.Member.Function;
import ashc.semantics.Member.Type;
import ashc.semantics.Semantics.TypeI;

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

	public TypeI(final String shortName, final int arrDims, final boolean optional) {
	    this.shortName = shortName;
	    this.arrDims = arrDims;
	    this.optional = optional;
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
	    StringBuffer arrBuffer = new StringBuffer();
	    for(int i = 0; i < arrDims; i++) arrBuffer.append("[]");
	    return String.format("%s%s%s", shortName, optional ? "?" : "", arrBuffer.toString());
	}

	public boolean isVoid() {
	    return shortName.equals("void");
	}

	public boolean canBeAssignedTo(TypeI exprType) {
	    if(this.equals(exprType)) return true;
	    return exprType.arrDims == this.arrDims && this.optional == exprType.optional && (exprType.shortName.equals("null") || Semantics.typeHasSuper(exprType.shortName, this.shortName));
	}

    }

    public static boolean typeExists(final String typeName) {
	return typeName.equals("void") || EnumPrimitive.isPrimitive(typeName) || typeNameMap.containsKey(typeName);
    }

    public static boolean typeHasSuper(String type, String superType) {
	Optional<Type> t = getType(type), superT = getType(superType);
	if(t.isPresent() && superT.isPresent()){
	   return t.get().hasSuper(superT.get().qualifiedName);
	}
	return false;
    }

    public static void addType(final Type type) {
	types.put(type.qualifiedName, type);
	typeStack.push(type);
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

    public static TypeI getVarType(final String id, final TypeI type) {
	if (type.arrDims > 0 && id.equals("length")) return new TypeI(EnumPrimitive.INT);
	else {
	    final Optional<Type> t = getType(type.shortName);
	    if (t.isPresent()) return t.get().getField(id).type;
	}
	return null;
    }

    public static TypeI getVarType(final String id) {
	return getVarType(id, new TypeI(typeStack.peek().qualifiedName.shortName, 0, false));
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

}
