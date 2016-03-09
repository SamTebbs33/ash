package ash.grammar.node;

import ash.grammar.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodePackage extends Node<AshParser.PackageDecContext> {

    private NodeQualifiedName qualifiedName;

    public NodePackage(AshParser.PackageDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
        qualifiedName = visitor.visitQualifiedName(context.qualifiedName());
    }
}
