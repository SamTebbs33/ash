class AshObject {
	private func println(obj : Object) {
		System.out.println(obj)
	}
}

class Person(name : String, age : int) : AshObject {
	func main() {
		println("hello")
	}
}