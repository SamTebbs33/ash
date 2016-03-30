package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.grammar.semantics.TypeInstance;

/**
 * Created by samtebbs on 16/03/2016.
 */
public class NodeFloat extends Node<AshParser.ExprContext> implements Expression {

    private final float val;

    public NodeFloat(AshParser.ExprContext context, float val, AshParserVisitor visitor) {
        super(context, visitor);
        this.val = val;
    }

    @Override
    public String toString() {
        return "NodeFloat{" +
                "val=" + val +
                "} " + super.toString();
    }

    @Override
    public TypeInstance getType() {
        return TypeInstance.FLOAT;
    }

    @Override
    public void analyse() {

    }
}
