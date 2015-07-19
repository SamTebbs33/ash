package ashc.util;

/**
 * Ash
 *
 * @author samtebbs, 16:32:09 - 24 May 2015
 */
public class BitOp {

    public static boolean and(final int a, final int b) {
	return (a & b) != 0;
    }
    
    public static boolean or(final int a, final int b) {
	return (a | b) != 0;
    }
    
    public static boolean xor(final int a, final int b) {
	return (a ^ b) != 0;
    }

}
