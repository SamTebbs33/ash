package ash;

import ash.error.AshParserErrorListener;
import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshLexer;
import ash.grammar.antlr.AshParser;
import ash.grammar.node.NodeFile;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class Ash {

    public static CommonTokenStream tokenStream;

    public static void main(String[] args) throws IOException {
        AshLexer lexer = new AshLexer(new ANTLRFileStream("Test.ash"));
        tokenStream = new CommonTokenStream(lexer);
        AshParser parser = new AshParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new AshParserErrorListener());
        AshParser.FileContext tree = parser.file(); // parse a start rule
        AshParserVisitor visitor = new AshParserVisitor();
        NodeFile file = visitor.visitFile(tree);
        file.preAnalyse();
        file.analyse();
    }

}
