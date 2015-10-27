include stdlib.Lang

public class Test {
	public static func main(args : [String]) {
		var f = func (str : String) = System.out.println(str)
		f("hello")
	}
}