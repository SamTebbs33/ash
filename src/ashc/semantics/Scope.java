package ashc.semantics;

import static ashc.main.AshCompiler.*;
import ashc.semantics.Semantics.TypeI;

/**
 * Ash
 * @author samtebbs, 15:08:08 - 23 May 2015
 */
public class Scope {
    
    public static class FuncScope extends Scope {
	
	public TypeI returnType;

	public FuncScope(TypeI typeI) {
	    returnType = typeI;   
	}
	
    }

    public static QualifiedName namespace;

    public Scope() {
    }

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

}
