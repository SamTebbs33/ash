import java.util.HashMap
import java.lang.Integer

class Test2<T>() {
	public func test() : T {
	
	}
}

class Test {
	var testObj : Test2<HashMap<String>>?
	var test = testObj.test()
}