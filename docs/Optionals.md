# Optionals
An optional type is a type whose value could possibly be null. The value of a non-optional variable can never be null.

An optional type is denoted by the type (exlcuding primitives), followed by a question mark.

```
var str : String? = ""
```

A variable of a non-optional type cannot be assigned to an optional value, as the optional variable could possibly be null, which breaks the safety provided by optional types. However a variable of an optional type can be assigned to a non-optional value.

```
var str : String? = ""
var str2 : String = null // Illegal, as the variable's type is not optional
var str3 : String = str // Illegal
str = str3 // Legal
```

## Unwrapping
The only way to convert an optional value to a non-optional is to unwrap it using the `!` operator. If the unwrapped value is null, a `NullObjectException` exception is thrown, else the value is returned.

```
var nullObj = null // Inferred as Object?
var obj = nullObj! // Would fail, as nullObj is null
nullObj = Object() // Re-assign it to a non-null value
obj = nullObj! // Wouldn't fail, as nullObj is no longer null
```