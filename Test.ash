class AshObject {
	public func println(obj : Object) {
		System.out.println(obj)
	}
}

class Test : AshObject {
	func test() {
		var x = 42
		match x {
			0 -> println("zero")
			1..<10 -> println(">= 1 and < 10")
			_ -> println(">= 10")
		}
	}
}