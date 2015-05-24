package ashc.semantics;

import java.util.LinkedList;

/**
 * Ash
 * @author samtebbs, 15:02:23 - 23 May 2015
 */
public class QualifiedName {

    public String shortName;
    public LinkedList<String> sections = new LinkedList<String>();

    public QualifiedName(final String section) {
	add(section);
    }

    public void add(final String section) {
	if (!section.isEmpty()) {
	    sections.add(section);
	    shortName = section;
	}
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

    public String toString(){
	String str = "";
	for(int i = 0; i < sections.size() - 1; i++) str += sections.get(i) + ".";
	str += sections.getLast();
	return str;
    }

    public static QualifiedName fromPath(String path) {
	final QualifiedName name = new QualifiedName("");
	for (final String str : path.split("\\."))
	    name.add(str);
	return name;
    }
    
}
