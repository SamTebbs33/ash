package ash.semantics;

import ash.semantics.member.Variable;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by samtebbs on 19/03/2016.
 */
public class Scope {

    private static final Stack<Scope> scopeStack = new Stack<>();
    public final List<Variable> vars = new LinkedList<>();

    public static void push(Scope scope) {
        scopeStack.push(scope);
    }

    public static void pop() {
        scopeStack.pop();
    }

    public static Scope peek() {
        return scopeStack.peek();
    }

    public void addVar(Variable var) {
        vars.add(var);
    }
}
