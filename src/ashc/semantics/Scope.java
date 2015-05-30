package ashc.semantics;

import static ashc.main.AshCompiler.*;

import java.util.*;

import ashc.semantics.Member.Variable;
import ashc.semantics.Semantics.TypeI;

/**
 * Ash
 * @author samtebbs, 15:08:08 - 23 May 2015
 */
public class Scope {

    public static class FuncScope extends Scope {

	public TypeI returnType;

	public FuncScope(final TypeI typeI) {
	    returnType = typeI;
	}

    }

    public static QualifiedName namespace;

    public Scope() {}
    
    public LinkedList<Variable> vars = new LinkedList<Variable>();

    public static Scope getScope() {
	return get().scopeStack.peek();
    }

    public static QualifiedName getNamespace() {
	return namespace;
    }

    public static void push(final Scope scope) {
	get().scopeStack.push(scope);
    }

    public static void pop() {
	get().scopeStack.pop();
    }

    public boolean hasVar(String id) {
	for(Variable var : vars) if(var.id.equals(id)) return true;
	return false;
    }
    
    public void addVar(Variable var){
	vars.add(var);
    }

}
