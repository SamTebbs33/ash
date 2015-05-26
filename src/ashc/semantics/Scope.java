package ashc.semantics;

import static ashc.main.AshCompiler.*;

/**
 * Ash
 * @author samtebbs, 15:08:08 - 23 May 2015
 */
public class Scope {

    public QualifiedName namespace;

    public Scope(final QualifiedName namespace) {
	this.namespace = namespace;
    }

    public static Scope getScope() {
	return get().scopeStack.peek();
    }

    public static QualifiedName getNamespace() {
	return getScope().namespace;
    }

    public static void push(final Scope scope) {
	get().scopeStack.push(scope);
    }

}
