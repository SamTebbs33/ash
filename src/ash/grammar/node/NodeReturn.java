package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

import java.util.Optional;

/**
 * Created by samtebbs on 15/03/2016.
 */
public class NodeReturn extends Node<AshParser.ReturnStmtContext> implements Statement {

    Optional<Expression> expr;

    public NodeReturn(AshParser.ReturnStmtContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.expr = visitor.visitOrNull(context.expr(), visitor::visitExpr);
    }

    @Override
    public String toString() {
        return "NodeReturn{" +
                "expr=" + expr +
                "} " + super.toString();
    }

    @Override
    public void analyse() {
        // TODO
    }
}
