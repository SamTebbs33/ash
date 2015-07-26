package ashc.load;

import java.io.*;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import ashc.error.*;
import ashc.library.*;
import ashc.main.*;
import ashc.semantics.*;

/**
 * Ash
 *
 * @author samtebbs, 20:15:46 - 23 May 2015
 */
public class TypeImporter {

    public static ClassLoader loader = ClassLoader.getSystemClassLoader();

    public static ashc.semantics.Member.Type loadClass(final String path, final String alias) {
	// Attempt to find a source file relative to current working directory
	File srcFile = new File(path.replace('.', File.separatorChar)+".ash");
	if(srcFile.exists() && srcFile.isFile()){
	    try {
		AshCompiler compiler = new AshCompiler(srcFile.toString());
		compiler.parse();
		compiler.preAnalyse();
		compiler.analyse();
		compiler.generate();
	    } catch (IOException e) {
		AshError.compilerError("Cannot find source file: " + srcFile.toString());
	    }
	}
	try {
	    final InputStream stream = Library.getClassStream(path);
	    if (stream != null) {
		final ashc.semantics.Member.Type type = readClass(stream);
		Semantics.addType(type, false);
		return type;
	    }
	} catch (final IOException e) {
	    AshError.compilerError("Cannot find class: " + path);
	}
	return null;
    }

    private static ashc.semantics.Member.Type readClass(final InputStream stream) throws IOException {
	final ClassReader reader = new ClassReader(stream);
	final ClassNode node = new ClassNode();
	reader.accept(node, 0);
	stream.close();
	return new ashc.semantics.Member.Type(node);
    }

}
