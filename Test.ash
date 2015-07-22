public class MatchStatement {
	public static func main(args : String[]) {
		const name : String? = "sam"
		match name {
			"Sammy", null, "Tebbs" -> print("0..2")
			"sam" -> print("3")
			_ -> print("else")
		}
		
	}
	public static func print(msg : String) -> System.out.println(msg)
}