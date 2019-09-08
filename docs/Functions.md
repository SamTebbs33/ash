# Functions
A function (func) is a piece of code that can be run several times from various places, without needing to copy and paste code over and over again.

A basic function declaration is denoted by a set of [modifiers](Modifiers.md), the `func` keyword, a function name, a list of parameters enclosed by parentheses, and an optional return type (prefixed by a lambda arrow).

Below are some examples of different parameter-return type combinations.

```
// Func with no parameters and no return type
public func sayHello() {

}

// Func with one parameter and no return type
public func sayHello(name : String) {

}

// Func with two parameters and no return type
public func sayHello(name : String, age : int) {

}

// Func with no parameters and a return type of 'String'
public func sayHello2() -> String {

}
```

The function's parameters must be filled in with expressions when you call the function. The function signature is made up of the modifiers, the name, the parameters and the return type.

The code that is to be executed when the function is called is enclosed by braces after the function signature.

```
// This function will say hello!
public func sayHello(name : String) {
	println("Hello " + name)
}
```

## Single-line functions
If you only want to run one statement in your function, you can use a single-line function instead. You do this by replacing the first brace with an equals sign (`=`) and then a single statement.

```
public func sayHello(name : String) = println("Hello " + name)
```

Note that if you provide a return type to the function, you must instead use an expression after the equals sign. The function will then return this expression when you call the function.

```
func getMyName() -> String = "Alexander"
```

The above function would return the String "Alexander".

If you want to be able to throw an exception from a function, you must add the arrow (``=>``) after the return type (if there is one) and then add the class name of the exception.

```
// A function that can throw a NoSuchElementException
func doThings() => NoSuchElementException {
	// code, blah, blah
}
```
