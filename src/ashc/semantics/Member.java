package ashc.semantics;

import java.lang.reflect.*;
import java.util.*;

import ashc.grammar.Node.IExpression;
import ashc.grammar.Node.NodeExprs;
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
	public LinkedList<Type> supers = new LinkedList<Member.Type>();

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

	public Field getField(final String id) {
	    for (final Field field : fields)
		if (field.qualifiedName.shortName.equals(id)) return field;
	    return null;
	}

	public TypeI getFuncType(final String id, final NodeExprs args) {
	    final LinkedList<TypeI> parameters = new LinkedList<TypeI>();
	    for (final IExpression arg : args.exprs)
		parameters.add(arg.getExprType());
	    for (final Function func : functions)
		if (func.paramsAreEqual(parameters)) if (func.qualifiedName.shortName.equals(id)) return func.returnType;
	    return null;
	}

	public boolean hasSuper(final QualifiedName name) {
	    for (final Type type : supers)
		if (type.qualifiedName.equals(name)) return true;
		else if (type.hasSuper(name)) return true;
	    return false;
	}

	public Function getFunc(final String id, final NodeExprs args) {
	    final LinkedList<TypeI> parameters = new LinkedList<TypeI>();
	    for (final IExpression arg : args.exprs)
		parameters.add(arg.getExprType());
	    return getFunc(id, parameters);
	}

	public Function getFunc(final String id, final LinkedList<TypeI> parameters) {
	    for (final Function func : functions)
		if (func.paramsAreEqual(parameters)) if (func.qualifiedName.shortName.equals(id)) return func;
	    Function func = null;
	    for (final Type superType : supers)
		if ((func = superType.getFunc(id, parameters)) != null) return func;
	    return null;
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

	@Override
	public boolean equals(final Object obj) {
	    if (obj instanceof Function) {
		final Function func = (Function) obj;
		return qualifiedName.equals(func.qualifiedName) && paramsAreEqual(func.parameters);
	    }
	    return false;
	}

	private boolean paramsAreEqual(final LinkedList<TypeI> params2) {
	    if (parameters.size() != params2.size()) return false;
	    for (int i = 0; i < Math.min(parameters.size(), params2.size()); i++)
		if (!parameters.get(i).equals(params2.get(i))) return false;
	    return true;
	}

	@Override
	public String toString() {
	    return "Function [parameters=" + parameters + ", returnType=" + returnType + ", qualifiedName=" + qualifiedName + ", modifiers=" + modifiers + "]";
	}

    }

    public static class Variable {

	public String id;
	public TypeI type;

	public Variable(final String id, final TypeI type) {
	    this.id = id;
	    this.type = type;
	}

	@Override
	public String toString() {
	    return "Variable [id=" + id + ", type=" + type + "]";
	}
    }

    public static class Field extends Variable {

	public QualifiedName qualifiedName;
	public int modifiers;

	public Field(final QualifiedName qualifiedName, final int modifiers, final TypeI type) {
	    super(qualifiedName.shortName, type);
	    this.qualifiedName = qualifiedName;
	    this.modifiers = modifiers;
	}

	public static Field from(final java.lang.reflect.Field field) {
	    final int mods = field.getModifiers();
	    final TypeI type = TypeI.fromClass(field.getType());
	    final QualifiedName name = QualifiedName.fromClass(field.getDeclaringClass());
	    name.add(field.getName());
	    return new Field(name, mods, type);
	}

	@Override
	public boolean equals(final Object obj) {
	    if (obj instanceof Field) return ((Field) obj).qualifiedName.equals(qualifiedName);
	    return false;
	}

    }

}
