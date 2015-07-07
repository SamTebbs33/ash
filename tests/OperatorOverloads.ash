class Person(age : int) {
	func +(toAdd : int) -> Person(age + toAdd)
	
	var person = Person(0)
	var person2 = person + 1
}