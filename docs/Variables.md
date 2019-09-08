# Variables
Variables are references to data that is stored somewhere in the deep depths of the computer's memory.

Variables can either have constant (using the `const` keyword) or changeable values (using the `var` keyword). A variable can be declared with either an explict or inferred type. Variables that use an inferred type must be given an assignment from which the compiler infers the variable's type. Variables with an explicit type that is not an optional or primitive must also be given an assignment.

```
var name : String = "Sam" // The type of 'name' is String
var name2 = "Sam" // The type of 'name' is inferred as String
```

Variables declared with the `const` keyword cannot be re-assigned.

```
// Speed of light in a vacuum (ms^-1)
const c = 299792458
c = 123 // Illegal! You can't change the speed of light!
```

The type of a variable can be an interface, enum, class or [primitive](Primitives.md).

At the class-declaration level, multiple variable declarations can be chained on the same line.

```
var a = 0, b = "Sam"
```

The example above would declare an `int` with the value 0, and a `String` with the value "Sam". Both variables are non-constant as the `var` keyword was used.

## Properties
Properties are variables that are bundled with a getter and setter. This emulates the common pattern of declaring a private variable along with getter and setter methods. A property can only have one getter and one setter, but both are optional. 

```
var greeting = "Anthony" {
    get = "Hello " + self
    set {
        if (newVal.isEmpty()) return "unnamed"
        else return newVal
    }
}
```

When the variable is being retrieved, the get block is run. When the variable's value is being set, the set block is run and the variable is assigned to the value returned by the block. The `newVal` variable is implicitly created and is used to refer to the value that the variable is being assigned to. The `self` variable is used to refer directly to the value of the property, and bypasses all setters and getters (to prevent potential infinite recursion). The variable is assigned to the value returned by the set block.

```
println(greeting) // "Hello Anthony"
greeting = ""
println(greeting) // "Hello unnamed"
greeting = "Anne"
println(greeting) // "Hello Anne"
```
