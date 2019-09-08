# Interfaces
An interface is a type that defines the capabilites of a class or enum, rather than its state.

An interface is declared in much the same way as a class. The exceptions are that it uses the `interface` keyword (rather than the `class` keyword), has no constructors and cannot extend a class.

Normally, the functions inside an interface have no implementation (the class that implements an interface provides its own implementation for the interface's functions). However, a default implementation can be provided, which is used when the implementing class does not provide its own.

```
interface Vehicle {
	func move(x : int, y : int)
	
	func moveManyTimes(times : uint, x : int, y : int) {
		for (var i = 0, i < times, i++) move(x, y)
	}
	
}
```

A default implementation is provided for the `moveManyTimes()` function, but every class that implements the `Vehicle` interface must provide its own implementation of the `move()` method, since no default implementation is defined.

A class would implement the `Vehicle` interface like so:

```
class Car : Vehicle {
	var xPos : int, yPos : int
	override func move(x : int, y : int) {
		xPos += x
		yPos += y
	}
}
```

An implementation of the `moveManyTimes()` method would be optional. Note that interfaces can extend other interfaces, but are not reuquired to provide their own implementations of functions (that is up to the implementing class).