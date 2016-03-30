package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

import java.util.List;
import java.util.Optional;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeFile extends Node<AshParser.FileContext> implements PreAnalysable {

    private Optional<NodePackage> packageDec;
    private List<NodeImport> imports;
    private List<NodeClassDec> classDecs;

    public NodeFile(AshParser.FileContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.packageDec = visitor.visitOrNull(context.packageDec(), visitor::visitPackageDec);
        this.imports = visitor.visit(context.importDec(), visitor::visitImportDec);
        this.classDecs = visitor.visit(context.classDec(), visitor::visitClassDec);
    }

    @Override
    public String toString() {
        return "NodeFile{" +
                "packageDec=" + packageDec +
                ", imports=" + imports +
                ", classDecs=" + classDecs +
                "} " + super.toString();
    }

    @Override
    public void analyse() {
        analyse(packageDec);
        analyse(imports);
        analyse(classDecs);
    }

    @Override
    public void preAnalyse() {
        preAnalyse(packageDec);
        preAnalyse(imports);
        preAnalyse(classDecs);
    }
}
