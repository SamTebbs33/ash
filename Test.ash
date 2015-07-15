public class Person {
	public static var name : String? -> {
		set -> "Tebby"
	}
	public static func main(args : String[]) {
		name = "Sam"
		System.out.println(name)
	}
}