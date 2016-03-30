package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

import java.util.List;

/**
 * Created by samtebbs on 04/04/2016.
 */
public class NodeList extends Node<AshParser.ExprContext> {

    public final List<Expression> exprs;

    public NodeList(AshParser.ExprContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.exprs = visitor.visit(context.expr(), visitor::visitExpr);
    }

    @Override
    public void analyse() {

    }
}
