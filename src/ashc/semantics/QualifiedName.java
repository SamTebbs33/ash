package ashc.semantics;

import java.util.*;

/**
 * Ash
 *
 * @author samtebbs, 15:02:23 - 23 May 2015
 */
public class QualifiedName {

    public String shortName = null, enclosingType = "";
    public LinkedList<String> sections = new LinkedList<String>();

    public QualifiedName(final String name) {
        final String[] sections = name.split("\\.");
        for (final String section : sections)
            add(section);
    }

    public QualifiedName add(final String section) {
        if (!section.isEmpty()) {
            sections.add(section);
            if (shortName != null) if (enclosingType.isEmpty()) enclosingType = shortName;
            else enclosingType += "." + shortName;
            shortName = section;
        }
        return this;
    }

    public QualifiedName copy() {
        final QualifiedName name = new QualifiedName("");
        for (final String section : sections)
            name.add(section);
        return name;
    }

    public static QualifiedName fromDirPath(final String path) {
        final QualifiedName name = new QualifiedName("");
        for (final String str : path.split("/"))
            name.add(str);
        return name;
    }

    public static QualifiedName fromClass(final Class<?> cls) {
        final QualifiedName name = new QualifiedName("");
        final String className = cls.getName();
        for (final String section : className.split("\\."))
            name.add(section);
        return name;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < (sections.size() - 1); i++)
            str += sections.get(i) + ".";
        if (!sections.isEmpty()) str += sections.getLast();
        return str;
    }

    public static QualifiedName fromPath(final String path) {
        final QualifiedName name = new QualifiedName("");
        for (final String str : path.split("\\."))
            name.add(str);
        return name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof QualifiedName) return sections.equals(((QualifiedName) obj).sections);
        return false;
    }

    public void pop() {
        if (!sections.isEmpty()) sections.removeLast();
    }

    public String toBytecodeName() {
        return toString().replace('.', '/');
    }

    public void setLast(final String string) {
        pop();
        add(string);
    }

}
