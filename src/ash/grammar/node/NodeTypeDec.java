package ash.grammar.node;

import ash.error.Error;
import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.semantics.EnumType;
import ash.semantics.Modifier;
import ash.semantics.member.Type;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.Optional;

/**
 * Created by samtebbs on 09/03/2016.
 */
public abstract class NodeTypeDec<C extends ParserRuleContext> extends NodeDeclaration<C> {

    List<Modifier> mods;
    String id;
    List<NodeFuncParam> params;
    List<NodeQualifiedName> supers;

    protected Type newType;

    public NodeTypeDec(C ctx, AshParser.ModsContext mods, TerminalNode id, AshParser.ParamsContext params, AshParser.TypeDecSupersContext supers, AshParserVisitor visitor) {
        super(ctx, visitor);
        this.mods = Modifier.fromList(visitor.visitMods(mods));
        this.id = visitor.visitTerminal(id);
        this.params = visitor.visitOrEmpty(params, visitor::visitParams);
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

    @Override
    public void preAnalyse() {
        // Check if the class has already been defined
        newType = new Type(id, mods, getEnumType());
        if(Type.typeExists(newType.name)) error(Error.TYPE_ALREADY_DEFINED, newType.type.name().toLowerCase(), id);
    }

    protected abstract EnumType getEnumType();
}
