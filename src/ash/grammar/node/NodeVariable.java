package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.grammar.semantics.TypeInstance;

import java.util.Optional;

/**
 * Created by samtebbs on 14/03/2016.
 */
public class NodeVariable extends NodePrefix<AshParser.VarContext> implements Expression {

    String id;

    public NodeVariable(AshParser.VarContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.id = visitor.visitTerminal(context.ID());
        if (context.suffix() != null) {
            NodePrefix suffix = visitor.visitSuffix(context.suffix());
            suffix.prefix = Optional.of(this);
        }
    }

    @Override
    public String toString() {
        return "NodeVariable{" +
                "id='" + id + '\'' +
                "} " + super.toString();
    }

    @Override
    public TypeInstance getType() {
        // TODO
        return null;
    }

    @Override
    public void analyse() {
        // TODO
    }
}
