package ashc.error;

import ashc.grammar.*;
import ashc.main.*;

/**
 * Ash
 *
 * @author samtebbs, 15:44:44 - 23 May 2015
 */
public class AshError {

    public static int numErrors = 0;

    public static enum EnumError {
	TYPE_ALREADY_EXISTS("Type already exists (%s)"),
	ALIAS_ALREADY_EXISTS("Alias already exists (%s)"),
	TYPE_ALREADY_IMPORTED("Type has already been imported (%s)"),
	TYPE_DOES_NOT_EXIST("Type doesn't exist (%s)"),
	TYPE_DOES_NOT_EXTEND("Type (%s) does not extend %s"),

	EXPECTED_STRING_INTERP_TERMINATOR("Expected terminating } for string interpolated expression"),

	FUNC_ALREADY_EXISTS("The function \'%s\' already exists"),
	FUNC_DOES_NOT_EXIST("The function \'%s\' with the given arguments doesn't exist in type %s"),
	FUNC_IS_NOT_VISIBLE("The function \'%s\' is not visible from the current context"),
	CONSTRUCTOR_DOES_NOT_EXIST("The constructor with given arguments does not exist in type %s"),
	RETURN_EXPR_IN_VOID_FUNC("Cannot return an expression in a void function"),
	RETURN_IN_MUT_FUNC("Cannot return in a mutator function"),
	NOT_ALL_PATHS_HAVE_RETURN("Not all code paths have a return statement"),
	RETURN_VOID_IN_NONVOID_FUNC("Cannot return void in a non-void function"),
	NON_STATIC_FUNC_USED_IN_STATIC_CONTEXT("A non-static function cannot be used from a static context (%s)"),
	NON_STATIC_VAR_USED_IN_STATIC_CONTEXT("A non-static variable/field cannot be used from a static context (%s)"),
	MUT_FUNC_IS_STATIC("Mutator functions cannot be static (%s)"),

	FIELD_ALREADY_EXISTS("\'%s\' already exists"),

	VAR_ALREADY_EXISTS("\'%s\' already exists"),
	VAR_DOES_NOT_EXIST("\'%s\' does not exist"),
	VAR_IS_NOT_VISIBLE("\'%s\' is not visible from the current context"),
	CONST_VAR_IS_PROPERTY("Cannot declare a const property (%s)"),
	PROPERTY_IN_FUNC("Cannot declare a property in a function (%s)"),

	MISSING_ASSIGNMENT("Assignment expected"),
	TOO_MANY_ARRAY_ACCESSES("Attempted to access %s array dimension(s) on a variable with %s dimension(s) (%s)"),
	TOO_MANY_GENERICS("Too many generics declared"),
	ARRAY_INDEX_NOT_NUMERIC("Array index must be numeric (%s)"),
	CANNOT_USE_SELF("Cannot use the self keyword in the current context"),
	EXPECTED_BOOL_EXPR("Expected a boolean expression, found %s"),
	EXPECTED_PRIMITIVE_TYPE("Expected a primitive type, but found %s"),
	EXPECTED_NUMERIC_TYPE("Expected a numeric type, but found %s"),
	CANNOT_ITERATE_TYPE("Cannot iterate over type %s"),
	INCOMPATIBLE_TYPES("The types %s and %s are incompatible"),

	UNCHECKED_CAST("The casted variable has not been checked to make sure it can be casted (%s as %s)"),
	UNWRAPPED_VALUE_NOT_OPTIONAL("An unwrapped value must be optional (%s)"),
	UNCHECKED_UNWRAP("The unwrapped variable \'%s\' has not been checked to make sure it is not null"),

	CANNOT_EXTEND_FINAL_TYPE("Cannot extend a final type (%s)"),
	CANNOT_EXTEND_TYPE("%s %s cannot extend %s %s (%s)"),
	CANNOT_EXTEND_OPTIONAL_TYPE("Cannot extend an optional type (%s)"),
	CANNOT_EXTEND_MULTIPLE_CLASSES("Cannot extend multiple classes (%s)"),

	DUPLICATE_MODIFIERS("Duplicate modifiers (%s)"),
	DUPLICATE_ARGUMENTS("Duplicate arguments (%s)"),
	DUPLICATE_TYPES("Duplicate types (%s)"),
	PARAM_DEF_EXPR_NOT_LAST("Only the last parameter can have a default value"),

	ELVIS_EXPR_NOT_OPTIONAL("The first expression in an elvis expression must be optional (%s)"),
	ELVIS_EXPR_IS_OPTIONAL("The second expression in an elvis expression cannot be optional (%s)"),
	ELVIS_EXPR_IS_PRIMITIVE("The first expression in an elvis expression cannot be a primitive (%s)"),
	OPERATOR_CANNOT_BE_APPLIED_TO_TYPES("Operator %s cannot be applied to types %s and %s"),
	OPERATOR_CANNOT_BE_APPLIED_TO_TYPE("Operator %s cannot be applied to type %s"),

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
	numErrors++;
    }

    public static void semanticError(final Node node, final int line, final int column, final EnumError error, final Object... args) {
	node.errored = true;
	semanticError(line, column, error, args);
    }

    public static void warning(final int line, final int column, final EnumError error, final Object... args) {
	System.err.printf("Warning[%s:%d:%d] ", AshCompiler.get().relFilePath, line, column);
	System.err.printf(error.format + "%n", args);
    }

}
