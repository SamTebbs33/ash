package ash.grammar.semantics.member;

import ash.grammar.semantics.EnumType;
import ash.grammar.semantics.Modifier;
import ash.grammar.semantics.QualifiedName;
import ash.grammar.semantics.TypeInstance;

import java.util.*;

/**
 * Created by samtebbs on 26/03/2016.
 */
public class Type extends Member {

    public final List<Type> superTypes = new LinkedList<>();
    public final List<Variable> variables = new LinkedList<>();
    public final List<Function> functions = new LinkedList<>();
    public final EnumType type;
    public static Optional<QualifiedName> currentPackage = Optional.empty();

    private static final Stack<Type> typeStack = new Stack<>();

    /**
     * Stores all found types
     */
    private static final HashMap<QualifiedName, Type> types = new HashMap<>();

    /**
     * Stores aliases for types. For example, when a type is imported, it can be referenced with the short name as it is added to the aliases map
     */
    private static final HashMap<String, TypeInstance> typeAliases = new HashMap<>();

    public Type(String name, List<Modifier> mods, EnumType type) {
        super(currentPackage.isPresent() ? currentPackage.get().add(name) : new QualifiedName(name), mods);
        this.type = type;
    }

    public static void pushType(Type type) {
        typeStack.push(type);
        types.put(type.name, type);
        typeAliases.put(type.name.getShortName(), TypeInstance.fromType(type));
    }

    public static Optional<Type> popType() {
        return !typeStack.isEmpty() ? Optional.of(typeStack.pop()) : Optional.empty();
    }

    public static Optional<Type> peekType() {
        return !typeStack.isEmpty() ? Optional.of(typeStack.peek()) : Optional.empty();
    }

}
