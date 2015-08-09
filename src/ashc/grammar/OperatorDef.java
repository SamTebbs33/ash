package ashc.grammar;

import java.util.*;

import ashc.codegen.GenNode.EnumInstructionOperand;
import ashc.grammar.OperatorDef.OperatorDefNative.NativeOpInfo;

import org.objectweb.asm.*;

/**
 * Ash
 * 
 * @author samtebbs, 17:46:11 - 26 Jul 2015
 */
public class OperatorDef {

    public static HashMap<String, OperatorDef> operatorDefs = new HashMap<>();

    public String id, name;
    public EnumOperatorType type;
    public int precedence;
    public EnumOperatorAssociativity assoc;

    public static enum EnumOperatorType {
	BINARY('0'), PREFIX('1'), POSTFIX('2');
	
	public char opIdPrefix;
	
	private EnumOperatorType(char ch){
	    this.opIdPrefix = ch;
	}

	public static EnumOperatorType get(final String str) {
	    for (final EnumOperatorType type : values())
		if (type.name().toLowerCase().equals(str)) return type;
	    return null;
	}
    }

    public static enum EnumOperatorAssociativity {
	NONE, RIGHT, LEFT;

	public static EnumOperatorAssociativity get(final String str) {
	    for (final EnumOperatorAssociativity type : values())
		if (type.name().toLowerCase().equals(str)) return type;
	    return null;
	}
    }

    public static enum EnumOperation {
	INCREMENT,
	DECREMENT,
	ADD,
	SUBTRACT,
	MULTIPLY,
	DIVIDE,
	MOD,
	POW,
	AND,
	OR,
	XOR,
	NOT,
	BIT_AND,
	BIT_OR,
	BIT_XOR,
	BIT_NOT,
	LESS,
	GREATER,
	EQUAL,
	NOT_EQUAL,
	LESS_EQUAL,
	GREATER_EQUAL,
	L_SHIFT,
	R_SHIFT,
	R_SHIFT_LOGICAL,
	UNDEFINED;

    }

