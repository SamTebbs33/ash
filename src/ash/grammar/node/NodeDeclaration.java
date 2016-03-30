package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Created by samtebbs on 20/03/2016.
 */
public abstract class NodeDeclaration<C extends ParserRuleContext> extends Node<C> implements PreAnalysable {
    public NodeDeclaration(C context, AshParserVisitor visitor) {
        super(context, visitor);
    }
}
