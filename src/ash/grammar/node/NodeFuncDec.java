package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeFuncDec extends Node<AshParser.FuncDecContext> {
    public NodeFuncDec(AshParser.FuncDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
    }
}
