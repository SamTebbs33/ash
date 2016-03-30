package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.semantics.QualifiedName;
import ash.semantics.member.Type;

import java.util.List;
import java.util.Optional;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeImport extends Node<AshParser.ImportDecContext> implements PreAnalysable {

    NodeQualifiedName qualifiedName;
    Optional<String> alias;
    Optional<List<String>> multiImports;

    public NodeImport(AshParser.ImportDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.qualifiedName = visitor.visitQualifiedName(context.qualifiedName());
        if(context.aliasedImport() != null) alias = visitor.visitOrNull(context.aliasedImport().ID(), visitor::visitTerminal);
        else if(context.multiImport() != null) multiImports = Optional.of(visitor.visit(context.multiImport().ID(), visitor::visitTerminal));
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
        String importAlias = alias.isPresent() ? alias.get() : qualifiedName.name.getShortName();
        Type.typeAliases.put(importAlias, qualifiedName.name);
        if(multiImports.isPresent()) {
            QualifiedName name = qualifiedName.name.pop();
            for(String i : multiImports.get()) Type.typeAliases.put(i, name.add(i));
        }
    }
}
