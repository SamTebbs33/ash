package ash.grammar.ash.grammar.node;

import ash.grammar.AshParser;

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
        this.packageDec = visitor.visitPackageDec(context.packageDec());
        this.imports = visitor.visit(context.importDec(), visitor::visitImportDec);
        this.typeDecs = visitor.visit(context.typeDec(), visitor::visitTypeDec);
    }

}
