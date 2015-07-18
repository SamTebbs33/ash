package ashc.semantics;

import java.lang.reflect.*;
import java.util.*;

import ashc.grammar.*;
import ashc.grammar.Node.IExpression;
import ashc.grammar.Node.NodeExprs;
import ashc.semantics.Scope.FuncScope;
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

    public boolean isVisible() {
	if (isLocal) return true;
	// Check if the member was declared as public, or no modifiers were
	// given (Members are implicitly public)
	if (BitOp.and(modifiers, EnumModifier.PUBLIC.intVal) || (modifiers == 0)) return true;
	// If the current type is the same type as the member's enclosing type,
	// the member is visible
	if ((enclosingType != null) && enclosingType.equals(Semantics.currentType())) return true;
	// If the member is private, it isn't visible
	if (BitOp.and(modifiers, EnumModifier.PRIVATE.intVal)) return false;
	// If the current type is a sub-type of the member's enclosing type, it
	// is visible
	if (Semantics.currentType().hasSuper(enclosingType.qualifiedName)) return true;
	return false;
    }
    
    public boolean isStatic() {
	return BitOp.and(modifiers, EnumModifier.STATIC.intVal);
    }
    
    public boolean isPrivate() {
	return BitOp.and(modifiers, EnumModifier.PRIVATE.intVal);
    }

    public static enum EnumType {
	CLASS, ENUM, INTERFACE
    }

    public static class Type extends Member {
	public EnumType type;
	public HashMap<String, LinkedList<Function>> functions = new HashMap<String, LinkedList<Function>>();
	public LinkedList<Field> fields = new LinkedList<Field>();
	public LinkedList<Type> supers = new LinkedList<Member.Type>();
	public LinkedList<String> generics = new LinkedList<String>();
	public HashMap<String, LinkedList<TypeI>> genericsMap = new HashMap<String, LinkedList<TypeI>>();

	public Type(final QualifiedName qualifiedName, final int modifiers, final EnumType type) {
	    super(qualifiedName, modifiers);
	    this.type = type;
	}

	public Type(final Class<?> cls, final String path) {
	    super(QualifiedName.fromPath(path), cls.getModifiers());
	    type = cls.isEnum() ? EnumType.ENUM : cls.isInterface() ? EnumType.INTERFACE : EnumType.CLASS;
	    for (final TypeVariable genericType : cls.getTypeParameters())
		generics.add(genericType.getName());
	    if (cls.getSuperclass() != null) {
		final Type superType = new Type(cls.getSuperclass(), cls.getSuperclass().getName());
		supers.add(superType);
	    }
	    for(Class i : cls.getInterfaces()) supers.add(new Type(i, i.getName()));
	    
	    Semantics.addType(this);
	    for (final Method method : cls.getMethods())
		addFunction(Function.fromExecutable(method));
	    for (final Constructor constructor : cls.getConstructors())
		addFunction(Function.fromExecutable(constructor));
	    for (final java.lang.reflect.Field field : cls.getFields())
		fields.add(ashc.semantics.Member.Field.from(field));
	    Semantics.exitType();
	}

	public void addFunction(final Function func) {
	    final String name = func.qualifiedName.shortName;
	    if (functions.containsKey(name)) functions.get(name).add(func);
	    else {
		final LinkedList<Function> funcs = new LinkedList<Function>();
		funcs.add(func);
		functions.put(name, funcs);
	    }
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
	    for (final Type superType : supers) {
		final Field field = superType.getField(id);
		if (field != null) return field;
	    }
	    return null;
	}

	public TypeI getFuncType(final String id, final NodeExprs args) {
	    final LinkedList<TypeI> parameters = new LinkedList<TypeI>();
	    for (final IExpression arg : args.exprs)
		parameters.add(arg.getExprType());
	    if (functions.containsKey(id)) for (final Function func : functions.get(id))
		if (func.paramsAreEqual(parameters)) return func.returnType;
	    return null;
	}

	public boolean hasSuper(final QualifiedName name) {
	    for (final Type type : supers){
		if (type.qualifiedName.equals(name)) return true;
		else if (type.hasSuper(name)) return true;
	    }
	    return false;
	}

	public Function getFunc(final String id, final NodeExprs args) {
	    final LinkedList<TypeI> parameters = new LinkedList<TypeI>();
	    for (final IExpression arg : args.exprs)
		parameters.add(arg.getExprType());
	    return getFunc(id, parameters);
	}

	public Function getFunc(final String id, final LinkedList<TypeI> parameters) {
	    if (functions.containsKey(id)) for (final Function func : functions.get(id))
		if (func.paramsAreEqual(parameters)) return func;

	    Function func = null;
	    for (final Type superType : supers)
		if ((func = superType.getFunc(id, parameters)) != null) return func;
	    return null;
	}

	@Override
	public String toString() {
	    return "Type [type=" + type + ", qualifiedName=" + qualifiedName + "]";
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

	public Type getSuperClass() {
	    return supers.getFirst();
	}

    }

    public static class Function extends Member {

	public LinkedList<TypeI> parameters = new LinkedList<TypeI>();
	public boolean hasDefExpr = false;
	public TypeI returnType;
	public LinkedList<String> generics = new LinkedList<String>();

	public Function(final QualifiedName qualifiedName, final int modifiers) {
	    super(qualifiedName, modifiers);
	    enclosingType = Semantics.currentType();
	}

	public static Function fromExecutable(final Executable method) {
	    final QualifiedName name = QualifiedName.fromClass(method.getDeclaringClass());
	    name.add(method.getName().substring(method.getName().lastIndexOf('.') + 1));

	    final Function func = new Function(name, method.getModifiers());

	    final Parameter[] params = method.getParameters();
	    for (final Parameter param : params)
		func.parameters.add(TypeI.fromClass(param.getType()));
	    // If it's a method, get the return type, otherwise it's a
	    // constructor
	    if (method instanceof Method){
		func.returnType = TypeI.fromClass(((Method) method).getReturnType());
	    }
	    else func.returnType = new TypeI(Semantics.currentType().qualifiedName.shortName, 0, false);
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

	public boolean isConstructor() {
	    return qualifiedName.shortName.equals(enclosingType.qualifiedName.shortName);
	}

    }

    public static class Variable extends Field {

	public Variable(final String id, final TypeI type) {
	    super(new QualifiedName(id), 0, type, false, false);
	    if(Scope.inFuncScope()){
		isLocal = true;
		this.localID = ++Scope.getFuncScope().locals;
		if(Scope.getFuncScope().isStatic) this.localID--; // Static funcs don't have a "this" instance, so decrease the varID by one
	    }else isLocal = false;
	}

	@Override
	public String toString() {
	    return "Variable [id=" + id + ", type=" + type + "]";
	}
    }

    public static class Field extends Member {

	public TypeI type;
	public String id;
	public int localID;
	public boolean isSetProperty, isGetProperty;

	public Field(final QualifiedName qualifiedName, final int modifiers, final TypeI type, boolean isSetProperty, boolean isGetProperty) {
	    super(qualifiedName, modifiers);
	    this.id = qualifiedName.shortName;
	    this.type = type;
	    enclosingType = Semantics.currentType();
	    this.isGetProperty = isGetProperty;
	    this.isSetProperty = isSetProperty;
	}

	public static Field from(final java.lang.reflect.Field field) {
	    final int mods = field.getModifiers();
	    final TypeI type = TypeI.fromClass(field.getType());
	    final QualifiedName name = QualifiedName.fromClass(field.getDeclaringClass());
	    name.add(field.getName());
	    return new Field(name, mods, type, false, false);
	}

	@Override
	public boolean equals(final Object obj) {
	    if (obj instanceof Field) return ((Field) obj).qualifiedName.equals(qualifiedName);
	    return false;
	}

    }

}
