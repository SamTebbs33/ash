package ashc.load;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import ashc.semantics.Member.EnumType;
import ashc.semantics.Member.Function;
import ashc.semantics.Member.Type;
import ashc.semantics.QualifiedName;
import ashc.semantics.Semantics;

/**
 * Ash
 * @author samtebbs, 20:15:46 - 23 May 2015
 */
public class TypeImporter {

    public static ClassLoader loader = ClassLoader.getSystemClassLoader();

    public static boolean loadClass(final String path) {
	String shortName = path.substring(path.lastIndexOf('.')+1);
	// Check if it's already been imported
	if(Semantics.bindingExists(shortName)) return true;
	try {
	    final Class<?> cls = loader.loadClass(path);
	    final EnumType enumType = cls.isEnum() ? EnumType.ENUM : cls.isInterface() ? EnumType.INTERFACE : EnumType.CLASS;
	    final Type type = new Type(QualifiedName.fromPath(path), cls.getModifiers(), enumType);
	    Semantics.bindName(type.qualifiedName);
	    Semantics.addType(type);
	    for (final Method method : cls.getMethods())
		type.functions.add(Function.fromMethod(method));
	    for (final Field field : cls.getFields())
		type.fields.add(ashc.semantics.Member.Field.from(field));
	    Semantics.addType(type);
	} catch (final ClassNotFoundException e) {
	    e.printStackTrace();
	}
	return true;
    }

}
