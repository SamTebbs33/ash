package ashc.util;

/**
 * Ash
 *
 * @author samtebbs, 09:48:36 - 25 Jul 2015
 */
public class Tuple<T, E> {
    public T a;
    public E b;

    public Tuple(final T a, final E b) {
        this.a = a;
        this.b = b;
    }
}
