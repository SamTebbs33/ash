class Person(age : int) {
	func +(toAdd : int) -> Person(age + toAdd)
	func ++() -> this + 1
	
	var person = Person(0)
	var person2 = person + 1
	var person3 = person2++
}