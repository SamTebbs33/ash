package ash.grammar.node;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by samtebbs on 19/03/2016.
 */
public interface Analysable extends Errorable {

    public void analyse();

    public default <T extends Analysable> void analyse(Optional<T> opt) {
        if(opt.isPresent()) analyse(opt.get());
    }

    public default void analyse(Analysable n) {
        n.analyse();
        if(n.getErrored()) this.setErrored();
    }

    public default <T extends Analysable> void analyse(List<T> nodes) {
        nodes.forEach(node -> analyse(node));
    }

    public default <T extends Analysable> void analyse(T... objs) {
        Arrays.stream(objs).forEach(this::analyse);
    }

}
