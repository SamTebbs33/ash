public class Range(start : int, end : int) : Iterable<T> {

	public var step = 1
	
	public func Range(start : int, end : int, step : int {
		this(start, end)
		this.step = step
	}
	
	public func iterator() : Iterator<Integer> -> RangeIterator(start, end, step)
	
}

class RangeIterator(start : int, end : int, step : int) : Iterator<Integer> {
	
	private var current = start
	
	public func hasNext() : bool -> current + step <= end
	
	public func next() : int {
		if current < start {
			current = start
		} else current += step
		return current
	}
	
}