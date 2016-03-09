package ash.grammar.node;

import ash.grammar.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeTypeDec extends Node<AshParser.TypeDecContext> {
    public NodeTypeDec(AshParser.TypeDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
    }
}
