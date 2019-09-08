# Tuples
Tuples are structures that group data of different types together. They are in fact similar to [data classes](Classes.md#data_classes), but have no name.

A tuple type is defined with an opening parenthesis, at least two types separated by commas, and then a closing parenthesis. The types within the tuple can be any sort of type (even other tuples!). Tuples must have more than 1 type in order to not clash with expressions surrounded in parentheses (a tuple with just one type is extremely pointless anyway).

Below is an example of a tuple that stores a String and an integer.

```
(String, int)
```

You can assign a tuple to an expression. These are formed by an opening parenthesis, at least 2 expressions separated by commas, and then a closing parenthesis.

Below is an example of a tuple expression that contains a String and an integer.

```
("Mark", 23)
```

We could create a tuple that simulates the `Person` data class from the [page about classes](Classes.md).

```
var person : (String, int) = ("Mark", 23)
```

Note that the expressions in the tuple expression must have the same types as those used in the type definition, and must be in the same order.

```
var person : (String, int) = (23, "Mark") // Wrong order!
var person2 : (String, int) = (23.0, true) // Wrong types!
```

A tuple expression can of course also be used for type inference, in which case the tuple definition is unnecessary.

```
var person = ("Mark", 23) // Inferred as (String, int)
```

To access the elements stored inside a tuple, use the characters 'a' to 'z', where 'a' denotes the first element.

```
var personName = person.a // Access the String from 'person'
var personAge = person.b // Access the int from 'person'
```