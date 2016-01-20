// Ash, 9 lines
public class Person(age : int, name : String) {

    {
        println("New person with name %s and age %s" % name % age)
    }

    public static func main(args : [String]) = var p = Person(19, "Sam")

}