package ash.semantics.member;

import ash.semantics.Modifier;
import ash.semantics.QualifiedName;
import ash.semantics.TypeInstance;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by samtebbs on 26/03/2016
 */
public class Variable extends Member {

    public TypeInstance type;

    public Variable(QualifiedName name, List<Modifier> mods, TypeInstance type) {
        super(name, mods);
        this.type = type;
    }

    public Variable(String name, List<Modifier> mods, TypeInstance type) {
        this(Type.peekType().get().name.add(name), mods, type);
    }

    public Variable(String name, TypeInstance type) {
        this(name, new LinkedList<>(), type);
    }

}
