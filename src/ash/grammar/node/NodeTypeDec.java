package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.grammar.semantics.Modifier;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

/**
 * Created by samtebbs on 09/03/2016.
 */
public abstract class NodeTypeDec<C extends ParserRuleContext> extends NodeDeclaration<C> {

    List<Modifier> mods;
    String id;
    List<NodeFuncParam> params;
    List<NodeQualifiedName> supers;

    public NodeTypeDec(C ctx, AshParser.ModsContext mods, TerminalNode id, AshParser.TypeDecParamsContext params, AshParser.TypeDecSupersContext supers, AshParserVisitor visitor) {
        super(ctx, visitor);
        this.mods = Modifier.fromList(visitor.visitMods(mods));
        this.id = visitor.visitTerminal(id);
        this.params = visitor.visitOrEmpty(params, visitor::visitTypeDecParams);
        this.supers = visitor.visitOrEmpty(supers, visitor::visitTypeDecSupers);
    }

    @Override
    public String toString() {
        return "NodeTypeDec{" +
                "mods=" + mods +
                ", id='" + id + '\'' +
                ", params=" + params +
                ", supers=" + supers +
                "} " + super.toString();
    }
}
