package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

import java.util.List;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeClassBlock extends Node<AshParser.ClassBlockContext> {

    List<NodeVarDec> varDecs;
    List<NodeFuncDec> funcDecs;

    public NodeClassBlock(AshParser.ClassBlockContext context, AshParserVisitor visitor) {
        super(context, visitor);
        varDecs = visitor.visit(context.varDec(), visitor::visitVarDec);
        funcDecs = visitor.visit(context.funcDec(), visitor::visitFuncDec);
    }

    @Override
    public void analyse() {
        analyse(varDecs);
        analyse(funcDecs);
    }

    @Override
    public String toString() {
        return "NodeClassBlock{" +
                "varDecs=" + varDecs +
                ", funcDecs=" + funcDecs +
                '}';
    }
}
