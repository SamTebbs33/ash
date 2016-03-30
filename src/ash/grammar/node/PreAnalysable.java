package ash.grammar.node;

import java.util.List;
import java.util.Optional;

/**
 * Created by samtebbs on 20/03/2016.
 */
public interface PreAnalysable extends Errorable {
    public void preAnalyse();

    public default <T extends PreAnalysable> void preAnalyse(Optional<T> opt) {
        if(opt.isPresent()) preAnalyse((PreAnalysable) opt.get());
    }

    public default void preAnalyse(PreAnalysable n) {
        n.preAnalyse();
        if(n.getErrored()) this.setErrored();
    }

    public default <T extends PreAnalysable> void preAnalyse(List<T> nodes) {
        nodes.forEach(node -> preAnalyse(node));
    }
}
