package ash.error;

/**
 * Created by samtebbs on 19/03/2016.
 */
public enum Error {

    UNDEFINED_BIN_OP_BEHAVIOUR("The behaviour for %s with arguments %s and %s is undefined"),
    EXPECTED_OTHER_TYPE("Expected expression of type %s but got %s"),
    UNDEFINED_UNARY_OP_BEHAVIOUR("The behaviour for %s with argument %s is undefined"),
    CONST_VAR_REQUIRES_EXPRESSION("The constant variable declaration for \'%s\' must have an assignment"),
    TYPE_CANNOT_BE_CONVERTED("Type %s cannot be converted to %s");

    public final String format;

    Error(String format) {
        this.format = format;
    }
}
