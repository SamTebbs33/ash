public class Test {

    public static func main(args : [String]) {

        var i = 2
        var foo = match i {
            1 -> "one"
            2 -> "two"
            3 -> "three"
            _ -> "other"
        }
        System.out.println(foo)
    }

}