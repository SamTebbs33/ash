package ash.grammar.semantics;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by samtebbs on 26/03/2016.
 */
public class QualifiedName {

    public final List<String> sections;

    public QualifiedName(List<String> sections) {
        this.sections = sections;
    }

    public QualifiedName(String shortName) {
        this(Arrays.asList(shortName.split("\\.")));
    }

    public QualifiedName add(String name) {
        List<String> list2 = sections.stream().collect(Collectors.toList());
        list2.add(name);
        return new QualifiedName(list2);
    }

    public String getShortName() {
        return sections.get(sections.size() - 1);
    }
}
