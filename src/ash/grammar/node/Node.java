package ash.grammar.node;

import ash.Ash;
import ash.error.*;
import ash.error.Error;
import ash.grammar.AshParserVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

/**
 * Created by samtebbs on 09/03/2016.
 */
public abstract class Node<C extends ParserRuleContext> implements Analysable {

    private final FileLocation location;
    private boolean errored;

    @Override
    public boolean getErrored() {
        return errored;
    }

    @Override
    public void setErrored() {
        errored = true;
    }

    public Node(C context, AshParserVisitor visitor) {
        Interval interval = context.getSourceInterval();
        Token token = Ash.tokenStream.get(interval.a);
        location = new FileLocation(token);
    }

    protected void error(Error error, Object... args) {
        AshParserErrorListener.report(AshParserErrorListener.AshErrorType.SEMANTIC, this.location, String.format(error.format, args));
        setErrored();
    }

    @Override
    public String toString() {
        return "Node{" +
                "location=" + location +
                '}';
    }

}
