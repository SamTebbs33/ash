package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

import java.util.List;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeFile extends Node<AshParser.FileContext> {

    private NodePackage packageDec;
    private List<NodeImport> imports;
    private List<NodeTypeDec> typeDecs;

    public NodeFile(AshParser.FileContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.packageDec = visitor.visitOrNull(context.packageDec(), visitor::visitPackageDec);
        this.imports = visitor.visit(context.importDec(), visitor::visitImportDec);
        this.typeDecs = visitor.visit(context.typeDec(), visitor::visitTypeDec);
    }

}
