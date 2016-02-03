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

    public static Library ashLibrary, javaLibrary;

    public static void findLibs() throws AshError, IOException {

        // Find the Java rt.jar
        final String temp = String.class.getResource("/java/lang/String.class").getFile();
        final int rtIndex = temp.indexOf("rt.jar");
        if (rtIndex != -1) {
            final File javaJar = new File(temp);
            if (!javaJar.exists())
                throw new AshError("The Java rt.jar file does not exist at: " + temp);
            javaLibrary = new JarLibrary(javaJar);
        } else throw new AshError("Could not find rt.jar, please ensure that Java is correctly installed");

        // Find the Ash standard library jar, disabled until I actually add this
    /*
     * String ashHome = System.getenv("ASH_HOME"); if(ashHome == null || ashHome.isEmpty()) throw new
	 * AshError("The Ash standard library jar file has not been installed correctly"); File ashJar = new File(ashHome); if(!ashJar.exists() ||
	 * !ashJar.isFile()) throw new AshError("The Ash standard library jar file does not exist at: " + ashHome); ashLibrary = new JarLibrary(ashJar);
	 */
    }

    public abstract InputStream getStream(String classQualifiedName) throws IOException;

    public static InputStream getClassStream(final String classQualifiedName) throws IOException {
        final InputStream is = javaLibrary.getStream(classQualifiedName);
        return is != null ? is : (ashLibrary != null ? ashLibrary.getStream(classQualifiedName) : null);
    }

}
