class Optional<T>(value : T) {
	var value : T?
	public func present() : bool -> value != null
}

class Test {
	var optObj = Optional<Test>(null)
	var obj = optObj.value // Should pass
	var obj2 : String = optObj.value // Should fail
}