package ashc.grammar;

import ashc.semantics.*;

/**
 * Ash
 *
 * @author samtebbs, 10:49:05 - 27 May 2015
 */
public class Operator {

    public static enum EnumOperatorType {
	ARITHMETIC, ASSIGNMENT
    }

    public static enum EnumOperation {
	INCREMENT("increment"),
	DECREMENT("decrement"),
	ADD("add"),
	SUBTRACT("subtract"),
	MULTIPLY("multiply"),
	DIVIDE(EnumPrimitive.DOUBLE, "divide"),
	MOD("mod"),
	POW(EnumPrimitive.DOUBLE, "pow"),
	AND(EnumPrimitive.BOOL, "and"),
	OR(EnumPrimitive.BOOL, "or"),
	XOR(EnumPrimitive.BOOL, "xor"),
	NOT(EnumPrimitive.BOOL, "not"),
	BIT_AND("bit_and"),
	BIT_OR("bit_or"),
	BIT_XOR("bit_xor"),
	BIT_NOT("bit_not"),
	LESS(EnumPrimitive.BOOL, "less"),
	GREATER(EnumPrimitive.BOOL, "greater"),
	EQUAL(EnumPrimitive.BOOL, "equal"),
	NOT_EQUAL(EnumPrimitive.BOOL, "not_equal"),
	LESS_EQUAL(EnumPrimitive.BOOL, "less_equal"),
	GREATER_EQUAL(EnumPrimitive.BOOL, "greater_equal"),
	L_SHIFT("l_shift"),
	R_SHIFT("r_shift"),
	R_SHIFT_LOGICAL("r_shift_logical");

	public EnumPrimitive primitive;
	public String bytecodeName;

	private EnumOperation(String bytecodeName) {
	    this(null, bytecodeName);
	}

	private EnumOperation(final EnumPrimitive p, String bytecodeName) {
	    primitive = p;
	    this.bytecodeName = bytecodeName;
	}

    }

    public EnumOperation operation;
    public EnumOperatorType type;
    public String opStr;

    public Operator(String opStr) {
	this.opStr = opStr;
	if (opStr.charAt(opStr.length() - 1) == '=' && !opStr.equals("==")) {
	    type = EnumOperatorType.ASSIGNMENT;
	    opStr = opStr.substring(0, opStr.length() - 1);
	} else type = EnumOperatorType.ARITHMETIC;
	operation = getOperation(opStr);
    }

    public static EnumOperation getOperation(final String opStr) {
	switch (opStr) {
	    case "-":
		return EnumOperation.SUBTRACT;

	    case "+":
		return EnumOperation.ADD;

	    case "/":
		return EnumOperation.DIVIDE;

	    case "*":
		return EnumOperation.MULTIPLY;

	    case "%":
		return EnumOperation.MOD;

	    case "**":
		return EnumOperation.POW;

	    case "^":
		return EnumOperation.BIT_XOR;

	    case "&":
		return EnumOperation.BIT_AND;

	    case "|":
		return EnumOperation.BIT_OR;

	    case "~":
		return EnumOperation.BIT_NOT;

	    case "<<":
		return EnumOperation.L_SHIFT;

	    case ">>":
		return EnumOperation.R_SHIFT;

	    case ">>>":
		return EnumOperation.R_SHIFT_LOGICAL;

	    case "<":
		return EnumOperation.LESS;

	    case ">":
		return EnumOperation.GREATER;

	    case "<=":
		return EnumOperation.LESS_EQUAL;

	    case ">=":
		return EnumOperation.GREATER_EQUAL;

	    case "&&":
		return EnumOperation.AND;

	    case "||":
		return EnumOperation.OR;

	    case "^^":
		return EnumOperation.XOR;

	    case "!":
		return EnumOperation.NOT;
		
	    case "==":
		return EnumOperation.EQUAL;
		
	    case "!=":
		return EnumOperation.NOT_EQUAL;

	    default:
		return null;
	}
    }

    public static boolean isOperator(String name) {
	return getOperation(name) != null;
    }

    public static String filterOperators(String shortName) {
	EnumOperation op = getOperation(shortName);
	if(op == null) return shortName;
	else return "$" + op.bytecodeName;
    }

}
