public class MatchStatement {
	static var name = "Sam"
	public static func main(args : String[]) {
		match name {
			"Tebbs", "sam" -> System.out.println("Incorrect :(")
			"Sam" -> System.out.println("Correct :)")
			_  -> System.out.println("Else, (incorrect) :/")
		}
	}
}