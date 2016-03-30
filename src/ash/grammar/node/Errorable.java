package ash.grammar.node;

/**
 * Created by samtebbs on 20/03/2016.
 */
public interface Errorable {

    public void setErrored();
    public boolean getErrored();

    public default void ifNotErrored(Runnable r) {
        if(!getErrored()) r.run();
    }

}
