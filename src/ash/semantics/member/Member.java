package ash.semantics.member;

import ash.semantics.Modifier;
import ash.semantics.QualifiedName;

import java.util.List;

/**
 * Created by samtebbs on 26/03/2016.
 */
public abstract class Member {

    public final QualifiedName name;
    public final List<Modifier> mods;

    public Member(QualifiedName name, List<Modifier> mods) {
        this.name = name;
        this.mods = mods;
    }

    public Member(String shortName, List<Modifier> mods) {
        this(new QualifiedName(shortName), mods);
    }
}
