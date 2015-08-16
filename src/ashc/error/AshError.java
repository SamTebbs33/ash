package ashc.error;

import ashc.grammar.*;
import ashc.main.*;

/**
 * Ash
 *
 * @author samtebbs, 15:44:44 - 23 May 2015
 */
public class AshError extends Exception {

    public static int numErrors = 0;

    public AshError(final String msg) {
	super(msg);
    }

    public static enum EnumError {
	TYPE_ALREADY_EXISTS("Type already exists (%s)"),
	ALIAS_ALREADY_EXISTS("Alias already exists (%s)"),
	TYPE_ALREADY_IMPORTED("Type has already been imported (%s)"),
	TYPE_DOES_NOT_EXIST("Type doesn't exist (%s)"),
	TYPE_DOES_NOT_EXTEND("Type (%s) does not extend %s"),

	EXPECTED_STRING_INTERP_TERMINATOR("Expected terminating } for string interpolated expression"),
	PATH_DOES_NOT_MATCH_PACKAGE("The relative path to this file does not match the package structure (%s)"),
	CANNOT_FIND_CLASS("Cannot find class file or source file \"%s\""),

	FUNC_ALREADY_EXISTS("The function \'%s\' already exists"),
	FUNC_ALREADY_EXISTS_IN_TYPE("The function \'%s\' already exists in type %s"),
	FUNC_DOES_NOT_EXIST_IN_TYPE("The function \'%s\' with the given arguments doesn't exist in type %s"),
	FUNC_DOES_NOT_EXIST("The function \'%s\' with the given arguments doesn't exist"),
	FUNC_IS_NOT_VISIBLE("The function \'%s\' is not visible from the current context"),
	CONSTRUCTOR_DOES_NOT_EXIST("The constructor with given arguments does not exist in type %s"),
	RETURN_EXPR_IN_VOID_FUNC("Cannot return an expression in a void function"),
	RETURN_IN_MUT_FUNC("Cannot return in a mutator function"),
	NOT_ALL_PATHS_HAVE_RETURN("Not all code paths have a return statement"),
	RETURN_VOID_IN_NONVOID_FUNC("Cannot return void in a non-void function"),
	NON_STATIC_FUNC_USED_IN_STATIC_CONTEXT("A non-static function cannot be used from a static context (%s)"),
	NON_STATIC_VAR_USED_IN_STATIC_CONTEXT("A non-static variable/field cannot be used from a static context (%s)"),
	THIS_USED_IN_GLOBAL_FUNC("The \'this\' keyword cannot be used in a non-extension global function"),
	MUT_FUNC_IS_STATIC("Mutator functions cannot be static (%s)"),
	MUST_CALL_SUPER_CONSTRUCTOR("A sub-class must call one of the super-class' (%s) non-empty constructors"),
	OVERRIDE_KEYWORD_REQUIRED("The \'override\' modifier is required when overriding a super-type's function"),
	CANNOT_OVERRIDE_PRIVATE_FUNC("A private function cannot be overriden"),
	CANNOT_OVERRIDE_FINAL_FUNC("A final function cannot be overriden"),
	FUNC_SIGNATURES_DO_NOT_MATCH("The overridden and overriding functions' signatures do not match"),
	OVERRIDEN_FUNC_DOES_NOT_EXIST("The overriden function does not exist in a super class"),
	MISSING_FUNC_IMPLEMENTATION("An implementation for the \'%s\' function from %s is missing"),

	FIELD_ALREADY_EXISTS("\'%s\' already exists"),

	OPERATOR_ALREADY_EXISTS("Operator \"%s\" already exists"),
	WRONG_NUMBER_OF_PARAMS_FOR_OP("An overload for a %s operator must have %d parameters"),
	OP_OVERLOADS_CANNOT_HAVE_DEFEXPR("Operator overloads cannot use a default parameter value"),
	UNDEFINED_OPERATOR("Undefined %s operator \'%s\'"),

