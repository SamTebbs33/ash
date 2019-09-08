# Extension functions
Extension functions are used to add functions to a a pre-existing type. Note that extension functions can only be declared in [definition files](Definition_files.md).

An extension function is declared just like a normal function, but the function's name is prefixed with a class/interface/enum name and a dot (`.`). The `this` keyword refers to the object that the function is called from.

```
func String.toInt() -> int = Integer.parseInt(this)
```

The above function would convert a `String` to an `int`, and could be called from any `String` object.

```
var str = "12345"
var x = str.toInt()
```

Note that primitives cannot have extension functions applied to them.
