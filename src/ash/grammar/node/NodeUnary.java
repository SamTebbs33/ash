package ash.grammar.node;

import ash.error.Error;
import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.semantics.Operator;
import ash.semantics.TypeInstance;

import java.util.Optional;

/**
 * Created by samtebbs on 16/03/2016.
 */
public class NodeUnary extends Node<AshParser.ExprContext> implements Expression {

    Expression expr;
    String op;
    boolean prefix;

    TypeInstance exprType;
    Optional<TypeInstance> type;

    public NodeUnary(AshParser.ExprContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.expr = visitor.visitExpr(context.expr(0));
        if(context.prefixOp != null) {
            this.op = visitor.visitToken(context.prefixOp);
            this.prefix = true;
        } else {
            this.op = visitor.visitToken(context.postfixOp);
            this.prefix = false;
        }
    }

    @Override
    public String toString() {
        return "NodeUnary{" +
                "expr=" + expr +
                ", op='" + op + '\'' +
                ", prefix=" + prefix +
                "} " + super.toString();
    }

    @Override
    public TypeInstance getType() {
        return null;
    }

    @Override
    public void analyse() {
        analyse(expr);
        if(!getErrored()) {
            exprType = expr.getType();
            type = Operator.getOperationResult(exprType, op);
            if(!type.isPresent()) error(Error.UNDEFINED_UNARY_OP_BEHAVIOUR, op, exprType);
        }
    }
}
