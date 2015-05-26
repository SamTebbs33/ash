package ashc.semantics;

/**
 * Ash
 * @author samtebbs, 21:02:48 - 24 May 2015
 */
public enum EnumPrimitive {

    BYTE("int8", "byte"),
    SHORT("int16", "short"),
    INT("int", "int"),
    LONG("int64", "long"),
    FLOAT("float", "float"),
    DOUBLE("float64", "double"),
    CHAR("char", "char"),
    BOOL("bool", "boolean");
    
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
    
    public static EnumPrimitive getPrimitive(String name){
	for (final EnumPrimitive p : EnumPrimitive.values())
	    if (p.ashName.equals(name)) return p;
	return null;
    }

}