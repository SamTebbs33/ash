public class Generics<T> {
	public var obj : T?
	public static func main(args : String[]){
		var g = Generics<Object>()
		var str : String? = g.obj
	}
}