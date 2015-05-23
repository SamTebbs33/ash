package ashc.semantics;

/**
 * Ash
 * @author samtebbs, 15:02:05 - 23 May 2015
 */
public class Member {
    
    public QualifiedName qualifiedName;
    public short modifiers;

    public Member(QualifiedName qualifiedName, short modifiers) {
	this.qualifiedName = qualifiedName;
	this.modifiers = modifiers;
    }
    
    public static enum EnumType {
	CLASS,
	ENUM,
	INTERFACE	
    }
    
    public static class Type extends Member {
	public EnumType type;
	public Type(QualifiedName qualifiedName, short modifiers, EnumType type) {
	    super(qualifiedName, modifiers);
	    this.type = type;
	}
	@Override
	public boolean equals(Object obj) {
	    if(obj instanceof Type) return qualifiedName.shortName.equals(((Type) obj).qualifiedName.shortName);
	    else if(obj instanceof String) return qualifiedName.shortName.equals((String)obj);
	    else return false;
	}
	
    }
    
    public static class Function extends Member {

	public Function(QualifiedName qualifiedName, short modifiers) {
	    super(qualifiedName, modifiers);
	}
	
    }
    
    public static class Field extends Member {

	public Field(QualifiedName qualifiedName, short modifiers) {
	    super(qualifiedName, modifiers);
	}
	
    }

}
