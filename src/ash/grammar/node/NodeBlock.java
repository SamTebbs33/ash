package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.grammar.semantics.Scope;

import java.util.List;

/**
 * Created by samtebbs on 15/03/2016.
 */
public class NodeBlock extends Node<AshParser.BracedBlockContext> implements Statement {

    List<Statement> stmts;

    public NodeBlock(AshParser.BracedBlockContext context, AshParserVisitor visitor) {
        super(context, visitor);
        stmts = visitor.visit(context.stmt(), visitor::visitStmt);
    }

    @Override
    public void analyse() {
        Scope.push(new Scope());
        analyse(stmts);
        Scope.pop();
    }

    @Override
    public String toString() {
        return "NodeBlock{" +
                "stmts=" + stmts +
                '}';
    }
}
