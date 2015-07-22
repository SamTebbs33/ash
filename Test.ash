public class MatchStatement {
	public static func main(args : String[]) {
		const name : String? = "yo"
		match name {
			null -> print("Is null")
			_ -> print("Is else")
		}
		
	}
	public static func print(msg : String) -> System.out.println(msg)
}