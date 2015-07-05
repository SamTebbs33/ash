class AshObject {
	public func println(obj : Object) {
		System.out.println(obj)
	}
}

class Test : AshObject {
	func test() {
		var range = 0..<10
		for(a in range) println("iteration $a")
	}
}