package ashc.main;

import java.io.*;
import java.util.*;

import ashc.codegen.*;
import ashc.grammar.*;
import ashc.grammar.Lexer.UnexpectedTokenException;
import ashc.grammar.Node.NodeDefFile;
import ashc.grammar.Node.NodeFile;
import ashc.grammar.Parser.GrammarException;

/**
 * Ash
 *
 * @author samtebbs, 15:14:41 - 23 May 2015
 */
public class AshCompiler {

    private final Parser parser;
    private final Lexer lexer;
    private NodeFile fileNode;
    private NodeDefFile defFileNode;
    public String relFilePath, parentPath;
    public int errors;
    public String fileName;

    public static Stack<AshCompiler> compilers = new Stack<AshCompiler>();

    public AshCompiler(final String relFilePath) throws FileNotFoundException, IOException {
	final File file = new File(relFilePath);
	fileName = file.getName();
	if(fileName.indexOf('.') > 0) fileName = fileName.substring(0, fileName.indexOf('.'));
	parentPath = file.getParentFile() != null ? file.getParentFile().getPath() : "";
	this.relFilePath = relFilePath;
	lexer = new Lexer(new BufferedReader(new FileReader(file)));
	parser = new Parser(lexer);
	compilers.push(this);
    }

    public void parseSourceFile() {
	try {
	    fileNode = parser.parseFile();
	} catch (final GrammarException e) {
	    parser.handleException(e);
	}
    }

    public void preAnalyse() {
	if (fileNode != null) fileNode.preAnalyse();
	else if(defFileNode != null) defFileNode.preAnalyse();
    }

    public void analyse() {
	if (fileNode != null) fileNode.analyse();
	else if(defFileNode != null) defFileNode.analyse();
    }

    public void generate() {
	if (fileNode != null) {
	    fileNode.generate();
	    GenNode.generate();
	}else if(defFileNode != null){
	    defFileNode.generate();
	    GenNode.generate();
	}
    }

    public static AshCompiler get() {
	return compilers.peek();
    }

    public void parseDefFile() {
	try {
	    defFileNode = parser.parseDefFile();
	} catch (GrammarException e) {
	    parser.handleException(e);
	}
	parser.clear();
    }

}
