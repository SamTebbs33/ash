package ashc.grammar;

/**
 * Ash
 * @author samtebbs, 16:10:22 - 26 May 2015
 */
public enum EnumModifier {

    /**
     * The java modifiers are mapped to their relevant values that are stored in
     * java classes.
     * The ash-specific modifiers are mapped to values not used by java
     * modifiers.
     */
    ABSTRACT(1024),
    OVERRIDE(512),
    NATIVE(256),
    REQUIRED(128),
    STANDARD(64),
    FINAL(16),
    STATIC(8),
    PRIVATE(2),
    PROTECTED(4),
    PUBLIC(1);

    public int intVal;

    private EnumModifier(final int intVal) {
	this.intVal = intVal;
    }

}
