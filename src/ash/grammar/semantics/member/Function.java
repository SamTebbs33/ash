package ash.grammar.semantics.member;

import ash.grammar.semantics.Modifier;

import java.util.List;

/**
 * Created by samtebbs on 26/03/2016.
 */
public class Function extends Member {
    public Function(String name, List<Modifier> mods) {
        super(Type.peekType().get().name.add(name), mods);
    }
}
