package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.grammar.semantics.TypeInstance;

import java.util.Optional;

/**
 * Created by samtebbs on 10/03/2016.
 */
public class NodeType extends Node<AshParser.TypeContext> {

    Optional<NodeQualifiedName> name;
    Optional<String> primitive;
    final int arrDims;

    public NodeType(AshParser.TypeContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.name = visitor.visitOrNull(context.qualifiedName(), visitor::visitQualifiedName);
        this.primitive = visitor.visitOrNull(context.PRIMITIVE(), visitor::visitTerminal);
        this.arrDims = context.ARRAY_DIM().size();
    }

    @Override
    public String toString() {
        return "NodeType{" +
                "name=" + name +
                "} " + super.toString();
    }

    @Override
    public void analyse() {
        // TODO
    }

    public TypeInstance asType() {
        if(primitive.isPresent()) return new TypeInstance(arrDims, primitive.get());
        else return new TypeInstance(arrDims, name.get().toString());
    }
}
