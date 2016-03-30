package ash.semantics;

import java.util.Optional;

/**
 * Created by samtebbs on 16/03/2016.
 */
public class Operator {

    String op, name;
    OperatorType type;

    public static Optional<TypeInstance> getOperationResult(TypeInstance expr1Type, TypeInstance expr2Type, String op) {
        // TODO
        return Optional.empty();
    }

    public static Optional<TypeInstance> getOperationResult(TypeInstance exprType, String op) {
        // TODO
        return Optional.empty();
    }
}
