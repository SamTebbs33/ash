import java.util.Comparator

public class Test {

    public static func main(args : [String]) {

        System.out.println(foo(2))
        
    }

    static func foo(i : int) -> String
        = match i {
            0 -> "zero"
            1 -> "one"
            2 -> "two"
            3 -> "three"
            _ -> "other"
        }
}