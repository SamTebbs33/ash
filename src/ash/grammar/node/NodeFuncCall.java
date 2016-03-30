package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.semantics.TypeInstance;

import java.util.List;
import java.util.Optional;

/**
 * Created by samtebbs on 14/03/2016.
 */
public class NodeFuncCall extends NodePrefix<AshParser.FuncCallContext> implements Expression, Statement {

    String id;
    List<Expression> args;

    public NodeFuncCall(AshParser.FuncCallContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.id = visitor.visitTerminal(context.ID());
        this.args = visitor.visitOrEmpty(context.expr(), visitor::visitExpr);
        NodePrefix suffix = visitor.visitSuffix(context.suffix());
        suffix.prefix = Optional.of(this);
    }

    @Override
    public String toString() {
        return "NodeFuncCall{" +
                "id='" + id + '\'' +
                ", args=" + args +
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
