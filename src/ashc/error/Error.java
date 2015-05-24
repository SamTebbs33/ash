package ashc.error;

import ashc.grammar.Node;
import ashc.main.AshCompiler;

/**
 * Ash
 * @author samtebbs, 15:44:44 - 23 May 2015
 */
public class Error {

    public static enum EnumError {
	TYPE_ALREADY_EXISTS("Type already exists (%s)"),
	TYPE_ALREADY_IMPORTED("Type has already been imported (%s)"),
	TYPE_DOES_NOT_EXIST("Type doesn't exist (%s)"),

	CANNOT_EXTEND_FINAL_TYPE("Cannot extend a final type (%s)"),
	CANNOT_EXTEND_TYPE("%s %s cannot extend %s %s (%s)"),
	
	DUPLICATE_MODIFIERS("Duplicate modifiers (%s)");

	public String format;

	private EnumError(final String format) {
	    this.format = format;
	}
    }

    public static void semanticError(final int line, final int column, final EnumError error, final String... args) {
	System.err.printf("Error [%s:%d:%d] ", AshCompiler.get().relFilePath, line, column);
	System.err.printf(error.format + "%n", (Object[]) args);
    }
    
    public static void semanticError(Node node, final int line, final int column, final EnumError error, final String... args) {
	node.errored = true;
	semanticError(line, column, error, args);
    }

}
