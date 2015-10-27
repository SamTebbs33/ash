public class Test {

    public static func main(args : [String]) {

        var square = func (x : int) -> int = x * x
        System.out.println(square(4))

        var print = func (str : String) = System.out.println(str)
        print("hello")

        var sayHi = func () = System.out.println("hello")
        sayHi()

        var complexFunc = func (x : int, y : int) -> int {
            if(x == y) return 0
            else if(x < y) return -1
            else return 1
        }
        System.out.println(complexFunc(1, 2))

    }

}