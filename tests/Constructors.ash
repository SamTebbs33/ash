class Constructors(name : String) {
	public var age = 0
	public func Constructors(name : String, b : bool) {
		this(name)
	}
	public static func main(args : [String]) {
		var obj = Constructors("Sam", true)
		var name2 = obj.name.trim()
		System.out.println(name2)
	}
}