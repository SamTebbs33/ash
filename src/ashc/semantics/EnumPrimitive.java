package ashc.semantics;

import ashc.codegen.GenNode.EnumInstructionOperand;

/**
 * Ash
 *
 * @author samtebbs, 21:02:48 - 24 May 2015
 */
public enum EnumPrimitive {

    BOOL("bool", "boolean", 'Z', false, EnumInstructionOperand.BOOL, false),
    DOUBLE("double", "double", 'D', true, EnumInstructionOperand.DOUBLE, false),
    FLOAT("float", "float", 'F', true, EnumInstructionOperand.FLOAT, false),
    LONG("long", "long", 'J', true, EnumInstructionOperand.LONG, false),
    INT("int", "int", 'I', true, EnumInstructionOperand.INT, true),
    SHORT("short", "short", 'S', true, EnumInstructionOperand.SHORT, true),
    BYTE("byte", "byte", 'B', true, EnumInstructionOperand.BYTE, true),
    UBYTE("ubyte", "byte", 'B', true, EnumInstructionOperand.BYTE, false),
    USHORT("ushort", "short", 'S', true, EnumInstructionOperand.SHORT, false),
    ULONG("ulong", "long", 'J', true, EnumInstructionOperand.LONG, false),
    UINT("uint", "int", 'I', true, EnumInstructionOperand.INT, false),
    CHAR("char", "char", 'C', false, EnumInstructionOperand.CHAR, false);

    public String ashName, javaName;
    public char bytecodeChar;
    public boolean validForArrayIndex, isNumeric;
    public EnumInstructionOperand instructionType;

    EnumPrimitive(final String ashName, final String javaName, final char bytecodeChar, final boolean isNumeric, final EnumInstructionOperand type, final boolean wholeNumber) {
        this.ashName = ashName;
        this.javaName = javaName;
        this.bytecodeChar = bytecodeChar;
        this.isNumeric = isNumeric;
        instructionType = type;
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

    public static boolean isJavaPrimitive(final String typeName) {
        for (final EnumPrimitive p : EnumPrimitive.values())
            if (p.javaName.equals(typeName)) return true;
        return false;
    }

    public static EnumPrimitive getFromJavaPrimitive(final String clsName) {
        for (final EnumPrimitive p : EnumPrimitive.values())
            if (p.javaName.equals(clsName)) return p;
        return null;
    }

    public static EnumPrimitive getFromBytecodePrimitive(final char ch) {
        for (final EnumPrimitive p : EnumPrimitive.values())
            if (p.bytecodeChar == ch) return p;
        return null;
    }

}
