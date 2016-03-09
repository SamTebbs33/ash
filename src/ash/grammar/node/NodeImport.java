package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeImport extends Node<AshParser.ImportDecContext> {
    public NodeImport(AshParser.ImportDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
    }
}
