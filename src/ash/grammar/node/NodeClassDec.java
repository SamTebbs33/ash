package ash.grammar.node;

import ash.error.Error;
import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.semantics.EnumType;
import ash.semantics.member.Type;

import java.util.Optional;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeClassDec extends NodeTypeDec<AshParser.ClassDecContext> {

    NodeClassBlock block;

    public NodeClassDec(AshParser.ClassDecContext context, AshParserVisitor visitor) {
        super(context, context.mods(), context.ID(), context.params(), context.typeDecSupers(), visitor);
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
        supers.forEach(superType -> {
            Optional<Type> typeOpt = Type.getType(superType.name);
            if(typeOpt.isPresent()) {
                Type type = typeOpt.get();
                switch(type.type) {
                    case ENUM:
                        error(Error.TYPE_CANNOT_EXTEND, "class", "enum");
                        break;
                    case CLASS:
                        if(newType.superClass.isPresent()) error(Error.CLASS_CANNOT_EXTEND_SEVERAL_CLASSES);
                        else newType.superClass = Optional.of(type);
                        break;
                    case INTERFACE:
                        if(!newType.superInterfaces.contains(type)) newType.superInterfaces.add(type);
                        break;
                }
            }
        });
        if(!newType.superClass.isPresent()) newType.superClass = Optional.of(Type.OBJECT);
    }

    @Override
    public void preAnalyse() {
        super.preAnalyse();
        if(!getErrored()){
            Type.pushType(newType);
            preAnalyse(block);
            Type.popType();
        }
    }

    @Override
    protected EnumType getEnumType() {
        return EnumType.CLASS;
    }
}
