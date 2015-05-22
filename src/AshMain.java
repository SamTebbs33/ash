import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ashc.grammar.Lexer;
import ashc.grammar.Parser;

/**
 * Grammar
 *
 * @author samtebbs, 19:52:26 - 20 May 2015
 */
public class AshMain {

    public static void main(final String[] args) throws FileNotFoundException, IOException {
	long time = System.currentTimeMillis();
	final Lexer lexer = new Lexer(new BufferedReader(new FileReader(new File("lex.txt"))));
	final Parser parser = new Parser(lexer);
	parser.start();
	time = System.currentTimeMillis() - time;
	System.out.printf("Time: %d ms\n", time);
    }

}
