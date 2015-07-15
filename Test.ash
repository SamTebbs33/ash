public class Person {
	public static var name : String? -> {
		set -> new.isEmpty() ? "unnamed" : new
	}
	public static func main(args : String[]) {
		name = "Sam"
		System.out.println(name)
		name = ""
		System.out.println(name)
	}
}