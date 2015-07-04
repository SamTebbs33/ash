class Person(age : int) {
	func *(operand : int) -> Person(age * operand)
	func +(person : Person) -> Person(age + person.age)
	
	var person = Person(0)
	var person2 = Person(0)
	var person3 = person + person2 * 1
}