package ash.grammar.node;

import ash.error.Error;
import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.semantics.Modifier;
import ash.semantics.Scope;
import ash.semantics.TypeInstance;
import ash.semantics.member.Type;
import ash.semantics.member.Variable;

import java.util.List;
import java.util.Optional;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class NodeVarDec extends NodeDeclaration<AshParser.VarDecContext> implements Statement {

    List<String> mods;
    String id;
    Optional<NodeType> type;
    Optional<Expression> expression;
    boolean isConst, isLocal = true;
    Variable var;

    public NodeVarDec(AshParser.VarDecContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.mods = visitor.visitMods(context.mods());
        this.id = visitor.visitTerminal(context.ID());
        this.type = visitor.visitOrNull(context.typeDef(), visitor::visitTypeDef);
        this.expression = visitor.visitOrNull(context.expr(), visitor::visitExpr);
        this.isConst = context.VAR() == null;
        var = new Variable(id, Modifier.fromList(mods), null);
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
            if(type.isPresent()) var.type = type.get().asType();
            else var.type = expression.get().getType();
            if(type.isPresent() && expression.isPresent()){
                TypeInstance type1 = type.get().asType(), exprType = expression.get().getType();
                if(type1.canBeAssignedTo(exprType)) error(Error.TYPE_CANNOT_BE_CONVERTED, type1, exprType);
            }
            Scope.peek().addVar(var);
        });
    }

    @Override
    public void preAnalyse() {
        isLocal = false;
        Type.currentType().fields.add(var);
    }
}
