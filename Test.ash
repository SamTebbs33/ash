public class MatchStatement {
	public static func main(args : String[]) {
		const age = 3
		match age {
			0 -> print("Is 0")
			1 -> print("Is 1")
			2 -> print("Is 2")
			_ -> print("Is else")
		}
		
	}
	public static func print(msg : String) -> System.out.println(msg)
}