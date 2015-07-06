class AshObject {
	public func println(obj : Object) {
		System.out.println(obj)
	}
}

class Test : AshObject {
	func test() {
		var x = 0
		match x {
			0 {}
			1, 2, 3 {}
			4..<10 {}
			_ {}
		}
	}
}