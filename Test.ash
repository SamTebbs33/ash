import java.util.HashMap
import java.lang.CharSequence

class Test2<T>() {
	public func test() : T {
	
	}
}

class Test {
	var testObj = Test2<String>()
	var test : String = testObj.test()
	var test2 : int = testObj.test() // Should fail, as a String can't be assigned to an int
}