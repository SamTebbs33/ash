package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeFuncParam extends Node<AshParser.FuncParamContext> {

    String id;
    NodeType type;

    public NodeFuncParam(AshParser.FuncParamContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.id = visitor.visitTerminal(context.ID());
        this.type = visitor.visitType(context.type());
    }

    @Override
    public String toString() {
        return "NodeFuncParam{" +
                "id='" + id + '\'' +
                ", type=" + type +
                "} " + super.toString();
    }

    @Override
    public void analyse() {
        analyse(type);
    }
}
