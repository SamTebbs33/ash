package ash.grammar.ash.grammar.node;

import ash.grammar.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeFuncParam extends Node<AshParser.FuncParamContext> {
    public NodeFuncParam(AshParser.FuncParamContext context, AshParserVisitor visitor) {
        super(context, visitor);
    }
}
