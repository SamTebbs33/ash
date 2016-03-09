package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeFuncParam extends Node<AshParser.FuncParamContext> {
    public NodeFuncParam(AshParser.FuncParamContext context, AshParserVisitor visitor) {
        super(context, visitor);
    }
}
