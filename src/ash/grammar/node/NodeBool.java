package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.grammar.semantics.TypeInstance;

/**
 * Created by samtebbs on 16/03/2016.
 */
public class NodeBool extends Node<AshParser.ExprContext> implements Expression {

    boolean val;

    public NodeBool(AshParser.ExprContext context, boolean val, AshParserVisitor visitor) {
        super(context, visitor);
        this.val = val;
    }

    @Override
    public void analyse() {

    }

    @Override
    public String toString() {
        return "NodeBool{" +
                "val=" + val +
                "} " + super.toString();
    }

    @Override
    public TypeInstance getType() {
        return TypeInstance.BOOL;
    }
}
