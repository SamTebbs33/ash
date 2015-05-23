package ashc.main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import ashc.grammar.Lexer;
import ashc.grammar.Node.NodeFile;
import ashc.grammar.Parser;
import ashc.semantics.Scope;

/**
 * Ash
 * @author samtebbs, 15:14:41 - 23 May 2015
 */
public class AshCompiler {

    public Stack<Scope> scopeStack = new Stack<Scope>();
    private final Parser parser;
    private final Lexer lexer;
    private NodeFile fileNode;
    
    public static Stack<AshCompiler> compilers = new Stack<AshCompiler>();

    public AshCompiler(final File file) throws FileNotFoundException, IOException {
	lexer = new Lexer(new BufferedReader(new FileReader(file)));
	parser = new Parser(lexer);
	compilers.push(this);
    }

    public void parse() {
	fileNode = parser.start();
    }

    public void preAnalyse() {
	if (fileNode != null) fileNode.preAnalyse();
    }

    public void analyse() {
	if (fileNode != null) fileNode.analyse();
    }
    
    public static AshCompiler get(){
	return compilers.peek();
    }

}
