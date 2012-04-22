package dojo.kata3

/**
 * Leicht an unsere Bedürfnisse Angepasste Implementierung von:
 * https://github.com/shadaj/euler/blob/master/src/euler/Euler2.scala
 */
object FibonacciShadaj {

  def nextFib(x: Int, y: Int): Stream[Int] = {
    x #:: nextFib(y, x+y)
  }

  def fib = nextFib(1,1)
  
  def solve(max: Int) = {
    fib.takeWhile(_ <= 4000000).filter(_ % 2 == 0).sum
  }
}