public class Test {
	public var name = "Sam" -> {
		set {
			if(true) return "Tebby"
			else return "Tebbs"
		}
	}
	public func test(){
		name = "Yo"
		var name2 = name
	}
}