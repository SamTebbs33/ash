package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.grammar.semantics.TypeInstance;

/**
 * Created by samtebbs on 16/03/2016.
 */
public class NodeInt extends Node<AshParser.ExprContext> implements Expression {

    int val;

    public NodeInt(AshParser.ExprContext context, String val, int radix, AshParserVisitor visitor) {
        super(context, visitor);
        this.val = Integer.parseInt(val, radix);
    }

    @Override
    public String toString() {
        return "NodeInt{" +
                "val=" + val +
                "} " + super.toString();
    }

    @Override
    public TypeInstance getType() {
        return TypeInstance.INT;
    }

    @Override
    public void analyse() {

    }
}
