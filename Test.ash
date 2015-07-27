include Test

class Test {
	func &/(operand : int) : int -> operand
}

class Test2 {
	public static func main(args : String[]) {
		var test = Test()
		var a = test &/ 10
	}
}