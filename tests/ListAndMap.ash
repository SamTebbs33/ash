import java.util.LinkedList, HashMap

public class ListAndMap {
	static var list : LinkedList<String> = {"1", "2", "3"}
	static var map : HashMap<String, String> = {"1" : "val1", "2" : "val2"}
	public static func main(args : String[]) {
		System.out.println(list.get(0))
		System.out.println(map.get("1"))
	}
}