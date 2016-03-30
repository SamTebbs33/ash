package ash.grammar.node;

import ash.error.Error;
import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.semantics.QualifiedName;
import ash.semantics.TypeInstance;
import ash.semantics.member.Type;

import java.util.Optional;

/**
 * Created by samtebbs on 10/03/2016.
 */
public class NodeType extends Node<AshParser.TypeContext> {

    Optional<String> shortName;
    Optional<NodeQualifiedName> name;
    Optional<String> primitive;
    QualifiedName qualifiedName;
    final int arrDims;

    public NodeType(AshParser.TypeContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.name = visitor.visitOrNull(context.qualifiedName(), visitor::visitQualifiedName);
        this.shortName = visitor.visitOrNull(context.ID(), visitor::visitTerminal);
        this.primitive = visitor.visitOrNull(context.PRIMITIVE(), visitor::visitTerminal);
        this.arrDims = context.ARRAY_DIM().size();
        if(this.name.isPresent()) qualifiedName = this.name.get().name;
        else if(this.shortName.isPresent()) qualifiedName = new QualifiedName(this.shortName.get());
        else this.qualifiedName = new QualifiedName(this.primitive.get());
    }

    @Override
    public String toString() {
        return "NodeType{" +
                "name=" + name +
                "} " + super.toString();
    }

    @Override
    public void analyse() {

        if(!Type.typeExists(qualifiedName)) error(Error.TYPE_DOES_NOT_EXIST, qualifiedName);
    }

    public TypeInstance asType() {
        return new TypeInstance(arrDims, qualifiedName);
    }
}
