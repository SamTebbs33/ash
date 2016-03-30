package ash.library;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;

/**
 * Ash
 *
 * @author samtebbs, 09:45:35 - 23 Jul 2015
 */
public abstract class Library {

    public static Library ashLibrary;
    public static Library[] javaLibraries;

    public static void loadLibs() {
        // Find the Java rt.jar
        String[] jarLocations = System.getProperty("sun.boot.class.path").split(":");
        javaLibraries = new Library[jarLocations.length];
        for (int i = 0; i < jarLocations.length; i++)
            try {
                javaLibraries[i] = new JarLibrary(new File(jarLocations[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static Optional<InputStream> getClassStream(final String classQualifiedName) {
        for (Library lib : javaLibraries) {
            InputStream is = null;
            try {
                is = lib.getStream(classQualifiedName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (is != null) return Optional.of(is);
        }
        return Optional.empty();
    }

    public abstract InputStream getStream(String classQualifiedName) throws IOException;

}
