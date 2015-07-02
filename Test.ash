//import java.lang.Iterable

class Person(name : String, age : int)

class Test : Person {

	func makePerson(name : String, age : int = 0) -> Person(name, age)
	var obj = makePerson("Sam")!
	
}