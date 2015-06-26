package ashc.semantics;

import java.util.*;

import ashc.semantics.Member.Variable;
import ashc.semantics.Semantics.TypeI;

/**
 * Ash
 * @author samtebbs, 15:08:08 - 23 May 2015
 */
public class Scope {

    public static Stack<Scope> scopeStack = new Stack<Scope>();

    public static class FuncScope extends Scope {

	public TypeI returnType;

	public FuncScope(final TypeI typeI) {
	    returnType = typeI;
	}

    }

    public static class PropertyScope extends FuncScope {

	public PropertyScope(final TypeI varType) {
	    super(varType);
	}
    }

    private static QualifiedName namespace = new QualifiedName("");

    public Scope() {}

    public LinkedList<Variable> vars = new LinkedList<Variable>();
    public Scope parent;

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

}
