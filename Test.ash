public class MatchStatement {
	
	public static func main(args : String[]) {
		var a = 1
		match a {
			2 -> System.out.println("0")
			0 -> System.out.println("1")
			1 -> System.out.println("2")
			_ -> System.out.println("other")
		}
		if(true) System.out.println("Done")
	}
	
}