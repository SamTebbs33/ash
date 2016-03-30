package ash.grammar.node;

import ash.error.Error;
import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.semantics.TypeInstance;

import java.util.Optional;

/**
 * Created by samtebbs on 15/03/2016.
 */
public class NodeIf extends Node<AshParser.IfStmtContext> implements Statement {

    Expression expression;
    NodeBlock block;
    Optional<NodeIf> elseIfStmt;
    Optional<NodeElse> elseStmt;

    public NodeIf(AshParser.IfStmtContext context, AshParserVisitor visitor) {
        super(context, visitor);
        this.expression = visitor.visitExpr(context.expr());
        this.block = visitor.visitBracedBlock(context.bracedBlock());
        this.elseIfStmt = visitor.visitOrNull(context.elseIfStmt(), visitor::visitElseIfStmt);
        this.elseStmt = visitor.visitOrNull(context.elseStmt(), visitor::visitElseStmt);
    }

    @Override
    public String toString() {
        return "NodeIf{" +
                "expression=" + expression +
                ", block=" + block +
                ", elseIfStmt=" + elseIfStmt +
                ", elseStmt=" + elseStmt +
                "} " + super.toString();
    }

    @Override
    public void analyse() {
        analyse(expression, block);
        analyse(elseIfStmt);
        analyse(elseStmt);
        ifNotErrored(() -> {
            TypeInstance exprType = expression.getType();
            if(exprType != TypeInstance.BOOL) error(Error.EXPECTED_OTHER_TYPE, TypeInstance.BOOL, exprType);
        });
    }

}
