public class Peroprties {
	public static var name : String? -> {
		set -> new.isEmpty() ? "unnamed" : new
		get -> self
	}
	public static func main(args : String[]) {
		name = "Sam"
		System.out.println(name) // Prints "Sam"
		name = ""
		System.out.println(name) // Prints "unnamed"
	}
}