package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeImport extends Node<AshParser.ImportDecContext> implements PreAnalysable {

    NodeQualifiedName qualifiedName;

    public NodeImport(AshParser.ImportDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.qualifiedName = visitor.visitQualifiedName(context.qualifiedName());
    }

    @Override
    public String toString() {
        return "NodeImport{" +
                "qualifiedName=" + qualifiedName +
                "} " + super.toString();
    }

    @Override
    public void analyse() {

    }

    @Override
    public void preAnalyse() {
        // TODO
    }
}
