# Lists and maps
Lists and maps are dynamic colelctions, which means that elements can be added and removed as needed, unlike the fixed length arrays.

## Lists
Lists can be created using a list expression.
```
// A list of `String`s
var list = {"hi", "hello", "good day"}
```
A list expression returns an instance of `java.util.LinkedList`.

Once generics have been implemented, the list's elements will be accessible with array syntax.
```
list[1] // "hello"
```

## Maps
Maps store data in key-value pairs and can be created with a map expression.
```
// A map of `String`s bound to `int`s
var map = {"Sam" : 188, "Alex" : 192, "Maria" : 183}
```
A map expression returns an instance of `java.util.HashMap`.

Once generics have been implemented, the map's elements will be accessible with array syntax using a valid key.
```
map["Alex"] // 192
```
