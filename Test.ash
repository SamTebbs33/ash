class AshObject {
	public func println(obj : Object) {
		System.out.println(obj)
	}
}

class Test : AshObject {
	func test() {
		var a = 0
		match a {
			1..5 -> println("greeting 1")
			2 {
				println("greeting 2")
			}
			_ -> println("not a greeting")
		}
	}
}