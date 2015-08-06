public class Foo {
	func foo() {}
	private func bar() {}
	func foo2(obj : Object) : String? -> null
}

class Bar : Foo {
	override public func foo() {}
	override func bar() {}
	override func foo2(obj : Object){}
}