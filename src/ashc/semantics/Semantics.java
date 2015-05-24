package ashc.semantics;

import java.util.HashMap;
import java.util.Stack;

import ashc.load.TypeImporter;
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

	public TypeI(final String shortName, final int arrDims) {
	    this.shortName = shortName;
	    this.arrDims = arrDims;
	}

	public static TypeI fromClass(final Class cls) {
	    String clsName = cls.getName();
	    int arrDims = clsName.length();
	    clsName = clsName.replace("[", "");
	    arrDims = arrDims - clsName.length();
	    final String shortName = clsName.substring(clsName.lastIndexOf('.') + 1);
	    if (clsName.charAt(0) == 'L'){
		TypeImporter.loadClass(clsName.substring(1).replace(";", ""));
	    }
	    return new TypeI(shortName, arrDims);
	}

    }

    public static boolean typeExists(final String typeName) {
	return typeNameMap.containsKey(typeName);
    }

    public static void addType(final Type type) {
	types.put(type.qualifiedName, type);
	typeStack.push(type);
    }

    public static void bindName(final String shortName, final QualifiedName qualifiedName) {
	typeNameMap.put(shortName, qualifiedName);
    }

    public static void bindName(QualifiedName qualifiedName) {
	bindName(qualifiedName.shortName, qualifiedName);
    }

    public static boolean bindingExists(String shortName) {
	return typeNameMap.containsKey(shortName);
    }

}
