package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeVarDec extends Node<AshParser.VarDecContext> {
    public NodeVarDec(AshParser.VarDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
    }
}
