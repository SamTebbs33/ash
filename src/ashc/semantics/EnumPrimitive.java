package ashc.semantics;

/**
 * Ash
 * @author samtebbs, 21:02:48 - 24 May 2015
 */
public enum EnumPrimitive {

    BYTE("int8", "byte"), SHORT("int16", "short"), INT("int", "int"), LONG("int64", "long"), FLOAT("float", "float"), DOUBLE("float64", "double"), CHAR("char", "char"), BOOL("bool", "boolean");
    
    public String ashName, javaName;
    
    private EnumPrimitive(String ashName, String javaName){
	this.ashName = ashName;
	this.javaName = javaName;
    }

    public static boolean isPrimitive(String typeName) {
	for(EnumPrimitive p : EnumPrimitive.values()) if(p.ashName.equals(typeName)) return true;
	return false;
    }
    
}