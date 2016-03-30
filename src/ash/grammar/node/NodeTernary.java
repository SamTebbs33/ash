package ash.grammar.node;

import ash.error.Error;
import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.semantics.TypeInstance;

/**
 * Created by samtebbs on 16/03/2016.
 */
public class NodeTernary extends Node<AshParser.ExprContext> implements Expression {

    Expression boolExpr, expr1, expr2;

    public NodeTernary(AshParser.ExprContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.boolExpr = visitor.visitExpr(context.expr(0));
        this.expr1 = visitor.visitExpr(context.expr(1));
        this.expr2 = visitor.visitExpr(context.expr(2));
    }

    @Override
    public String toString() {
        return "NodeTernary{" +
                "boolExpr=" + boolExpr +
                ", expr1=" + expr1 +
                ", expr2=" + expr2 +
                "} " + super.toString();
    }

    @Override
    public TypeInstance getType() {
        // TODO
        return null;
    }

    @Override
    public void analyse() {
        analyse(boolExpr, expr1, expr2);
        ifNotErrored(() -> {
            TypeInstance boolExprType = boolExpr.getType();
            if(boolExprType != TypeInstance.BOOL) error(Error.EXPECTED_OTHER_TYPE, TypeInstance.BOOL, boolExprType);
        });
    }
}
