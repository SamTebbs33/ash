# Arrays
Arrays are fixed length data structures thatc an hold an arbitrary number of elements of the same type. They can either be created with an array expression or with the `new` keyword.
```
// An int array of length 6 with pre-defined values
var array = {1, 1, 2, 3, 5, 8}
// An int array of length 20 with no pre-defined values
var array2 = new [int, 20]
```
A certain index is then accessed with square brackets.
```
array[4] // 5
array2[2] // 0
```
Arrays can also be multi-dimensional, which act like arrays of arrays.
```
// an array of int arrays with pre-defined values
var array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}
// an array of int arrays, where the first dimension has length 20, and the second 4
var array2 = new [int, 20, 4]

array[1] // {4, 5, 6}
array[1][2] // 6

array2[3] // {0, 0, 0, 0}
array2[3][1] // 0
```
