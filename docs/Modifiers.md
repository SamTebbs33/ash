# Modifiers
Modifiers are keywords that are used to modify a member (class, enum, interface, field, function et.c). Below is a list of the keywords offered by Ash:

* **public**: Makes the member accessible outside of this class or this file. This member is used by default.
* **private**: Makes this member inaccessible outside of the current class.
* **protected**: Makes this member only visible to this type and its sub-types.
* **static**: Makes this member only accessible by prefixing the member with the class name, e.g the `pow()` function inside the `Math` class is static and is accessed by using `Math.pow()`.
* **final**: When used on a class, it makes the class non-extendable. When used on a function, it makes the function non-overridable.
* **native**: Desnotes that the function is implemented in native code, rather than in Java.
* **override**: Denotes that this function is overriding a super-type function.