package ashc.semantics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * Created by samtebbs on 05/03/2016.
 */
public class Parameters implements Iterable<Parameter> {

    public LinkedList<Parameter> types;
    public boolean hasDefExpr;
    private int size = 0;

    public Parameters() {
        types = new LinkedList<>();
    }

    public void add(Parameter type) {
        types.add(type);
        size++;
    }

    public void add(TypeI type) {
        add(new Parameter("arg" + size, type, 0));
    }

    public String toBytecodeName() {
        final StringBuffer sb = new StringBuffer();
        types.forEach(param -> sb.append(param.type.toBytecodeName()));
        return sb.toString();
    }

    public int size() {
        return size;
    }

    public List<TypeI> getTypeIList() {
        LinkedList<TypeI> list = new LinkedList<>();
        forEach(param -> list.add(param.type));
        return list;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Parameters)) return false;
        Parameters params2 = (Parameters) obj;
        if ((size == 0) && (params2.size() == 0)) return true;
        // If the function has a default parameter expression and the size
        // of parmas2 is 1 less than params then allow it
        if (size() != params2.size() && !(hasDefExpr && (size() == (params2.size() + 1))))
            return false;
        final int len = Math.min(size(), params2.size());
        for (int i = 0; i < len; i++) {
            // If it is a generic, continue
            if (!get(i).type.canBeAssignedTo(params2.get(i).type)) return false;
        }
        return true;
    }

    public Parameter get(int i) {
        return types.get(i);
    }

    public boolean isEmpty() {
        return types.isEmpty();
    }

    @Override
    public Iterator<Parameter> iterator() {
        return new Iterator<Parameter>() {

            int i;

            @Override
            public boolean hasNext() {
                return i < size() - 1;
            }

            @Override
            public Parameter next() {
                if (hasNext()) return types.get(i++);
                else throw new NoSuchElementException();
            }
        };
    }

    @Override
    public void forEach(Consumer<? super Parameter> action) {
        for (Parameter param : this) action.accept(param);
    }

    public void clear() {
        types.clear();
    }

    public void addFirst(Parameter typeI) {
        types.addFirst(typeI);
    }

    public void addFirst(TypeI type) {
        addFirst(new Parameter("arg" + size, type, 0));
    }
}
