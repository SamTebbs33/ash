class Person(name : String, age : int) // Data class

class Test {
  public func makePerson(name : String, age : int = 0) -> Person(name, age) // A single-line function with a default parameter value. Calls the default constructor of Person
  
  var person = makePerson("Sarah")
  var personName = person.name
  var personAge = person.age
  
  var person2 = makePerson("Sam", 19)
  var person2Name = person2.name
  var person2Age = person2.age
}
