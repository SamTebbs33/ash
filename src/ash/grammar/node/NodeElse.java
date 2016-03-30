package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

/**
 * Created by samtebbs on 15/03/2016.
 */
public class NodeElse extends Node<AshParser.ElseStmtContext> {

    NodeBlock block;

    public NodeElse(AshParser.ElseStmtContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.block = visitor.visitBracedBlock(context.bracedBlock());
    }

    @Override
    public String toString() {
        return "NodeElse{" +
                "block=" + block +
                "} " + super.toString();
    }

    @Override
    public void analyse() {
        analyse(block);
    }
}
