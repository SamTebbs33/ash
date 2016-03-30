package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;

import java.util.Optional;

/**
 * Created by samtebbs on 14/03/2016.
 */
public class NodeFuncBlock extends NodeBlock {

    Optional<Statement> stmt;

    public NodeFuncBlock(AshParser.FuncBlockContext context, AshParserVisitor visitor) {
        super(context.bracedBlock(), visitor);
        this.stmt = visitor.visitOrNull(context.stmt(), visitor::visitStmt);
    }

    @Override
    public void analyse() {
        if(stmt.isPresent()) analyse(stmt);
        else super.analyse();
    }

    @Override
    public String toString() {
        return "NodeFuncBlock{" +
                "stmt=" + stmt +
                "} " + super.toString();
    }
}
