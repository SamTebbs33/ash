package ash;

import ash.error.AshParserErrorListener;
import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshLexer;
import ash.grammar.antlr.AshParser;
import ash.grammar.node.NodeFile;
import ash.library.Library;
import ash.semantics.member.Type;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class Ash {

    public static CommonTokenStream tokenStream;
    public static final String[] classPath = {"."};
    public static final Stack<AshParserErrorListener> errorListeners = new Stack<>();

    public static void main(String[] args) throws IOException {
        Library.loadLibs();
        Type.loadJavaLang();
        compile(new File("Test.ash"));
    }

    public static boolean compile(File file) throws IOException {
        System.out.println("compile: " + file);
        AshLexer lexer = new AshLexer(new ANTLRFileStream(file.getName()));
        tokenStream = new CommonTokenStream(lexer);

        // Prepare the parser
        AshParser parser = new AshParser(tokenStream);
        parser.removeErrorListeners();
        AshParserErrorListener errorListener = new AshParserErrorListener();
        errorListeners.push(errorListener);
        parser.addErrorListener(errorListener);
        //parser.setTrace(true);

        // Start parsing and analysing
        AshParser.FileContext tree = parser.file(); // parse a start rule
        AshParserVisitor visitor = new AshParserVisitor();
        NodeFile fileNode = visitor.visitFile(tree);
        fileNode.preAnalyse();
        fileNode.analyse();
        errorListeners.pop();
        return errorListener.numErrors == 0;
    }

}
