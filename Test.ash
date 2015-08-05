public class Test {
	public static func main(args : [String]){
		var person = Person(19)
		var person2 = person + 1
	}
}

class Person(age : int) {
	public func +(operand : int) : Person -> Person(age + operand)
}