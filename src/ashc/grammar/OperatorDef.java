package ashc.grammar;

import java.util.*;

import ashc.codegen.GenNode.EnumInstructionOperand;
import ashc.grammar.OperatorDef.OperatorDefNative.NativeOpInfo;

import com.sun.xml.internal.ws.org.objectweb.asm.*;

/**
 * Ash
 * @author samtebbs, 17:46:11 - 26 Jul 2015
 */
public class OperatorDef {
    
    public static HashMap<String, OperatorDef> operatorDefs = new HashMap<>();
    
    public String id, name;
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
	R_SHIFT_LOGICAL;

    }

    static {
	
	NativeOpInfo[] equalityOpInfo = new NativeOpInfo[] {
		new NativeOpInfo(-1, EnumInstructionOperand.BYTE, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.CHAR, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.SHORT, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.INT, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.FLOAT, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.DOUBLE, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.LONG, EnumInstructionOperand.LONG)};
	
	addOperatorDef(new OperatorDefNative(EnumOperation.ADD, "+", "plus", EnumOperatorType.BINARY, 90, EnumOperatorAssociativity.LEFT, 
		new NativeOpInfo(Opcodes.IADD, EnumInstructionOperand.BYTE),
		new NativeOpInfo(Opcodes.IADD, EnumInstructionOperand.CHAR),
		new NativeOpInfo(Opcodes.IADD, EnumInstructionOperand.SHORT),
		new NativeOpInfo(Opcodes.IADD, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.LADD, EnumInstructionOperand.LONG),
		new NativeOpInfo(Opcodes.IADD, EnumInstructionOperand.FLOAT),
		new NativeOpInfo(Opcodes.DADD, EnumInstructionOperand.DOUBLE)));
	
	addOperatorDef(new OperatorDefNative(EnumOperation.SUBTRACT, "-", "minus", EnumOperatorType.BINARY, 90, EnumOperatorAssociativity.LEFT, 
		new NativeOpInfo(Opcodes.ISUB, EnumInstructionOperand.BYTE),
		new NativeOpInfo(Opcodes.ISUB, EnumInstructionOperand.CHAR),
		new NativeOpInfo(Opcodes.ISUB, EnumInstructionOperand.SHORT),
		new NativeOpInfo(Opcodes.ISUB, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.LSUB, EnumInstructionOperand.LONG),
		new NativeOpInfo(Opcodes.ISUB, EnumInstructionOperand.FLOAT),
		new NativeOpInfo(Opcodes.DSUB, EnumInstructionOperand.DOUBLE)));
	
	addOperatorDef(new OperatorDefNative(EnumOperation.MULTIPLY, "*", "multiply", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT,  
		new NativeOpInfo(Opcodes.IMUL, EnumInstructionOperand.BYTE),
		new NativeOpInfo(Opcodes.IMUL, EnumInstructionOperand.CHAR),
		new NativeOpInfo(Opcodes.IMUL, EnumInstructionOperand.SHORT),
		new NativeOpInfo(Opcodes.IMUL, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.LMUL, EnumInstructionOperand.LONG),
		new NativeOpInfo(Opcodes.IMUL, EnumInstructionOperand.FLOAT),
		new NativeOpInfo(Opcodes.DMUL, EnumInstructionOperand.DOUBLE)));
	
	addOperatorDef(new OperatorDefNative(EnumOperation.POW, "**", "pow", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT,
		new NativeOpInfo(-1, EnumInstructionOperand.DOUBLE)));
	
	addOperatorDef(new OperatorDefNative(EnumOperation.DIVIDE, "/", "divide", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT,   
		new NativeOpInfo(Opcodes.IDIV, EnumInstructionOperand.BYTE),
		new NativeOpInfo(Opcodes.IDIV, EnumInstructionOperand.CHAR),
		new NativeOpInfo(Opcodes.IDIV, EnumInstructionOperand.SHORT),
		new NativeOpInfo(Opcodes.IDIV, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.LDIV, EnumInstructionOperand.LONG),
		new NativeOpInfo(Opcodes.IDIV, EnumInstructionOperand.FLOAT),
		new NativeOpInfo(Opcodes.DDIV, EnumInstructionOperand.DOUBLE)));
	
	addOperatorDef(new OperatorDefNative(EnumOperation.MOD, "%", "mod", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT,
		new NativeOpInfo(Opcodes.IREM, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IREM, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IREM, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IREM, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.LREM, EnumInstructionOperand.LONG, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.DREM, EnumInstructionOperand.DOUBLE, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.FREM, EnumInstructionOperand.FLOAT, EnumInstructionOperand.INT)));
	
	addOperatorDef(new OperatorDefNative(EnumOperation.NOT, "!", "not", EnumOperatorType.UNARY, 0, EnumOperatorAssociativity.NONE, new NativeOpInfo(-1, EnumInstructionOperand.BOOL)));
	addOperatorDef(new OperatorDefNative(EnumOperation.OR, "||", "or", EnumOperatorType.BINARY, 10, EnumOperatorAssociativity.LEFT, new NativeOpInfo(-1, EnumInstructionOperand.BOOL)));
	addOperatorDef(new OperatorDefNative(EnumOperation.AND, "&&", "and", EnumOperatorType.BINARY, 20, EnumOperatorAssociativity.LEFT, new NativeOpInfo(-1, EnumInstructionOperand.BOOL)));
	addOperatorDef(new OperatorDefNative(EnumOperation.BIT_OR, "|", "bit_or", EnumOperatorType.BINARY, 30, EnumOperatorAssociativity.LEFT,
		new NativeOpInfo(Opcodes.IOR, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IOR, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IOR, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IOR, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.LOR, EnumInstructionOperand.LONG, EnumInstructionOperand.LONG)));
		
	addOperatorDef(new OperatorDefNative(EnumOperation.XOR, "^", "bit_xor", EnumOperatorType.BINARY, 40, EnumOperatorAssociativity.LEFT,
		new NativeOpInfo(Opcodes.IXOR, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IXOR, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IXOR, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IXOR, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.LXOR, EnumInstructionOperand.LONG, EnumInstructionOperand.LONG)));
	
	addOperatorDef(new OperatorDefNative(EnumOperation.BIT_AND, "&", "bit_and", EnumOperatorType.BINARY, 50, EnumOperatorAssociativity.LEFT,
		new NativeOpInfo(Opcodes.IAND, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IAND, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IAND, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IAND, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.LAND, EnumInstructionOperand.LONG, EnumInstructionOperand.LONG)));
		
	addOperatorDef(new OperatorDefNative(EnumOperation.EQUAL, "==", "equal", EnumOperatorType.BINARY, 60, EnumOperatorAssociativity.NONE, 
		new NativeOpInfo(-1, EnumInstructionOperand.BYTE, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.CHAR, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.SHORT, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.INT, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.FLOAT, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.DOUBLE, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.LONG, EnumInstructionOperand.LONG),
		new NativeOpInfo(-1, EnumInstructionOperand.ARRAY, EnumInstructionOperand.BOOL),
		new NativeOpInfo(-1, EnumInstructionOperand.REFERENCE, EnumInstructionOperand.BOOL)));
		
	addOperatorDef(new OperatorDefNative(EnumOperation.NOT_EQUAL, "!=", "not_equal", EnumOperatorType.BINARY, 60, EnumOperatorAssociativity.LEFT, equalityOpInfo));
	addOperatorDef(new OperatorDefNative(EnumOperation.LESS, "<", "less", EnumOperatorType.BINARY, 70, EnumOperatorAssociativity.LEFT, equalityOpInfo));
	addOperatorDef(new OperatorDefNative(EnumOperation.GREATER, ">", "greater", EnumOperatorType.BINARY, 70, EnumOperatorAssociativity.LEFT, equalityOpInfo));
	addOperatorDef(new OperatorDefNative(EnumOperation.LESS_EQUAL, "<=", "less_equal", EnumOperatorType.BINARY, 70, EnumOperatorAssociativity.LEFT, equalityOpInfo));
	addOperatorDef(new OperatorDefNative(EnumOperation.GREATER_EQUAL, ">=", "greater_equal", EnumOperatorType.BINARY, 70, EnumOperatorAssociativity.LEFT, equalityOpInfo));
	
	addOperatorDef(new OperatorDefNative(EnumOperation.L_SHIFT, "<<", "left_shift", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT,
		new NativeOpInfo(Opcodes.ISHL, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.ISHL, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT, EnumInstructionOperand.BYTE),
		new NativeOpInfo(Opcodes.ISHL, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT, EnumInstructionOperand.CHAR),
		new NativeOpInfo(Opcodes.ISHL, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT, EnumInstructionOperand.SHORT),
		new NativeOpInfo(Opcodes.LSHL, EnumInstructionOperand.LONG, EnumInstructionOperand.INT, EnumInstructionOperand.LONG)	
		));
	
	addOperatorDef(new OperatorDefNative(EnumOperation.R_SHIFT, ">>", "right_shift", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT,
		new NativeOpInfo(Opcodes.ISHR, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.ISHR, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT, EnumInstructionOperand.BYTE),
		new NativeOpInfo(Opcodes.ISHR, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT, EnumInstructionOperand.CHAR),
		new NativeOpInfo(Opcodes.ISHR, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT, EnumInstructionOperand.SHORT),
		new NativeOpInfo(Opcodes.LSHR, EnumInstructionOperand.LONG, EnumInstructionOperand.INT, EnumInstructionOperand.LONG)	
		));
	
	addOperatorDef(new OperatorDefNative(EnumOperation.R_SHIFT_LOGICAL, ">>>", "logical_right_shift", EnumOperatorType.BINARY, 100, EnumOperatorAssociativity.LEFT,
		new NativeOpInfo(Opcodes.IUSHR, EnumInstructionOperand.INT),
		new NativeOpInfo(Opcodes.IUSHR, EnumInstructionOperand.BYTE, EnumInstructionOperand.INT, EnumInstructionOperand.BYTE),
		new NativeOpInfo(Opcodes.IUSHR, EnumInstructionOperand.CHAR, EnumInstructionOperand.INT, EnumInstructionOperand.CHAR),
		new NativeOpInfo(Opcodes.IUSHR, EnumInstructionOperand.SHORT, EnumInstructionOperand.INT, EnumInstructionOperand.SHORT),
		new NativeOpInfo(Opcodes.LUSHR, EnumInstructionOperand.LONG, EnumInstructionOperand.INT, EnumInstructionOperand.LONG)	
		));
    }
    
    public OperatorDef(String id, String name, EnumOperatorType type, int precedence, EnumOperatorAssociativity assoc) {
	this.id = id;
	this.name = name;
	this.type = type;
	this.precedence = precedence;
	this.assoc = assoc;
    }
    
    public OperatorDef(String id, String name, EnumOperatorType unary) {
	this(id, name, unary, 1000, EnumOperatorAssociativity.NONE);
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
    
    public static class OperatorDefNative extends OperatorDef {
	
	public static class NativeOpInfo {
	    public EnumInstructionOperand type1, type2, retType;
	    public int opcode; // -1 means that this is a special case and that opcode generation is handled by the code generator
	    public NativeOpInfo(int opcode, EnumInstructionOperand type1, EnumInstructionOperand type2, EnumInstructionOperand ret) {
		this.type1 = type1;
		this.type2 = type2;
		this.retType = ret;
		this.opcode = opcode;
	    }
	    public NativeOpInfo(int opcode, EnumInstructionOperand type, EnumInstructionOperand retType){
		this(opcode, type, type, retType);
	    }
	    public NativeOpInfo(int opcode, EnumInstructionOperand type){
		this(opcode, type, type, type);
	    }
	}
	
	public NativeOpInfo[] opInfo;
	public EnumOperation operation;

	public OperatorDefNative(EnumOperation operation, String id, String name, EnumOperatorType type, int precedence, EnumOperatorAssociativity assoc, NativeOpInfo...opInfo) {
	    super(id, name, type, precedence, assoc);
	    this.opInfo = opInfo;
	    this.operation = operation;
	}
	
    }

    public static boolean operatorNameExists(String name2) {
	for(OperatorDef def : operatorDefs.values()) if(def.name.equals(name2)) return true;
	return false;
    }

    public static String filterOperators(final String shortName) {
        if (!operatorDefExists(shortName)) return shortName;
        else return "$" + getOperatorDef(shortName).name;
    }

}
