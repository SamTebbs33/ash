package ashc.semantics;

import static ashc.main.AshCompiler.get;

/**
 * Ash
 * @author samtebbs, 15:08:08 - 23 May 2015
 */
public class Scope {
    
    public QualifiedName namespace;

    public Scope(QualifiedName namespace) {
	this.namespace = namespace;
    }
    
    public static Scope getScope(){
	return get().scopeStack.peek();
    }
    
    public static QualifiedName getNamespace(){
	return getScope().namespace;
    }

}
