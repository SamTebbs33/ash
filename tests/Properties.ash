class Properties {
	static var name = "" {
		set = newVal.isEmpty() ? "unnamed" : newVal
	}
	public static func main(args : [String]) {
		System.out.println(name)
		name = "Mark"
		System.out.println(name)
		name = ""
		System.out.println(name)
	}
}
