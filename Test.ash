class Test(name : String) : Object{
	public var a : int -> {
		get -> self * 2
		set {
			self = 0
		}
	}
}