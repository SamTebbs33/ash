package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeClassBlock extends Node<AshParser.ClassBlockContext> {
    public NodeClassBlock(AshParser.ClassBlockContext context, AshParserVisitor visitor) {
        super(context, visitor);
    }
}
