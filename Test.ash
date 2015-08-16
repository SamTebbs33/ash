include Test

public class Test {
	public static func main(args : [String]) {
		var a = [2]
		match a {
			[0], [1] -> println("a")
			[2], [3] -> println("b")
			_ -> println("else")
		}
	}
}