    static {

	final NativeOpInfo[] equalityOpInfo = new NativeOpInfo[] { new NativeOpInfo(-1, EnumInstructionOperand.BYTE, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.CHAR, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.SHORT, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.INT, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.FLOAT, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.DOUBLE, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.LONG, EnumInstructionOperand.LONG) };

	addOperatorDef(new OperatorDef("?", "ternary", EnumOperatorType.BINARY));

	addOperatorDef(new OperatorDefNative(EnumOperation.ADD, "+", "plus", EnumOperatorType.BINARY, 90, EnumOperatorAssociativity.LEFT, new NativeOpInfo(Opcodes.IADD, EnumInstructionOperand.BYTE), new NativeOpInfo(Opcodes.IADD, EnumInstructionOperand.CHAR), new NativeOpInfo(Opcodes.IADD, EnumInstructionOperand.SHORT), new NativeOpInfo(Opcodes.IADD, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.LADD, EnumInstructionOperand.LONG), new NativeOpInfo(Opcodes.IADD, EnumInstructionOperand.FLOAT), new NativeOpInfo(Opcodes.DADD, EnumInstructionOperand.DOUBLE)));

	addOperatorDef(new OperatorDefNative(EnumOperation.SUBTRACT, "-", "minus", EnumOperatorType.BINARY, 90, EnumOperatorAssociativity.LEFT, new NativeOpInfo(Opcodes.ISUB, EnumInstructionOperand.BYTE), new NativeOpInfo(Opcodes.ISUB, EnumInstructionOperand.CHAR), new NativeOpInfo(Opcodes.ISUB, EnumInstructionOperand.SHORT), new NativeOpInfo(Opcodes.ISUB, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.LSUB, EnumInstructionOperand.LONG), new NativeOpInfo(Opcodes.ISUB, EnumInstructionOperand.FLOAT), new NativeOpInfo(Opcodes.DSUB, EnumInstructionOperand.DOUBLE)));

	addOperatorDef(new OperatorDefNative(EnumOperation.MULTIPLY, "*", "multiply", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT, new NativeOpInfo(Opcodes.IMUL, EnumInstructionOperand.BYTE), new NativeOpInfo(Opcodes.IMUL, EnumInstructionOperand.CHAR), new NativeOpInfo(Opcodes.IMUL, EnumInstructionOperand.SHORT), new NativeOpInfo(Opcodes.IMUL, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.LMUL, EnumInstructionOperand.LONG), new NativeOpInfo(Opcodes.IMUL, EnumInstructionOperand.FLOAT), new NativeOpInfo(Opcodes.DMUL, EnumInstructionOperand.DOUBLE)));

	addOperatorDef(new OperatorDefNative(EnumOperation.DIVIDE, "/", "divide", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT, new NativeOpInfo(Opcodes.IDIV, EnumInstructionOperand.BYTE), new NativeOpInfo(Opcodes.IDIV, EnumInstructionOperand.CHAR), new NativeOpInfo(Opcodes.IDIV, EnumInstructionOperand.SHORT), new NativeOpInfo(Opcodes.IDIV, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.LDIV, EnumInstructionOperand.LONG), new NativeOpInfo(Opcodes.IDIV, EnumInstructionOperand.FLOAT), new NativeOpInfo(Opcodes.DDIV, EnumInstructionOperand.DOUBLE)));

	addOperatorDef(new OperatorDefNative(EnumOperation.MOD, "%", "mod", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT, new NativeOpInfo(Opcodes.IREM, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IREM, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IREM, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IREM, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.LREM, EnumInstructionOperand.LONG, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.DREM, EnumInstructionOperand.DOUBLE, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.FREM, EnumInstructionOperand.FLOAT, EnumInstructionOperand.INT)));

	addOperatorDef(new OperatorDefNative(EnumOperation.UNDEFINED, "!", "optional_unwrap", EnumOperatorType.POSTFIX, 0, EnumOperatorAssociativity.NONE));
	addOperatorDef(new OperatorDefNative(EnumOperation.OR, "||", "or", EnumOperatorType.BINARY, 10, EnumOperatorAssociativity.LEFT, new NativeOpInfo(-1, EnumInstructionOperand.BOOL)));
	addOperatorDef(new OperatorDefNative(EnumOperation.AND, "&&", "and", EnumOperatorType.BINARY, 20, EnumOperatorAssociativity.LEFT, new NativeOpInfo(-1, EnumInstructionOperand.BOOL)));
	addOperatorDef(new OperatorDefNative(EnumOperation.BIT_OR, "|", "bit_or", EnumOperatorType.BINARY, 30, EnumOperatorAssociativity.LEFT, new NativeOpInfo(Opcodes.IOR, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IOR, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IOR, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IOR, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.LOR, EnumInstructionOperand.LONG, EnumInstructionOperand.LONG)));

	addOperatorDef(new OperatorDefNative(EnumOperation.XOR, "^", "bit_xor", EnumOperatorType.BINARY, 40, EnumOperatorAssociativity.LEFT, new NativeOpInfo(Opcodes.IXOR, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IXOR, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IXOR, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IXOR, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.LXOR, EnumInstructionOperand.LONG, EnumInstructionOperand.LONG)));

	addOperatorDef(new OperatorDefNative(EnumOperation.BIT_AND, "&", "bit_and", EnumOperatorType.BINARY, 50, EnumOperatorAssociativity.LEFT, new NativeOpInfo(Opcodes.IAND, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IAND, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IAND, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IAND, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.LAND, EnumInstructionOperand.LONG, EnumInstructionOperand.LONG)));

	addOperatorDef(new OperatorDefNative(EnumOperation.EQUAL, "==", "equal", EnumOperatorType.BINARY, 60, EnumOperatorAssociativity.NONE, new NativeOpInfo(-1, EnumInstructionOperand.BYTE, EnumInstructionOperand.BOOL), new NativeOpInfo(-1, EnumInstructionOperand.CHAR, EnumInstructionOperand.BOOL), new NativeOpInfo(-1, EnumInstructionOperand.SHORT, EnumInstructionOperand.BOOL), new NativeOpInfo(-1, EnumInstructionOperand.INT, EnumInstructionOperand.BOOL), new NativeOpInfo(-1, EnumInstructionOperand.FLOAT, EnumInstructionOperand.BOOL), new NativeOpInfo(-1, EnumInstructionOperand.DOUBLE, EnumInstructionOperand.BOOL), new NativeOpInfo(-1, EnumInstructionOperand.LONG, EnumInstructionOperand.LONG), new NativeOpInfo(-1, EnumInstructionOperand.ARRAY, EnumInstructionOperand.BOOL), new NativeOpInfo(-1, EnumInstructionOperand.REFERENCE, EnumInstructionOperand.BOOL)));

	addOperatorDef(new OperatorDefNative(EnumOperation.NOT_EQUAL, "!=", "not_equal", EnumOperatorType.BINARY, 60, EnumOperatorAssociativity.LEFT, equalityOpInfo));
	addOperatorDef(new OperatorDefNative(EnumOperation.LESS, "<", "less", EnumOperatorType.BINARY, 70, EnumOperatorAssociativity.LEFT, equalityOpInfo));
	addOperatorDef(new OperatorDefNative(EnumOperation.GREATER, ">", "greater", EnumOperatorType.BINARY, 70, EnumOperatorAssociativity.LEFT, equalityOpInfo));
	addOperatorDef(new OperatorDefNative(EnumOperation.LESS_EQUAL, "<=", "less_equal", EnumOperatorType.BINARY, 70, EnumOperatorAssociativity.LEFT, equalityOpInfo));
	addOperatorDef(new OperatorDefNative(EnumOperation.GREATER_EQUAL, ">=", "greater_equal", EnumOperatorType.BINARY, 70, EnumOperatorAssociativity.LEFT, equalityOpInfo));

	addOperatorDef(new OperatorDefNative(EnumOperation.L_SHIFT, "<<", "left_shift", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT, new NativeOpInfo(Opcodes.ISHL, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.ISHL, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT, EnumInstructionOperand.BYTE), new NativeOpInfo(Opcodes.ISHL, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT, EnumInstructionOperand.CHAR), new NativeOpInfo(Opcodes.ISHL, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT, EnumInstructionOperand.SHORT), new NativeOpInfo(Opcodes.LSHL, EnumInstructionOperand.LONG, EnumInstructionOperand.INT, EnumInstructionOperand.LONG)));

	addOperatorDef(new OperatorDefNative(EnumOperation.R_SHIFT, ">>", "right_shift", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT, new NativeOpInfo(Opcodes.ISHR, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.ISHR, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT, EnumInstructionOperand.BYTE), new NativeOpInfo(Opcodes.ISHR, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT, EnumInstructionOperand.CHAR), new NativeOpInfo(Opcodes.ISHR, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT, EnumInstructionOperand.SHORT), new NativeOpInfo(Opcodes.LSHR, EnumInstructionOperand.LONG, EnumInstructionOperand.INT, EnumInstructionOperand.LONG)));

	addOperatorDef(new OperatorDefNative(EnumOperation.R_SHIFT_LOGICAL, ">>>", "logical_right_shift", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT, new NativeOpInfo(Opcodes.IUSHR, EnumInstructionOperand.INT), new NativeOpInfo(Opcodes.IUSHR, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT, EnumInstructionOperand.BYTE), new NativeOpInfo(Opcodes.IUSHR, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT, EnumInstructionOperand.CHAR), new NativeOpInfo(Opcodes.IUSHR, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT, EnumInstructionOperand.SHORT), new NativeOpInfo(Opcodes.LUSHR, EnumInstructionOperand.LONG, EnumInstructionOperand.INT, EnumInstructionOperand.LONG)));
    }

