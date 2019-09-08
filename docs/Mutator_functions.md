# Mutator functions
Mutator functions are functions that implicitly return the object from which they were called. They use the same structure as a normal function, but the `func` keyword is replaced by the `mut` keyword, and they have no return type. In addition, mutator functions cannot use the `static` modifier and must be declared within a type declaration. These functions are useful for chaining function calls on an object.

```
class ListHolder {
	private var list = LinkedList()
	
	// The return type is inferred as being 'ListHolder'
	mut add(obj : Object) {
		list.add(obj)
		// This object is implicitly returned at the end of the function
	}
	
}
```

The mutator function could then be used in the following way:

```
var listHolder = ListHolder()
listHolder.add("Mutator").add("functions!")
```