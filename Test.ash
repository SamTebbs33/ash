public class Test {

    public static func main(args : [String]) {

        test(func (x : int, y : int) -> int {
                if(x == y) return 0
                else if(x < y) return -1
                else return 1
            }
        )
    }

    public func test(f : func (int, int) -> int) {
        System.out.println(f(1, 2))
    }

}