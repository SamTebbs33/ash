# Function Objects
Function objects are objects that can be passed around just like any other object and act as an expression. To create one, use the keyword `func`, a list of parameters, an optional return type and then a normal function body.
```
func (a : int, b : int) -> int = a + b
```
This function takes two integers and returns their sum. You could then assign this function object to a variable, and call it just like any other function.
```
var add = func (a : int, b : int) -> int = a + b
add(1, 2) // 3
```
If you wanted to pass this function object to a function as a parameter, you'd specify the function's type.
```
func doMaths(f : func (int, int) -> int, a : int, b : int) -> int = f(a, b)
```
The above function takes a function object and two integers as the parameters, and applies the function to the integers.
It would be called like so.
```
doMaths(add, 3, 7) // 10
```
