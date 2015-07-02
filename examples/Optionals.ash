class Optionals {
  func test() -> null // The return type is inferred as Object?
  var optional = test() // The varuable type is inferred as Object?
  var notNull = optional ?? Object() // If optional is null, notNull is assigned to a new Object instance, else it is assigned to notNull
  var unwrappedOptional = optional! // The optional variable is unwrapped, which returns the value it stores. The type is inferred as Object
}
