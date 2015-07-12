package ashc.semantics;

import java.util.*;

import ashc.semantics.Member.Field;
import ashc.semantics.Member.Type;
import ashc.semantics.Member.Variable;
import ashc.semantics.Semantics.TypeI;

/**
 * Ash
 *
 * @author samtebbs, 15:08:08 - 23 May 2015
 */
public class Scope {

    public static Stack<Scope> scopeStack = new Stack<Scope>();

    public static class FuncScope extends Scope {

	public TypeI returnType;
	public boolean isMutFunc;
	public int locals = 0;

	public FuncScope(final TypeI typeI, final boolean isMutFunc) {
	    super();
	    returnType = typeI;
	    this.isMutFunc = isMutFunc;
	}

    }

    public static class PropertyScope extends FuncScope {
	
	public Field field;

	public PropertyScope(final Field field) {
	    super(field.type, false);
	    this.field = field;
	}
    }

    private static QualifiedName namespace = new QualifiedName("");

    public Scope() {
	if (!scopeStack.isEmpty()) parent = scopeStack.peek();
    }

    public LinkedList<Variable> vars = new LinkedList<Variable>();
    public Scope parent;
    public HashSet<Field> nullChecks = new HashSet<Field>();
    public HashMap<Field, Type> castChecks = new HashMap<Field, Type>();

    public static Scope getScope() {
	return scopeStack.size() > 0 ? scopeStack.peek() : null;
    }

    public static QualifiedName getNamespace() {
	return namespace;
    }

    public static void setNamespace(final QualifiedName namespc) {
	if (namespc != null) namespace = namespc;
    }

    public static void push(final Scope scope) {
	if (scopeStack.size() > 0) scope.parent = scopeStack.peek();
	scopeStack.push(scope);
    }

    public static void pop() {
	scopeStack.pop();
    }

    public boolean hasVar(final String id) {
	for (final Variable var : vars)
	    if (var.id.equals(id)) return true;
	if (parent != null) return parent.hasVar(id);
	return false;
    }

    public void addVar(final Variable var) {
	vars.add(var);
    }

    public boolean hasNullCheck(final Field field) {
	if (nullChecks.contains(field)) return true;
	else if (parent != null) return parent.hasNullCheck(field);
	return false;

    }

    public static FuncScope getFuncScope() {
	for (int i = scopeStack.size() - 1; i >= 0; i--)
	    if (scopeStack.get(i) instanceof FuncScope) return (FuncScope) scopeStack.get(i);
	return null;
    }

    public static PropertyScope getPropertyScope() {
	for (int i = scopeStack.size() - 1; i >= 0; i--)
	    if (scopeStack.get(i) instanceof PropertyScope) return (PropertyScope) scopeStack.get(i);
	return null;
    }

    public static boolean inScope() {
	return scopeStack.size() > 0;
    }

    public boolean hasCastCheck(final Field field, final Type type) {
	return castChecks.containsKey(field) ? castChecks.get(field).equals(type) : parent != null ? parent.hasCastCheck(field, type) : false;
    }

    public static boolean inFuncScope() {
	return getFuncScope() != null;
    }

    public static boolean inPropertyScope() {
	return getPropertyScope() != null;
    }

}
