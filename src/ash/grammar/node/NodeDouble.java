package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.grammar.semantics.TypeInstance;

/**
 * Created by samtebbs on 16/03/2016.
 */
public class NodeDouble extends Node<AshParser.ExprContext> implements Expression {

    private final double val;

    public NodeDouble(AshParser.ExprContext ctx, double v, AshParserVisitor ashParserVisitor) {
        super(ctx, ashParserVisitor);
        this.val = v;
    }

    @Override
    public String toString() {
        return "NodeDouble{" +
                "val=" + val +
                "} " + super.toString();
    }

    @Override
    public TypeInstance getType() {
        return TypeInstance.DOUBLE;
    }

    @Override
    public void analyse() {

    }
}
