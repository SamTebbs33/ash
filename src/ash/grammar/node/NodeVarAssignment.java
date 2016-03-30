package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

/**
 * Created by samtebbs on 14/03/2016.
 */
public class NodeVarAssignment extends Node<AshParser.VarAssignmentContext> implements Statement {

    NodeVariable var;
    String op;
    Expression expr;

    public NodeVarAssignment(AshParser.VarAssignmentContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.var = visitor.visitVar(context.var());
        this.op = visitor.visitTerminal(context.ASSIGN_OP() != null ? context.ASSIGN_OP() : context.COMPOUND_ASSIGN_OP());
        this.expr = visitor.visitExpr(context.expr());
    }

    @Override
    public String toString() {
        return "NodeVarAssignment{" +
                "var=" + var +
                ", op='" + op + '\'' +
                ", expr=" + expr +
                "} " + super.toString();
    }

    @Override
    public void analyse() {
        analyse(var, expr);
        // TODO
    }
}
