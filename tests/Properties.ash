public class Properties {
	public static var name : String? -> {
		set -> new.isEmpty() ? "unnamed" : new
	}
	public static func main(args : String[]) {
		name = "Sam"
		System.out.println(name) // Prints "Sam"
		name = ""
		System.out.println(name) // Prints "unnamed"
	}
}