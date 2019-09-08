# Getting started
Note that you must have the Java runtime installed before compiling Ash files.
All ash source files have the extension `.ash`, and are compiled with the `java -jar Ash.jar <file_path>` command (a more convenient one will be introduced soon). You can get the compiler in jar form from the [GitHub releases](https://github.com/ash-lang/ash/releases) page.

## Your first Ash file
Just like Java, Ash programs start from a function called `main`. This function must be `public` and `static`, must return `void` and must take an argument of type `[String]`. See the below `HelloWorld.ash` file as an example.
```
public class HelloWorld {

  public static func main(args : [String]) {
    
  }

}
```
You can then compile this by running `java -jar Ash.jar HelloWorld.ash`. Note that you must be inside the directory with `HelloWorld.ash` and `Ash.jar`.

There shouldn't have been any errors when compiling the file (if there were, check if anyone has posted an issue and if not, please make one so that we can fix the problem).
You could then run the resulting class file with `java HelloWorld`, but it won't do anything interesting yet.

Add the following line to your `main` function's body.
```
System.out.println("Hello, world!")
```
So that it looks like this.
```
public class HelloWorld {

  public static func main(args : [String]) {
    System.out.println("Hello, world!")
  }

}
```
Now compile this and run it, and you should get a friendly greeting!
Congrats, you have now made your first semi-interesting Ash program! You may be thinking that this looks exactly like Java, but you have yet to find out the things that make Ash better than Java (read the documentation to find them out).
