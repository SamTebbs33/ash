class Test {
	func test() {
		var a = ""
		match a {
			"hello" -> println("greeting 1")
			"hi" {
				println("greeting 2")
			}
			_ -> println("not a greeting")
		}
	}
}