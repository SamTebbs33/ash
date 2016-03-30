package ash.semantics.member;

import ash.semantics.Modifier;
import ash.semantics.Parameters;
import ash.semantics.QualifiedName;
import ash.semantics.TypeInstance;

import java.util.List;

/**
 * Created by samtebbs on 26/03/2016.
 */
public class Function extends Member {

    public final Parameters params = new Parameters();
    public final TypeInstance returnType;

    public Function(String name, List<Modifier> mods, TypeInstance returnType) {
        this(Type.peekType().get().name.add(name), mods, returnType);
    }

    public Function(QualifiedName name, List<Modifier> mods, TypeInstance returnType) {
        super(name, mods);
        this.returnType = returnType;
    }
}