	VAR_ALREADY_EXISTS("\'%s\' already exists"),
	VAR_DOES_NOT_EXIST("\'%s\' does not exist"),
	VAR_IS_NOT_VISIBLE("\'%s\' is not visible from the current context"),
	CONST_VAR_IS_PROPERTY("Cannot declare a const property (%s)"),
	PROPERTY_IN_FUNC("Cannot declare a property in a function (%s)"),

	MISSING_ASSIGNMENT("Assignment expected"),
	TOO_MANY_ARRAY_ACCESSES("Attempted to access %s array dimension(s) on a variable with %s dimension(s) (%s)"),
	TOO_MANY_GENERICS("Too many generics declared"),
	ARRAY_INDEX_NOT_NUMERIC("Array index must be numeric and smaller than 64-bits (%s)"),
	ARRAY_INIT_TYPE_NOT_OPTIONAL("An array size initialiser's type must be optional or primitive (%s)"),
	ARRAY_INIT_SIZE_NOT_NUMERIC("An array size initialiser's length must be numeric (%s)"),
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
	CANNOT_EXTEND_ENUM("A type cannot extend an enum (%s)"),
	CANNOT_EXTEND_OPTIONAL_TYPE("Cannot extend an optional type (%s)"),
	CANNOT_EXTEND_MULTIPLE_CLASSES("Cannot extend multiple classes (%s)"),
	CONSTRUCT_BLOCK_NOT_ALLOWED("A construct block is not allowed in a type with no default constructor"),

	DUPLICATE_MODIFIERS("Duplicate modifiers (%s)"),
	DUPLICATE_ARGUMENTS("Duplicate arguments (%s)"),
	DUPLICATE_TYPES("Duplicate types (%s)"),
	PARAM_DEF_EXPR_NOT_LAST("Only the last parameter can have a default value"),

	ELVIS_EXPR_NOT_OPTIONAL("The first expression in an elvis expression must be optional (%s)"),
	ELVIS_EXPR_IS_OPTIONAL("The second expression in an elvis expression cannot be optional (%s)"),
	ELVIS_EXPR_IS_PRIMITIVE("The first expression in an elvis expression cannot be a primitive (%s)"),
	OPERATOR_CANNOT_BE_APPLIED_TO_TYPES("Operator %s cannot be applied to types %s and %s"),
	OPERATOR_CANNOT_BE_APPLIED_TO_TYPE("Operator %s cannot be applied to type %s"),

	MATCH_DOES_NOT_HAVE_DEFAULT("A match statement must have a default case"),
	BREAK_USED_OUTSIDE_LOOP("A break statement cannot be used outisde of a loop"),

	PRIMTIVE_CANNOT_BE_OPTIONAL("A primitive type cannot be optional (%s)"),
	CANNOT_ASSIGN("Cannot assign %s to %s");

	public String format;

	private EnumError(final String format) {
	    this.format = format;
	}
    }

    public static void semanticError(final int line, final int column, final EnumError error, final Object... args) {
	if ((line >= 0) && (column >= 0)) System.err.printf("Error[%s:%d:%d] ", AshCompiler.get().relFilePath, line, column);
	else System.err.printf("Error[%s] ", AshCompiler.get().relFilePath, line, column);
	System.err.printf(error.format + "%n", args);
	AshCompiler.get().errors++;
	numErrors++;
    }

    public static void semanticError(final Node node, final int line, final int column, final EnumError error, final Object... args) {
	node.errored = true;
	semanticError(line, column, error, args);
    }

    public static void semanticWarning(final int line, final int column, final EnumError error, final Object... args) {
	if (AshMain.warningsEnabled) {
	    System.err.printf("Warning[%s:%d:%d] ", AshCompiler.get().relFilePath, line, column);
	    System.err.printf(error.format + "%n", args);
	}
    }

    public static void compilerError(final String string) {
	System.err.printf("Error: %s%n", string);
	AshCompiler.get().errors++;
	numErrors++;
    }

    public static void compilerWarning(final String string) {
	System.err.printf("Warning: %s%n", string);
    }

    public static void verboseMsg(final String msg) {
	if (AshMain.verboseMsgEnabled) System.out.println(msg);
    }

}
