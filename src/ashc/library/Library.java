package ashc.library;

import ashc.error.AshError;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Ash
 *
 * @author samtebbs, 09:45:35 - 23 Jul 2015
 */
public abstract class Library {

    public static Library ashLibrary;
    public static Library[] javaLibraries;

    public static void findLibs() throws AshError, IOException {

        // Find the Java rt.jar
        String[] jarLocations = System.getProperty("sun.boot.class.path").split(";");
        javaLibraries = new Library[jarLocations.length];
        for (int i = 0; i < jarLocations.length; i++) javaLibraries[i] = new JarLibrary(new File(jarLocations[i]));

        // Find the Ash standard library jar, disabled until I actually add this
    /*
     * String ashHome = System.getenv("ASH_HOME"); if(ashHome == null || ashHome.isEmpty()) throw new
	 * AshError("The Ash standard library jar file has not been installed correctly"); File ashJar = new File(ashHome); if(!ashJar.exists() ||
	 * !ashJar.isFile()) throw new AshError("The Ash standard library jar file does not exist at: " + ashHome); ashLibrary = new JarLibrary(ashJar);
	 */
    }

    public static InputStream getClassStream(final String classQualifiedName) throws IOException {
        for (Library lib : javaLibraries) {
            final InputStream is = lib.getStream(classQualifiedName);
            if (is != null) return is;
        }
        return null;
    }

    public abstract InputStream getStream(String classQualifiedName) throws IOException;

}
