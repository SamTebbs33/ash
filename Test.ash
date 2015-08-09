public class Test {
	public static func main(args : [String]) {
		var a = A()
		a.foo()
	}
}

class A : B {
	override public func foo() {
		System.out.println("Hi!")
	}
}

interface B {
	public func foo()
}