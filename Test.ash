//import java.lang.Iterable

class Person(name : String, age : int){
	func get() : String? -> "hi"
}

class Test : Person {

	func makePerson(name : String, age : int = 0) -> Person(name, age)
	const obj = makePerson("Sam").get()!
	
}