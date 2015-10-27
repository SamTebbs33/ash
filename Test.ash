include stdlib.Lang

public class Test {
	public static func main(args : [String]) {
		var f = func (str : String) = System.out.println(str)
		var f2 = func (x : int, y : int) -> int = x + y
		f("hello")
		System.out.println(f2(1, 2))
	}
}