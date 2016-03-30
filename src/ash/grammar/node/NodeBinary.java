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
public class NodeBinary extends Node<AshParser.ExprContext> implements Expression {

    final Expression expr1, expr2;
    final String op;

    TypeInstance expr1Type, expr2Type;
    Optional<TypeInstance> type;

    public NodeBinary(AshParser.ExprContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.expr1 = visitor.visitExpr(context.expr(0));
        this.expr2 = visitor.visitExpr(context.expr(1));
        this.op = visitor.visitToken(context.binaryOp);
    }

    @Override
    public void analyse() {
        analyse(expr1);
        analyse(expr2);
        if(!getErrored()) {
            expr1Type = expr1.getType();
            expr2Type = expr2.getType();
            type = Operator.getOperationResult(expr1Type, expr2Type, op);
            if(!type.isPresent()) error(Error.UNDEFINED_BIN_OP_BEHAVIOUR, op, expr1Type, expr2Type);
        }
    }

    @Override
    public String toString() {
        return "NodeBinary{" +
                "expr1=" + expr1 +
                ", expr2=" + expr2 +
                ", op='" + op + '\'' +
                "} " + super.toString();
    }

    @Override
    public TypeInstance getType() {
        return type.get();
    }
}
