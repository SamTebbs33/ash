# Definition files
Definition files allow you to make declarations that don't belong to any specific class. Definition f.iles have the **.ashd** extension

The structure of a definition file is as follows:

1. An optional package declaration.
2. An optional list of include declarations.
3. An optional list of import declarations.
4. Any number of operator definitions.
5. Any number of function declarations.

When a source file includes the definition file, they will be able to use the included definition files, imported classes and declared functions from the definition file.

When calling a def-file's function (commonly caled a global function) from a class, it should have no prefix, for example:

```
// DefFile.ashd
func doThings() {

}

// MyClass.ash
func useGlobalFunc() {
	doThings() // Calls the 'doThings()' global function
}
```