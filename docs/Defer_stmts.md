# Defer Statements
Defer statements are used to delay a statement's execution until the end of a block of code.

```
func foo(msg : String) {
  defer println("deferred")
  println(msg)
}
```
The above code will print `msg` and then "deferred". You can also defer a block of code.
```
func foo(msg : String) {
  defer {
    println("1")
    println("2")
  }
  println(msg)
}
```
The above code prints `msg`, then "1" and then "2".
