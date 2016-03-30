package ash.library;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Ash
 *
 * @author samtebbs, 09:54:20 - 23 Jul 2015
 */
public class JarLibrary extends Library {

    public JarFile jarFile;
    public HashMap<String, JarEntry> entryMap = new HashMap<String, JarEntry>();

    public JarLibrary(final File file) throws IOException {
        if (file.exists()) {
            jarFile = new JarFile(file);
            final Enumeration<JarEntry> it = jarFile.entries();
            while (it.hasMoreElements()) {
                final JarEntry entry = it.nextElement();
                entryMap.put(entry.getName().replace(File.separatorChar, '.').replace(".class", ""), entry);
            }
        }
    }

    @Override
    public InputStream getStream(final String classQualifiedName) throws IOException {
        final JarEntry entry = entryMap.get(classQualifiedName);
        return entry == null ? null : jarFile.getInputStream(entry);
    }

}
