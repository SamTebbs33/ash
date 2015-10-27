package ashc.library;

import java.io.*;
import java.util.*;
import java.util.jar.*;

/**
 * Ash
 *
 * @author samtebbs, 09:54:20 - 23 Jul 2015
 */
public class JarLibrary extends Library {

    public File file;
    public JarFile jarFile;
    public HashMap<String, JarEntry> entryMap = new HashMap<String, JarEntry>();

    public JarLibrary(final File file) throws IOException {
        this.file = file;
        jarFile = new JarFile(file);
        final Enumeration<JarEntry> it = jarFile.entries();
        while (it.hasMoreElements()) {
            final JarEntry entry = it.nextElement();
            entryMap.put(entry.getName().replace(File.separatorChar, '.').replace(".class", ""), entry);
        }
    }

    @Override
    public InputStream getStream(final String classQualifiedName) throws IOException {
        final JarEntry entry = entryMap.get(classQualifiedName);
        return entry == null ? null : jarFile.getInputStream(entry);
    }

}
