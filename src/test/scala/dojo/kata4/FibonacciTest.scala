package dojo.kata4
import scaladojo.testengine.AbstractTest
import org.junit.Test

/**
 * Bonus Level... (Nils war schneller)
 * http://projecteuler.net/problem=2
 *
 * Problem 2
 * Each new term in the Fibonacci sequence is generated by adding the previous
 * two terms. By starting with 1 and 2, the first 10 terms will be:
 * 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...
 * By considering the terms in the Fibonacci sequence whose values do not exceed
 * four million, find the sum of the even-valued terms.
 * 
 */
class FibonacciTest extends AbstractTest {
  
  @Test
  def evenFibonacciRecursive {
    execute(4) {
      val sum = FibonacciShadaj.solve(4000000)
      print(sum)
      sum should be(4613732)
    }
  }
}