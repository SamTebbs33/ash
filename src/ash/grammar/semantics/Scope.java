package ash.grammar.semantics;

import java.util.Stack;

/**
 * Created by samtebbs on 19/03/2016.
 */
public class Scope {

    private static final Stack<Scope> scopeStack = new Stack<>();

    public static void push(Scope scope) {
        scopeStack.push(scope);
    }

    public static void pop() {
        scopeStack.pop();
    }

    public static Scope peek() {
        return scopeStack.peek();
    }

}
