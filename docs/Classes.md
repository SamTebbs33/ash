# Classes
A class is a type that aggregates fields (state) and functions (behaviour) into one structure. A class declaration is formed by zero or more modifiers, the `class` keyword and the name of the class.

Within braces, you can declare fields and functions inside the class.

```
public class Person {
	var name : String = ""
	var age : int
}
```

The above code declares a class with the `name` and `age` fields.

## Constructors
A class constructor can be created by declaring a function whose name is the same as the class.

```
class Person {
	var name : String = ""
	var age : int
	
	func Person(nameArg : String, ageArg : int) {
		name = nameArg
		age = ageArg
	}
}
```

The constructor is then used like this:

```
var person = Person("Sam", 19)
```

Any number of constructors with unique parameters can be created.

## Primary constructors
Declaring fields and then declaring a function that just assigns them to parameters is tedious. Therefore, Ash provides the ability to declare a primary constructor, which both declares the fields and assigns them for you.

The above class example could then be condensed into an equivalent and more concise form:

```
class Person(name : String, age : int) {
}
```

Providing braces here is rather unnecessary, since we haven't defined any functions for the class. For this we can create a data class.

```
class Person(name : String, age : int)
```

Data classes are identical to normal classes, but have the braces omitted.

## Inheritance
A class can inherit from another class (the super-class) and up to 255 interfaces (the super-interfaces). These are collectively called the class' super-types. These super-types are separated by commas after a colon (`:`), which suffixes the class' name or primary constructor.

```
class Student(name : String, age : int, year : int) : Person, Comparable
```
The `Student` class inherits from (extends) the `Person` class and inherits from (implements) the `Comparable` interface. If the super-class has at least one constructor with at least one parameter, it must be called by the primary constructor.

```
class Student(name : String, age : int, year : int) : Person(name, age), Comparable
```

The `Student` class' primary constructor calls the primary constructor of the `Person` class. Note that the super-class must come first in the list of super-types.

Every constructor in a class must at some point call a constructor of the super-class. This can be done either via calls to other constructors in the class, or a direct call to a super-class constructor.

The `this` keyword is used to refer to another constructor in the same class, and the `super` keywords is used to refer to a constructor in the super-class.

```
class Student(name : String, age : int, year : int) : Person(name, age) {
	func Student(name : String, age : int) {
		this(name, age, 10) 
		// Calls Student(name, age, year), which in turn calls Person(name, age)
	}
}
```

If a class does not provide a super-class, it is inferred as extending the `java.lang.Object` class. For classes that do extend `java.lang.Object`, a direct call to `Object`'s constructor is not required and is added by the compiler.