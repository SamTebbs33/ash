import java.util.LinkedList, HashMap, ArrayList

class Foo(a : int)

public class Test(b : int) : Foo(b) {
	public static func main(args : String[]) {
		var test = Test(10)
	}
}