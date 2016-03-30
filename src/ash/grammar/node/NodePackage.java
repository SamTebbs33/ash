package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodePackage extends Node<AshParser.PackageDecContext> implements PreAnalysable {

    private NodeQualifiedName qualifiedName;

    public NodePackage(AshParser.PackageDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
        qualifiedName = visitor.visitQualifiedName(context.qualifiedName());
    }

    @Override
    public String toString() {
        return "NodePackage{" +
                "qualifiedName=" + qualifiedName +
                "} " + super.toString();
    }

    @Override
    public void analyse() {
        analyse(qualifiedName);
    }

    @Override
    public void preAnalyse() {
        // TODO
    }
}
