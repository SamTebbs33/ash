package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;

/**
 * Created by samtebbs on 15/03/2016.
 */
public abstract class NodePrefix<C extends ParserRuleContext> extends Node<C> {

    Optional<NodePrefix> prefix;

    public NodePrefix(C context, AshParserVisitor visitor) {
        super(context, visitor);
        prefix = Optional.empty();
    }

    @Override
    public String toString() {
        return "NodePrefix{" +
                "prefix=" + prefix +
                "} " + super.toString();
    }
}
