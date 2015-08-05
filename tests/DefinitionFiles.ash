include DefFileTest

public class DefinitionFiles {
	public static func main(args : [String]) {
		foo() // Test global functions
		var str = "12345"
		var a : int = str.toInt() // Test extension functions
		var b = a +- 1 // Test custom operators
	}
}