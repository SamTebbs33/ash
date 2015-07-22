public class InitBlock {
	init {
		System.out.println("Block")
	}
	
	static var a = foo()
	
	static func foo() : int {
		System.out.println("Variable")
		return 0
	}
	
	public static func main(args : String[]) {
		
	}
}