import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Grammar
 *
 * @author samtebbs, 19:52:26 - 20 May 2015
 */
public class AshMain {

    public static void main(final String[] args) throws FileNotFoundException, IOException {
	final AshCompiler compiler = new AshCompiler(new File(args[0]));
	compiler.parse();
	compiler.preAnalyse();
	compiler.analyse();
    }

}
