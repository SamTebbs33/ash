package ashc.grammar;

import ashc.semantics.*;

/**
 * Ash
 * @author samtebbs, 10:49:05 - 27 May 2015
 */
public class Operator {

    public static enum EnumOperatorType {
	ARITHMETIC,
	ASSIGNMENT
    }

    public static enum EnumOperation {
	INCREMENT,
	DECREMENT,
	ADD,
	SUBTRACT,
	MULTIPLY,
	DIVIDE(EnumPrimitive.DOUBLE),
	MOD,
	POW(EnumPrimitive.DOUBLE),
	AND(EnumPrimitive.BOOL),
	OR(EnumPrimitive.BOOL),
	XOR(EnumPrimitive.BOOL),
	NOT(EnumPrimitive.BOOL),
	BIT_AND,
	BIT_OR,
	BIT_XOR,
	BIT_NOT,
	LESS(EnumPrimitive.BOOL),
	GREATER(EnumPrimitive.BOOL),
	EQUAL(EnumPrimitive.BOOL),
	LESS_EQUAL(EnumPrimitive.BOOL),
	GREATER_EQUAL(EnumPrimitive.BOOL),
	L_SHIFT,
	R_SHIFT,
	R_SHIFT_LOGICAL;

	public EnumPrimitive primitive;

	private EnumOperation() {
	    this(null);
	}

	private EnumOperation(final EnumPrimitive p) {
	    primitive = p;
	}

    }

    public EnumOperation operation;
    public EnumOperatorType type;

    public Operator(String opStr) {
	if (opStr.charAt(opStr.length() - 1) == '=') {
	    type = EnumOperatorType.ASSIGNMENT;
	    opStr = opStr.substring(0, opStr.length() - 1);
	} else type = EnumOperatorType.ARITHMETIC;
	operation = getOperation(opStr);
    }

    private EnumOperation getOperation(final String opStr) {
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

	    default:
		return null;
	}
    }

}
