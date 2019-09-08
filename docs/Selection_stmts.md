# Selection statements
Selection statements are used to choose a certain code path based on certain conditions.

## Match statement
A match statement is used to match an expression to other expressions, and follow a certain code path if they do indeed match.
```
var x = 2
match x {
  0 -> println("zero")
  1 -> println("one")
  2 -> println("two")
  _ -> println("other")
}
// prints "two"
```
The code after the underscore is executed if no expression has been matched yet, and must be the last match case in a match statement.

Match statements can also be used as expressions.
```
var x = 2
var number = match x {
  0 -> "zero"
  1 -> "one"
  2 -> "two"
  3 -> "three"
  _ -> "other"
}
println(number) // "two"
```
You can match against multiple expressions in one match case.
```
var x = 2
var number = match x {
  0 -> "zero"
  1 -> "one"
  2, 3 -> "two or three"
  _ -> "other"
}
println(number) // "two or three"
```
Note that when using a match statement as an expression, the returned expressions must be compatible. The match case block uses the same syntax as a function block, so you can group multiple statements together by enclosing them with braces.

## If statements
If statements are used to perform more complicated and diverse checks than match statements.
```
if condition {
  // Body
}
```
If the condition (which must be of type `bool`) is true, then the body is executed.
An else statement can then be added to the end of the if statement, whose body is run when the if statement's condition is instead false.
```
if condition {
  // Body
} else {
  // Body 2
}
```
An else statement can be replaced by an else-if statement, which is only run if the previous if or else-if statement's codition was false, and if its own condition is true.
```
if condition {
  // Body
} else if condition2 {
  // Body2
}
```
Note that no further chained if, else-if else statements can occur after an else statement.
