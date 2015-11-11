import java.util.Comparator
import TestInterface

public class LambdaExpressions {

    public static func main(args : [String]) {

        var comp = interface (a : int, b : int) -> bool = true
        System.out.println(comp.test(1, 2))

    }

}