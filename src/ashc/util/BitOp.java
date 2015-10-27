package ashc.util;

/**
 * Ash
 *
 * @author samtebbs, 16:32:09 - 24 May 2015
 */
public class BitOp {

    public static final double LOG_2 = Math.log(2);

    public static boolean and(final int a, final int b) {
        return (a & b) != 0;
    }

    public static boolean or(final int a, final int b) {
        return (a | b) != 0;
    }

    public static boolean xor(final int a, final int b) {
        return (a ^ b) != 0;
    }

    public static int clearBit(int modifiers, int bit) {
        return modifiers & ~(1 << bit);
    }

    public static int clearPow2(int modifiers, int product) {
        return modifiers & ~(1 << (int) (Math.log(product) / LOG_2));
    }

}
