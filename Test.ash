class Person(age : int) {
	mut +(toAdd : int) -> age += toAdd
	var person = Person(0)
	var person2 = Person(0)
	var person3 = person + 1
}