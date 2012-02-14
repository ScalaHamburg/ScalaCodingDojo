/*
 * Copyright 2011 by Nils Schmidt.
 */
package dojo.kata6

import java.lang.System

/**
 * @author Nils Schmidt
 */
object ProjectEuler23Nils extends Kata6 {

  /**
   * Creates all abundant numbers between 12 and 28123. (Sorted!)
   * @see Project Euler 23 for clarification of the limits.
   */
  override def allAbundants = (12 to LIMIT).filter(n => sumOfProperDivisors(n) > n)

  def sumOfProperDivisors(n: Int) = divisors(n).sum
  def divisors(n: Int) = (1 to n / 2).filter(d => (n % d) == 0)

  def notExpressableAsSumOfAbundantNumbers: Set[Int] = {
    var crossedOutArray: Array[Boolean] = new Array[Boolean](LIMIT + LIMIT + 1)
    crossedOutArray(0) = true

    val abundants = allAbundants //.par // Parallel version needs twice as much time...
    var filteredAbundants = allAbundants.toList

    while (!filteredAbundants.isEmpty) {
      val currentAbundant = filteredAbundants.head
      abundants.foreach(a => crossedOutArray(a + currentAbundant) = true)
      filteredAbundants = filteredAbundants.tail
    }

    var notCrossedOut = Set[Int]()
    for (i <- 1 to LIMIT) {
      if (!crossedOutArray(i)) notCrossedOut += i
    }
    return notCrossedOut
  }
}