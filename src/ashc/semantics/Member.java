package ashc.semantics;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;

import ashc.semantics.Semantics.TypeI;

/**
 * Ash
 * @author samtebbs, 15:02:05 - 23 May 2015
 */
public class Member {

    public QualifiedName qualifiedName;
    public int modifiers;

    public Member(final QualifiedName qualifiedName, final int modifiers) {
	this.qualifiedName = qualifiedName;
	this.modifiers = modifiers;
    }

    public static enum EnumType {
	CLASS,
	ENUM,
	INTERFACE
    }

    public static class Type extends Member {
	public EnumType type;
	public LinkedList<Function> functions = new LinkedList<Function>();
	public LinkedList<Field> fields = new LinkedList<Field>();

	public Type(final QualifiedName qualifiedName, final int modifiers, final EnumType type) {
	    super(qualifiedName, modifiers);
	    this.type = type;
	}

	@Override
	public boolean equals(final Object obj) {
	    if (obj instanceof Type) return qualifiedName.shortName.equals(((Type) obj).qualifiedName.shortName);
	    else if (obj instanceof String) return qualifiedName.shortName.equals(obj);
	    else return false;
	}

    }

    public static class Function extends Member {

	public LinkedList<TypeI> parameters = new LinkedList<TypeI>();
	public TypeI returnType;

	public Function(final QualifiedName qualifiedName, final int modifiers) {
	    super(qualifiedName, modifiers);
	}

	public static Function fromMethod(final Method method) {
	    final QualifiedName name = QualifiedName.fromClass(method.getDeclaringClass());
	    name.add(method.getName());

	    final Function func = new Function(name, method.getModifiers());

	    final Parameter[] params = method.getParameters();
	    for (final Parameter param : params)
		func.parameters.add(TypeI.fromClass(param.getClass()));
	    func.returnType = TypeI.fromClass(method.getReturnType());
	    return func;
	}

    }

    public static class Field extends Member {

	public TypeI type;

	public Field(final QualifiedName qualifiedName, final int modifiers, final TypeI type) {
	    super(qualifiedName, modifiers);
	    this.type = type;
	}

	public static Field from(final java.lang.reflect.Field field) {
	    final int mods = field.getModifiers();
	    final TypeI type = TypeI.fromClass(field.getType());
	    final QualifiedName name = QualifiedName.fromClass(field.getDeclaringClass());
	    name.add(field.getName());
	    return new Field(name, mods, type);
	}

    }

}
