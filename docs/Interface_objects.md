# Interface objects
Note that it is reccommended that you read the page about [function objects](Function_objects.md) before reading this.

Preferably, function objects would be used when a function is to be passed around. However, Java does not support this and instead uses lambda expressions that return instances of interfaces. In order to promote interoperability, Ash has a similar feature called interface objects.
Interface objects are created with the same syntax as function objects, but with the `func` keyword replaced with `interface`.
```
var comp = interface (a : int, b : int) -> int {
  if (a < b) return -1
  else if (a == b) return 0
  else return 1
}
```
The Ash compiler will then search through all imported functional interfaces and will try to find an interface with a method that matches the signature. In this case, it may find the `Comparator` interface and would return an intance of `Comparator`.
The `comp` object could then be used just like any other instance of `Comparator`, e.g by calling the `compare` method.
```
var res = comp.compare(0, 1) // -1
```
Since the compiler will use the first interface it finds with a method that matches the signature, the desired type may not always be inferred. Therefore you can specify the type of the object with the normal variable declaration syntax. Casting can also be used to explicitly specify the interface to use.
```
// This explicitly says that the interface object must be of type `Comparator`
var comp : Comparator = interface (a : int, b : int) -> int { ... }
var comp2 = (interface (a : int, b : int) -> int { ... }) as Comparator
```
