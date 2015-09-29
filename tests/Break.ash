// Tests the functionality of the "break" statement. The message should only be printed once.
public class Break {
  public static func main(args : [String]) {
    for var x = 0, x < 10, x = x + 1 {
      System.out.println("iteration")
      break
    }
  }
}
