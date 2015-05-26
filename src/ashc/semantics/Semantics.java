package ashc.semantics;

import java.util.*;

import ashc.load.*;
import ashc.semantics.Member.Field;
import ashc.semantics.Member.Function;
import ashc.semantics.Member.Type;

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
	    return "TypeI [shortName=" + shortName + ", arrDims=" + arrDims + "]";
	}

    }

    public static boolean typeExists(final String typeName) {
	return EnumPrimitive.isPrimitive(typeName) || typeNameMap.containsKey(typeName);
    }

    public static void addType(final Type type) {
	types.put(type.qualifiedName, type);
	typeStack.push(type);
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

}
