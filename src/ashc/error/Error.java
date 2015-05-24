package ashc.error;

import ashc.main.AshCompiler;

/**
 * Ash
 * @author samtebbs, 15:44:44 - 23 May 2015
 */
public class Error {

    public static enum EnumError {
	TYPE_ALREADY_EXISTS("Type already exists (%s)"),
	TYPE_ALREADY_IMPORTED("Type has already been imported (%s)");
	
	public String format;
	private EnumError(String format){
	    this.format = format;
	}
    }

    public static void semanticError(final int line, final int column, EnumError error, final String... args) {
	System.err.printf("Error [%s:%d:%d] ", AshCompiler.get().relFilePath, line, column);
	System.err.printf(error.format + "%n", (Object[])args);
    }

}
