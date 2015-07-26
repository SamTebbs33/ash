class A(name : String){
	construct {
		System.out.println("A")
	}
}

class B(name : String) : A(name){

	construct {
		System.out.println("B")
	}
	
	public static func main(args : String[]) {
		var b = B("Sam")
	}
}