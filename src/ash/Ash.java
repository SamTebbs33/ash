package ash;

import ash.grammar.AshLexer;
import ash.grammar.AshParser;
import ash.grammar.node.AshParserVisitor;
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
        AshParser.FileContext tree = parser.file(); // parse a start rule
        AshParserVisitor visitor = new AshParserVisitor();
        NodeFile file = visitor.visitFile(tree);
    }

}
