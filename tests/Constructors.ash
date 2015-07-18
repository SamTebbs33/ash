class Constructors(name : String) {
	public var age = 0
	public func Constructors(name : String, b : bool) {
		this(name)
	}
	public static func main(args : String[]?) {
		Constructors("Sam", true)
	}
}