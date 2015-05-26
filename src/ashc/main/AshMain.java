package ashc.main;

import java.io.*;

/**
 * Grammar
 *
 * @author samtebbs, 19:52:26 - 20 May 2015
 */
public class AshMain {

    public static void main(final String[] args) throws FileNotFoundException, IOException {
	final AshCompiler compiler = new AshCompiler(args[0]);
	compiler.parse();
	compiler.preAnalyse();
	compiler.analyse();
    }

}
