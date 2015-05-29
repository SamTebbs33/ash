package ashc.semantics;

/**
 * Ash
 * @author samtebbs, 21:02:48 - 24 May 2015
 */
public enum EnumPrimitive {

    BOOL("bool", "boolean"),
    DOUBLE("double", "double"),
    FLOAT("float", "float"),
    LONG("long", "long"),
    INT("int", "int"),
    SHORT("short", "short"),
    BYTE("byte", "byte"),
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