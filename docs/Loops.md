# Loops
Loops can be used to repeat pieces of code an arbitrary number of times. All loops can be broken out of using the `break` keyword, and you can jump to the next iteration by using the `continue` keyword.

## For loops
A for loop comes in two forms, and is used when the number of times a loop must occur is known.

### For-in loop
A for-in loop is used to loop over a certain collection of data, and use the `for` keyword, a variable name, the `in` keyword and then the data to loop over (either an array or an object that extends `Iterable`).
```
// Loop over each item in the list
for item in list {
  //body
}

// Loop in the range of 0 to 10
for num in 0 .. 10 {

}
```

### Traditional for loop
A traditional for loop is used to loop a certain number of times, rather than over a collection of data. It uses the `for` keyword, a statement to execute before the loop starts, a looping condition and a statement to execute at the end of each iteration.
```
// Loop 10 times
for var x = 0, x < 10, x++ {
  // body
}
```

## While loop
A while loop is used when the number of times to loop is unknown, and the loop should instead terminate when a certain condition is met.
It uses the `while` keyword followed by the looping condition.
```
// Loop 10 times
var x = 0
while x < 10 {
  // body
}
```

## Break and continue
To break out of the last entered loop, use the `break` keyword, this will resume all code after the end of the loop.
To continue with the next iteration of the loop, use the `continue` keyword.
