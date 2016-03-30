package ash.semantics;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by samtebbs on 04/04/2016.
 */
public class Parameters {

    public static class Parameter {
        public final TypeInstance type;
        public final String name;
        public final boolean hasDefaultValue;

        public Parameter(TypeInstance type, String name, boolean hasDefaultValue) {
            this.type = type;
            this.name = name;
            this.hasDefaultValue = hasDefaultValue;
        }
    }

    public final List<Parameter> params = new LinkedList<>();

}
