package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

import java.util.List;
import java.util.Optional;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeFuncDec extends NodeDeclaration<AshParser.FuncDecContext> {

    String id;
    List<String> mods;
    List<NodeFuncParam> params;
    NodeFuncBlock block;
    Optional<NodeType> type;

    public NodeFuncDec(AshParser.FuncDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.id = visitor.visitTerminal(context.ID());
        this.mods = visitor.visitMods(context.mods());
        this.params = visitor.visitParams(context.params());
        this.block = visitor.visitFuncBlock(context.funcBlock());
        this.type = visitor.visitOrNull(context.type(), visitor::visitType);
    }

    @Override
    public String toString() {
        return "NodeFuncDec{" +
                "id='" + id + '\'' +
                ", mods=" + mods +
                ", params=" + params +
                ", block=" + block +
                ", type=" + type +
                "} " + super.toString();
    }

    @Override
    public void analyse() {
        analyse(block);
        analyse(type);
    }

    @Override
    public void preAnalyse() {
        // TODO
    }
}