    public OperatorDef(final String id, final String name, final EnumOperatorType type, final int precedence, final EnumOperatorAssociativity assoc) {
	this.id = id;
	this.name = name;
	this.type = type;
	this.precedence = precedence;
	this.assoc = assoc;
    }

    public OperatorDef(final String id, final String name, final EnumOperatorType unary) {
	this(id, name, unary, 1000, EnumOperatorAssociativity.NONE);
    }

    public static boolean operatorDefExists(final String id, EnumOperatorType type) {
	return operatorDefs.containsKey(type.opIdPrefix+id);
    }

    public static void addOperatorDef(final OperatorDef def) {
	operatorDefs.put(def.type.opIdPrefix+def.id, def);
    }

    public static OperatorDef getOperatorDef(final String id, EnumOperatorType type) {
	return operatorDefs.containsKey(type.opIdPrefix+id) ? operatorDefs.get(type.opIdPrefix+id) : null;
    }

    public static class OperatorDefNative extends OperatorDef {

	public static class NativeOpInfo {
	    public EnumInstructionOperand type1, type2, retType;
	    public int opcode; // -1 means that this is a special case and that opcode generation is handled by the code generator

	    public NativeOpInfo(final int opcode, final EnumInstructionOperand type1, final EnumInstructionOperand type2, final EnumInstructionOperand ret) {
		this.type1 = type1;
		this.type2 = type2;
		retType = ret;
		this.opcode = opcode;
	    }

	    public NativeOpInfo(final int opcode, final EnumInstructionOperand type, final EnumInstructionOperand retType) {
		this(opcode, type, type, retType);
	    }

	    public NativeOpInfo(final int opcode, final EnumInstructionOperand type) {
		this(opcode, type, type, type);
	    }
	}

	public NativeOpInfo[] opInfo;
	public EnumOperation operation;

	public OperatorDefNative(final EnumOperation operation, final String id, final String name, final EnumOperatorType type, final int precedence, final EnumOperatorAssociativity assoc, final NativeOpInfo... opInfo) {
	    super(id, name, type, precedence, assoc);
	    this.opInfo = opInfo;
	    this.operation = operation;
	}

    }

    public static boolean operatorNameExists(final String name2) {
	for (final OperatorDef def : operatorDefs.values())
	    if (def.name.equals(name2)) return true;
	return false;
    }

    public static String filterOperators(final String shortName) {
	for(EnumOperatorType type : EnumOperatorType.values()){
	    if(operatorDefExists(shortName, type)) return "$" + getOperatorDef(shortName, type).name;
	}
	return shortName;
    }

}
