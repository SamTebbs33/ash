package ash.grammar.semantics.member;

import ash.grammar.semantics.Modifier;
import ash.grammar.semantics.QualifiedName;

import java.util.Arrays;
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
