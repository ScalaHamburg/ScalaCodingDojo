/*
 * Copyright 2011 by Nils Schmidt.
 */
package dojo.kata6

import scala.util._

/**
 * @author Nils
 */
object ProjectEuler23 {
  private val LIMIT = 28123 // 10000 // 1000

  /**
   * Creates all abundant numbers between 12 and 28123. (Sorted!)
   * @see Project Euler 23 for clarification of the limits.
   */
  def allAbundants = {
    (12 to LIMIT).filter(n=>sumOfProperDivisors(n)>n)
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
  def notExpressableAsSumOfAbundantNumbers: List[Int] = {
    val abundants = allAbundants
    (1 to LIMIT).filter(n=>isNotSumOfAnyTwoInOrderedList(n, abundants)).toList
  }
  
  def isNotSumOfAnyTwoInOrderedList(n:Int, l:IndexedSeq[Int]) = {
    val list = l.takeWhile(a=>a<n).reverse
//    println(list)
    val isNotSum = ! list.exists(x=>list.exists(y=>x+y==n))
    if(isNotSum){println(n+" ist nicht summe abundanter Zahlen")}
    isNotSum
  }
  
  def divisors(a: Int):List[Int] = {
    (2 to a/2).filter(d=>(a%d)==0).toList
  }
}