package ash.grammar.semantics;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by samtebbs on 26/03/2016.
 */
public enum Modifier {
    ;

    public static List<Modifier> fromList(List<String> strings) {
        List<Modifier> mods = new LinkedList<>();
        for(String str : strings) mods.add(Modifier.valueOf(str.toUpperCase()));
        return mods;
    }
}
