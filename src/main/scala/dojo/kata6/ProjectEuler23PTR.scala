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

  def divisors(n: Int):List[Int] = {
  		(1 to n/2).filter(d=>(n%d)==0).toList
  }

  /** 
   * Numbers that cannot be expressed as the sum of any abundant numbers.
   */
   def notExpressableAsSumOfAbundantNumbers_o: Set[Int] = {
    val abundants = allAbundants
    (1 to LIMIT).filter(n=>isNotSumOfAnyTwoInOrderedList(n, abundants)).toSet
  }
  
  def isNotSumOfAnyTwoInOrderedList(n:Int, l:IndexedSeq[Int]) = {
    val list = l.takeWhile(a=>a<n).reverse
    val isNotSum = ! list.par.exists(x=>list.par.exists(y=>x+y==n))
    if(isNotSum){println(n+" ist nicht summe abundanter Zahlen")}
    isNotSum
  }
  
  
   def notExpressableAsSumOfAbundantNumbers: Set[Int] = {
        var numbers = scala.collection.mutable.Set[Int]()
        numbers = numbers++(1 to LIMIT)
        
    		val abundants = allAbundants
    		
    		abundants.foreach{
    		  a =>
    		    abundants.foreach{
    		      b =>
    		      numbers.remove(a+b)
    		    }
    		}
    		numbers.toSet
  }
}