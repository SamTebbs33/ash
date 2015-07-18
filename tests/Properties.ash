class Properties {
	static var name = "" -> {
		set -> new.isEmpty() ? "unnamed" : new
		get -> self // This is unnecessary, but is here for clarity
	}
	public static func main(args : String[]) {
		System.out.println(name)
		name = "Mark"
		System.out.println(name)
		name = ""
		System.out.println(name)
	}
}
