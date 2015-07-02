class Person(name : String, age : int) // Data class

class Test {
  public func makePerson(name : String, age : int = 0) -> Person(name, age) // A single-line function with a default parameter value
  var person = makePerson("Sarah")
  var person2 = makePerson("Sam", 19)
}
