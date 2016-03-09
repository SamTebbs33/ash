package ash.grammar.node;

import ash.Ash;
import ash.grammar.AshParserVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

/**
 * Created by samtebbs on 09/03/2016.
 */
public abstract class Node<Ctx extends ParserRuleContext> {

    private final FileLocation location;

    public Node(Ctx context, AshParserVisitor visitor) {
        Interval interval = context.getSourceInterval();
        Token token = Ash.tokenStream.get(interval.a);
        location = new FileLocation(token);
    }

}
