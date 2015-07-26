package ashc.grammar;

import java.util.*;

/**
 * Ash
 * @author samtebbs, 17:46:11 - 26 Jul 2015
 */
public class OperatorDef {
    
    public static HashMap<String, OperatorDef> operatorDefs = new HashMap<>();
    
    public String id;
    public EnumOperatorType type;
    public int precedence;
    public EnumOperatorAssociativity assoc;
    
    public static enum EnumOperatorType {
	BINARY, UNARY;

	public static EnumOperatorType get(String str) {
	    for(EnumOperatorType type : values()) if(type.name().toLowerCase().equals(str)) return type;
	    return null;
	}
    }
    
    public static enum EnumOperatorAssociativity {
	NONE, RIGHT, LEFT;
	
	public static EnumOperatorAssociativity get(String str) {
	    for(EnumOperatorAssociativity type : values()) if(type.name().toLowerCase().equals(str)) return type;
	    return null;
	}
    }

    public OperatorDef(String id, EnumOperatorType type, int precedence, EnumOperatorAssociativity assoc) {
	this.id = id;
	this.type = type;
	this.precedence = precedence;
	this.assoc = assoc;
    }
    
    public static boolean operatorDefExists(String id){
	return operatorDefs.containsKey(id);
    }
    
    public static void addOperatorDef(OperatorDef def){
	operatorDefs.put(def.id, def);
    }
    
    public static OperatorDef getOperatorDef(String id){
	return operatorDefs.containsKey(id) ? operatorDefs.get(id) : null;
    }

}
