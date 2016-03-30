package ash.grammar.node;

import ash.error.Error;
import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.grammar.semantics.TypeInstance;

import java.util.List;
import java.util.Optional;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeVarDec extends NodeDeclaration<AshParser.VarDecContext> {

    List<String> mods;
    String id;
    Optional<NodeType> type;
    Optional<Expression> expression;
    boolean isConst;

    public NodeVarDec(AshParser.VarDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.mods = visitor.visitMods(context.mods());
        this.id = visitor.visitTerminal(context.ID());
        this.type = visitor.visitOrNull(context.type(), visitor::visitType);
        this.expression = visitor.visitOrNull(context.expr(), visitor::visitExpr);
        this.isConst = context.VAR() == null;
    }

    @Override
    public String toString() {
        return "NodeVarDec{" +
                "mods=" + mods +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", expression=" + expression +
                "} " + super.toString();
    }

    @Override
    public void analyse() {
        analyse(type);
        analyse(expression);
        if(isConst) if (!expression.isPresent()) error(Error.CONST_VAR_REQUIRES_EXPRESSION, id);
        ifNotErrored(() -> {
            if(type.isPresent() && expression.isPresent()){
                TypeInstance type1 = type.get().asType(), exprType = expression.get().getType();
                if(type1.canBeAssignedTo(exprType)) error(Error.TYPE_CANNOT_BE_CONVERTED, type1, exprType);
            }
        });
    }

    @Override
    public void preAnalyse() {

    }
}
