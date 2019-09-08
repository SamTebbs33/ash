# Operator overloads
A previously-defined operator can be overload using a function. To denote an operator overload, you follow the same structure as a normal function, but you prefix the `func` keyword with the type of operator you are overloading (see the [operators](Operators.md) page) and replace the function's name with the operator itself. One parameter would be needed for a binary operator and zero would be needed for a postfix or prefix operator.

Below is an example that overloads the `+` operator for use with a `Person` object and an integer. It returns a new `Person` object, with the integer added to the `age` variable.

```
class Person(name : String, age : int) {
	binary func +(operand : int) -> Person = new Person(age + operand)
}
```

It would be used like so:

```
var person = new Person("Sam", 36)
var person2 = person + 10 // 'person2' has an age of 46
```

A postfix or prefix operator overload would be defined in a similar way, but wouldn't use any parameters.

```
class Person(name : String, age : int) {
	postfix func ++() -> Person = return new Person(age + 1)
}
var person = new Person("Sam", 36)
var person2 = person++ // 'person2' has an age of 37
```
