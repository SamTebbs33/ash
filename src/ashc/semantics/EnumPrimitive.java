package ashc.semantics;

/**
 * Ash
 * @author samtebbs, 21:02:48 - 24 May 2015
 */
public enum EnumPrimitive {

    BOOL("bool", "boolean"),
    DOUBLE("float64", "double"),
    FLOAT("float", "float"),
    LONG("int64", "long"),
    INT("int", "int"),
    SHORT("int16", "short"),
    BYTE("int8", "byte"),
    ULONG("ulong", "long"),
    UINT("uint", "int"),
    CHAR("char", "char");

    public String ashName, javaName;

    private EnumPrimitive(final String ashName, final String javaName) {
	this.ashName = ashName;
	this.javaName = javaName;
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

}