# Source files
Ash source files are where you enter the majority of your project's code. The extension used for source files is **.ash**.

The structure of a source file is as follows:

1. An optional package declaration.
2. An optional list of include declarations.
3. An optional list of import declarations.
4. A public class/enum/interface declaration, whose name is the same as the file's.
5. Any number of other non-public class/enum/interface declarations.

## Package declarations
A package declaration denotes the file's location and namespace. Imagine the following project directory structure, where all source files are under the **src** directory:

```
- src
	- folder1
		- MyClass.ash
		- folder2
			- MyClass2.ash
	- MyMainClass.ash
```

The **MyClass** class would have the **folder1** package, the **MyClass2** class would have the **folder1.folder2** package, and the **MyMainClass** class would have an empty package. Only if your class file has non-empty package, do you add a package declaration to the file.

```
package folder1.folder2
```	

## Include declarations
An include declaration allows you to use a [definition file](Definition_files.md) inside your source file. An include declaration is denoted by the `include` keyword, the definition file's package name, and then the definition file's name.

```
include foo.bar.MyDefFile
```

Each separate import declartion is specified on its own line.

## Import declarations
An import declaration makes it possible to use a class and its members from another package. A basic import declaration is denoted by the `import` keyword, then the package name, and then the class name.

```
// Imports the LinkedList class
import java.util.LinkedList
```

You can import multiple classes from the same package by appending more class names (separated by commas):

```
// Imports the LinkedList, ArrayList and HashMap classes
import java.util.LinkedList, ArrayList, HashMap
```

Each separate import declartion is specified on its own line.

```
import java.util.LinkedList
import foo.bar.MyClass
```

The imported class can either be a source file (Java or Ash), or a compiled class file