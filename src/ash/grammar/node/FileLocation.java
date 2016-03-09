package ash.grammar.node;

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

}
