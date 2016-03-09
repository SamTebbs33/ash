package ash.grammar.ash.grammar.node;

import ash.grammar.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeImport extends Node<AshParser.ImportDecContext> {
    public NodeImport(AshParser.ImportDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
    }
}
