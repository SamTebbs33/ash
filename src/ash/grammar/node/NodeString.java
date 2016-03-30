package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.grammar.semantics.TypeInstance;

/**
 * Created by samtebbs on 16/03/2016.
 */
public class NodeString extends Node<AshParser.ExprContext> implements Expression {

    private final String str;

    public NodeString(AshParser.ExprContext ctx, String substring, AshParserVisitor ashParserVisitor) {
        super(ctx, ashParserVisitor);
        this.str = substring;
    }

    @Override
    public String toString() {
        return "NodeString{" +
                "str='" + str + '\'' +
                "} " + super.toString();
    }

    @Override
    public TypeInstance getType() {
        return TypeInstance.STRING;
    }

    @Override
    public void analyse() {

    }
}
