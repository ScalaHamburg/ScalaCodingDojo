package dojo.kata3

/*
 *  a) Find the least common multiple of a collection of numbers.
 */

object LeastCommonMultiple {
  
  def lcmRecursive(numbers: Seq[Int]) = {
    def gcd(a: Int, b: Int) = gcd1(a max b, a min b)
    def gcd1(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
    def lcm(a: Int, b: Int) = a * (b / gcd(a, b))
    numbers.foldLeft(1)(lcm(_, _))
  }
  
  def lcm(n: Seq[Int]) = {
    (9 to Int.MaxValue)find(x=>n.forall(x%_==0))get
  }

}