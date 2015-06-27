import java.util.LinkedList

class Test {
	var string = ""
	mut foo(val : String) {
		string = string.append(val)
	}
	var a = foo("Hi").foo(" ").foo("there!")
}