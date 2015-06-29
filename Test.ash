//import java.lang.Iterable

class Person(name : String, age : int)

class Test {

	func makePerson(name : String, age : int = 0) -> Person(name, age)
	var person = makePerson("Sam")
	
}