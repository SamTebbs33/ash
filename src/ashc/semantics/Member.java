package ashc.semantics;

import java.lang.reflect.*;
import java.util.*;

import ashc.grammar.*;
import ashc.grammar.Node.IExpression;
import ashc.grammar.Node.NodeExprs;
import ashc.semantics.Semantics.TypeI;
import ashc.util.*;

/**
 * Ash
 *
 * @author samtebbs, 15:02:05 - 23 May 2015
 */
public class Member {

    public QualifiedName qualifiedName;
    public int modifiers;
    public Type enclosingType;
    public boolean isLocal;

    public Member(final QualifiedName qualifiedName, final int modifiers) {
	this.qualifiedName = qualifiedName;
	this.modifiers = modifiers;
    }
    
    public boolean isVisible(){
	if(isLocal) return true;
	if(BitOp.and(modifiers, EnumModifier.PUBLIC.intVal)) return true;
	if(enclosingType.equals(Semantics.currentType())) return true;
	else if(BitOp.and(modifiers, EnumModifier.PRIVATE.intVal)) return false;
	else if(Semantics.currentType().hasSuper(enclosingType.qualifiedName) && BitOp.and(modifiers, EnumModifier.PROTECTED.intVal)) return true;
	return false;
    }

    public static enum EnumType {
	CLASS, ENUM, INTERFACE
    }

    public static class Type extends Member {
	public EnumType type;
	public LinkedList<Function> functions = new LinkedList<Function>();
	public LinkedList<Field> fields = new LinkedList<Field>();
	public LinkedList<Type> supers = new LinkedList<Member.Type>();
	public LinkedList<String> generics = new LinkedList<String>();
	public HashMap<String, LinkedList<TypeI>> genericsMap = new HashMap<String, LinkedList<TypeI>>();

	public Type(final QualifiedName qualifiedName, final int modifiers, final EnumType type) {
	    super(qualifiedName, modifiers);
	    this.type = type;
	}

	public void addGeneric(final String typeName, final TypeI generic) {
	    if (genericsMap.containsKey(typeName)) genericsMap.get(typeName).add(generic);
	    else {
		final LinkedList<TypeI> list = new LinkedList<TypeI>();
		list.add(generic);
		genericsMap.put(typeName, list);
	    }
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
		if (func.qualifiedName.shortName.equals(id)) if (func.paramsAreEqual(parameters)) return func;
	    Function func = null;
	    for (final Type superType : supers)
		if ((func = superType.getFunc(id, parameters)) != null) return func;
	    return null;
	}

	@Override
	public String toString() {
	    return "Type [type=" + type + /*
	     * ", functions=" + functions +
	     * ", fields=" + fields + ", supers="
	     * + supers +
	     */", qualifiedName=" + qualifiedName + "]";
	}

	public LinkedList<TypeI> getGenerics(final String string) {
	    if (genericsMap.containsKey(string)) return genericsMap.get(string);
	    else for (final Type type : supers)
		if (type.qualifiedName.shortName.equals(string)) {
		    final LinkedList<TypeI> genericList = new LinkedList<TypeI>();
		    for (int i = 0; i < type.generics.size(); i++)
			genericList.add(TypeI.getObjectType());
		    return genericList;
		}
	    return null;
	}

    }

    public static class Function extends Member {

	public LinkedList<TypeI> parameters = new LinkedList<TypeI>();
	public boolean hasDefExpr = false;
	public TypeI returnType;
	public LinkedList<String> generics = new LinkedList<String>();

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
	    // If the function has a default parameter expression and the size
	    // of parmas2 is 1 less than params then allow it
	    if ((parameters.size() != params2.size()) && !(hasDefExpr && (parameters.size() == (params2.size() + 1)))) return false;
	    final int len = Math.min(parameters.size(), params2.size());
	    for (int i = 0; i < len; i++) {
		// If it is a generic, continue
		if (generics.contains(parameters.get(i).shortName)) continue;
		if (!parameters.get(i).canBeAssignedTo(params2.get(i))) return false;
	    }
	    return true;
	}

	@Override
	public String toString() {
	    return "Function [parameters=" + parameters + ", returnType=" + returnType + ", qualifiedName=" + qualifiedName + ", modifiers=" + modifiers + "]";
	}

    }

    public static class Variable extends Field {

	public String id;

	public Variable(final String id, final TypeI type) {
	    super(new QualifiedName(id), 0, type);
	    this.id = id;
	    this.isLocal = true;
	}

	@Override
	public String toString() {
	    return "Variable [id=" + id + ", type=" + type + "]";
	}
    }

    public static class Field extends Member {

	public TypeI type;
	
	public Field(final QualifiedName qualifiedName, final int modifiers, final TypeI type) {
	    super(qualifiedName, modifiers);
	    this.type = type;
	    this.enclosingType = Semantics.currentType();
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
