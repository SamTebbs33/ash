package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

import java.util.List;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeQualifiedName extends Node<AshParser.QualifiedNameContext> {

    List<String> sections;

    public NodeQualifiedName(AshParser.QualifiedNameContext context, AshParserVisitor visitor) {
        super(context, visitor);
        sections = visitor.visit(context.ID(), visitor::visitTerminal);
    }
}
