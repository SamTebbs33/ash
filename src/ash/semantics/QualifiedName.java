package ash.semantics;

import java.io.File;
import java.util.Arrays;
import java.util.List;
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

    public QualifiedName() { this(Arrays.asList()); }

    public QualifiedName add(String name) {
        List<String> list2 = sections.stream().collect(Collectors.toList());
        list2.add(name);
        return new QualifiedName(list2);
    }

    public String getShortName() {
        return sections.get(sections.size() - 1);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < sections.size(); i++) {
            sb.append(sections.get(i));
            if(i < sections.size() - 1) sb.append(".");
        }
        return sb.toString();
    }

    public String toPathString() {
        return toString().replace('.', File.separatorChar);
    }

    public static QualifiedName fromPathString(String name) {
        return new QualifiedName(name.replace(File.separatorChar, '.'));
    }

    public QualifiedName pop() {
        QualifiedName name = new QualifiedName();
        for(int i = 0; i < sections.size() - 1; i++) name = name.add(sections.get(i));
        return name;
    }
}
