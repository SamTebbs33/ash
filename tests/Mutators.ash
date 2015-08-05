import java.util.LinkedList

public class Mutators {

	public static func main(args : [String]?) {
		var list = ListHolder()
		list.add("Sam").add("Tebbs")
	}
	
}

class ListHolder {

	public var list = LinkedList()
	
	public mut add(obj : Object) {
		list.add(obj)
	}
	
}