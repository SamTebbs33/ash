import Test3

class Test {
	static var test = Test2()
	public static func main(args : String[]) {
		test.bar()
		Test2.foo()
	}
}