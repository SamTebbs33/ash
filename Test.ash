import java.lang.Iterable

class Test() : Iterable<String> {

	var array = Test()
	
	func main(){
		for(a in array) var b : String = a // Should pass
		for(a in array) var b : int = a // Should fail
	}
	
}