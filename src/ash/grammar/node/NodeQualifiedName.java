package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.semantics.QualifiedName;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeQualifiedName extends Node<AshParser.QualifiedNameContext> {

    QualifiedName name;

    public NodeQualifiedName(AshParser.QualifiedNameContext context, AshParserVisitor visitor) {
        super(context, visitor);
        name = new QualifiedName(visitor.visit(context.ID(), visitor::visitTerminal));
    }

    @Override
    public String toString() {
        return name.toString();
    }

    @Override
    public void analyse() {

    }
}
