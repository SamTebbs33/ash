public class Test {

    public static func main(args : [String]) {

        var matcher = func (a : int) -> String = match a {
            -1 -> "minus one"
            0 -> "zero"
            1 -> "one"
            2 -> "two"
            _ -> "other"
        }

        System.out.println(matcher(-1))

    }

}