package ashc.semantics;

import java.util.LinkedList;

import ashc.semantics.Member.Type;

/**
 * Ash
 * @author samtebbs, 15:09:18 - 23 May 2015
 */
public class Semantics {
    
    public static LinkedList<Type> types = new LinkedList<Type>();
    
    public Semantics() {}
    
    public boolean typeExists(String typeName){
	return types.contains(typeName);
    }

}
