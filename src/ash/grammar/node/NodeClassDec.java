package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.grammar.semantics.EnumType;
import ash.grammar.semantics.member.Type;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeClassDec extends NodeTypeDec<AshParser.ClassDecContext> {

    NodeClassBlock block;

    public NodeClassDec(AshParser.ClassDecContext context, AshParserVisitor visitor) {
        super(context, context.mods(), context.ID(), context.typeDecParams(), context.typeDecSupers(), visitor);
        this.block = visitor.visitClassBlock(context.classBlock());
    }

    @Override
    public String toString() {
        return "NodeClassDec{" +
                "block=" + block +
                "} " + super.toString();
    }

    @Override
    public void analyse() {
        analyse(block);
    }

    @Override
    public void preAnalyse() {
        Type.pushType(new Type(id, mods, EnumType.CLASS));
    }
}
