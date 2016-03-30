package ash.grammar.semantics.member;

import ash.grammar.semantics.Modifier;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by samtebbs on 26/03/2016
 */
public class Variable extends Member {
    public Variable(String name, List<Modifier> mods) {
        super(Type.peekType().get().name.add(name), mods);
    }

    public Variable(String name) {
        this(name, new LinkedList<>());
    }

}
