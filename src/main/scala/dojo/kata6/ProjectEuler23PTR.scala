package dojo.kata6

import scala.util._

/**
 * @author PTR
 */
object ProjectEuler23PTR extends Kata6{

  /**
   * Creates all abundant numbers between 12 and 28123. (Sorted!)
   * @see Project Euler 23 for clarification of the limits.
   */
  override def allAbundants = {
    (12 to LIMIT).filter(n=> sumOfProperDivisors(n)>n)
  }

  /**
   * Sum of proper divisors.
   */
  def sumOfProperDivisors(number: Int): Long = {
    divisors(number).sum
  }

  /** 
   * Numbers that cannot be expressed as the sum of any abundant numbers.
   */
  override def notExpressableAsSumOfAbundantNumbers: Set[Int] = {
    val abundants = allAbundants
    (1 to LIMIT).filter(n=>isNotSumOfAnyTwoInOrderedList(n, abundants)).toSet
  }
  
  def isNotSumOfAnyTwoInOrderedList(n:Int, l:IndexedSeq[Int]) = {
    val list = l.takeWhile(a=>a<n).reverse
    val isNotSum = ! list.exists(x=>list.exists(y=>x+y==n))
    if(isNotSum){println(n+" ist nicht summe abundanter Zahlen")}
    isNotSum
  }
  
  def divisors(n: Int):List[Int] = {
    (1 to n/2).filter(d=>(n%d)==0).toList
  }
}