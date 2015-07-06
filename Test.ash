import java.util.LinkedList
import java.lang.Integer

class AshObject {
	public func println(obj : Object) {
		System.out.println(obj)
	}
}

class ListHolder() {
	public var list = LinkedList()
	
	public func [](index : int) -> list.get(index)
	public func [](index : String) -> list.get(Integer.parseInt(index))
	
}

class Test : AshObject {
	func test() {
		var list = ListHolder()
		var obj = list[0]
		var obj2 = list["1"]
	}
}