package ash.grammar.node;

import org.antlr.v4.runtime.Token;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class FileLocation {

    public final int line, column, length;

    public FileLocation(int line, int column, int length) {
        this.line = line;
        this.column = column;
        this.length = length;
    }

    public FileLocation(Token token) {
        this(token.getLine(), token.getCharPositionInLine(), token.getStopIndex() - token.getStartIndex() + token.getCharPositionInLine());
    }

    public int getEndColumn() {
        return column + length;
    }
}
