package ashc.semantics;

import ashc.semantics.Semantics.TypeI;

/**
 * Ash
 *
 * @author samtebbs, 21:02:48 - 24 May 2015
 */
public enum EnumPrimitive {

    BOOL("bool", "boolean", false, false),
    DOUBLE("double", "double", true, false),
    FLOAT("float", "float", true, false),
    LONG("long", "long", true, false),
    INT("int", "int", true, true),
    SHORT("short", "short", true, true),
    BYTE("byte", "byte", true, true),
    ULONG("ulong", "long", true, false),
    UINT("uint", "int", true, false),
    CHAR("char", "char", false, false);

    public String ashName, javaName;
    public boolean validForArrayIndex, isNumeric;

    private EnumPrimitive(final String ashName, final String javaName, final boolean isNumeric, final boolean wholeNumber) {
	this.ashName = ashName;
	this.javaName = javaName;
	this.isNumeric = isNumeric;
	validForArrayIndex = wholeNumber;
    }

    public static boolean isPrimitive(final String typeName) {
	for (final EnumPrimitive p : EnumPrimitive.values())
	    if (p.ashName.equals(typeName)) return true;
	return false;
    }

    public static EnumPrimitive getPrimitive(final String name) {
	for (final EnumPrimitive p : EnumPrimitive.values())
	    if (p.ashName.equals(name)) return p;
	return null;
    }

    public static boolean validForArrayIndex(final TypeI indexType) {
	return isPrimitive(indexType.shortName) && (indexType.arrDims == 0) && getPrimitive(indexType.shortName).validForArrayIndex;
    }

    public static boolean isNumeric(final String shortName) {
	final EnumPrimitive p = getPrimitive(shortName);
	if (p != null) return p.isNumeric;
	return false;
    }

    public static boolean isJavaPrimitive(String typeName) {
	for (final EnumPrimitive p : EnumPrimitive.values())
	    if (p.javaName.equals(typeName)) return true;
	return false;
    }

}