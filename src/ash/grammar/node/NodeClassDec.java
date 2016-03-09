package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeClassDec extends Node<AshParser.ClassDecContext> {
    public NodeClassDec(AshParser.ClassDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
    }
}
