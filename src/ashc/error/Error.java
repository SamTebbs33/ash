package ashc.error;

import ashc.grammar.*;
import ashc.main.*;

/**
 * Ash
 * @author samtebbs, 15:44:44 - 23 May 2015
 */
public class Error {

    public static enum EnumError {
	TYPE_ALREADY_EXISTS("Type already exists (%s)"),
	TYPE_ALREADY_IMPORTED("Type has already been imported (%s)"),
	TYPE_DOES_NOT_EXIST("Type doesn't exist (%s)"),
	TYPE_DOES_NOT_EXTEND("Type (%s) does not extend %s"),

	FUNC_ALREADY_EXISTS("Function already exists (%s)"),
	FUNC_DOES_NOT_EXIST("Function %s doesn't exist in type %s"),
	RETURN_EXPR_IN_VOID_FUNC("Cannot return an expression in a void function"),

	FIELD_ALREADY_EXISTS("Field already exists (%s)"),

	VAR_ALREADY_EXISTS("Variable already exists (%s)"),
	VAR_DOES_NOT_EXIST("Variable does not exist (%s)"),
	CONST_VAR_IS_PROPERTY("Cannot declare a const property (%s)"),
	PROPERTY_IN_FUNC("Cannot declare a property in a function (%s)"),

	MISSING_ASSIGNMENT("Assignment expected"),
	TOO_MANY_ARRAY_ACCESSES("Attempted to access %s array dimension(s) on a variable with %s dimension(s) (%s)"),
	ARRAY_INDEX_NOT_NUMERIC("Array index must be numeric (%s)"),
	CANNOT_USE_SELF("Cannot use the self keyword in the current context"),

	CANNOT_EXTEND_FINAL_TYPE("Cannot extend a final type (%s)"),
	CANNOT_EXTEND_TYPE("%s %s cannot extend %s %s (%s)"),
	CANNOT_EXTEND_OPTIONAL_TYPE("Cannot extend an optional type (%s)"),
	CANNOT_EXTEND_MULTIPLE_CLASSES("Cannot extend multiple classes (%s)"),

	DUPLICATE_MODIFIERS("Duplicate modifiers (%s)"),
	DUPLICATE_ARGUMENTS("Duplicate arguments (%s)"),
	DUPLICATE_TYPES("Duplicate types (%s)"),

	PRIMTIVE_CANNOT_BE_OPTIONAL("A primitive type cannot be optional (%s)"),
	CANNOT_ASSIGN("Cannot assign %s to %s");

	public String format;

	private EnumError(final String format) {
	    this.format = format;
	}
    }

    public static void semanticError(final int line, final int column, final EnumError error, final Object... args) {
	System.err.printf("Error[%s:%d:%d] ", AshCompiler.get().relFilePath, line, column);
	System.err.printf(error.format + "%n", args);
    }

    public static void semanticError(final Node node, final int line, final int column, final EnumError error, final Object... args) {
	node.errored = true;
	semanticError(line, column, error, args);
    }

}
