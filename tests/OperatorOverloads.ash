class Person(age : int, name : String) {
	func +(toAdd : int) -> Person(age + toAdd, name)
	
	var person = Person(0, "sam")
	var person2 = person + 1
}