package ashc.main;

import java.io.*;
import java.util.*;

import ashc.grammar.*;
import ashc.grammar.Node.NodeFile;
import ashc.semantics.*;

/**
 * Ash
 * @author samtebbs, 15:14:41 - 23 May 2015
 */
public class AshCompiler {

    public Stack<Scope> scopeStack = new Stack<Scope>();
    private final Parser parser;
    private final Lexer lexer;
    private NodeFile fileNode;
    public String relFilePath;

    public static Stack<AshCompiler> compilers = new Stack<AshCompiler>();

    public AshCompiler(final String relFilePath) throws FileNotFoundException, IOException {
	this.relFilePath = relFilePath;
	lexer = new Lexer(new BufferedReader(new FileReader(new File(relFilePath))));
	parser = new Parser(lexer);
	compilers.push(this);
    }

    public void parse() {
	fileNode = parser.start();
	System.out.println(fileNode.toString());
    }

    public void preAnalyse() {
	if (fileNode != null) fileNode.preAnalyse();
    }

    public void analyse() {
	if (fileNode != null) fileNode.analyse();
    }

    public static AshCompiler get() {
	return compilers.peek();
    }

}
