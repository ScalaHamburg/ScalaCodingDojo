/*
 * Copyright 2011 by Nils Schmidt.
 */
package dojo.kata6

import java.lang.System

/**
 * This class does not implement a solution to Project Euler Problem 23.
 * 
 * Instead of "Find the sum of all the positive integers which cannot be
 * written as the sum of TWO abundant numbers." it calculates the " sum of all
 * the positive integers which cannot be written as the sum of ANY (1...N)
 * abundant numbers"
 * 
 * @author Nils
 */
object NotProjectEuler23 extends Kata6 {
  //  private val LIMIT = 28123 // 10000 // 1000

  /**
   * Creates all abundant numbers between 12 and 28123. (Is already sorted!)
   * @see Project Euler 23 for clarification of the limits.
   */
  override def allAbundants = {
    (12 to LIMIT).filter(check(_) == 'abundant)
  }

  def check(number: Int): Symbol = {
    val xtype = sumOfProperDivisors(number) - number
    if (xtype < 0) return 'deficient
    if (xtype > 0) return 'abundant
    return 'perfect
  }

  /**
   * Sum of proper divisors.
   */
  def sumOfProperDivisors(number: Int): Long = {
    (1 to number / 2).filter(number % _ == 0).sum
  }

  override def notExpressableAsSumOfAbundantNumbers: Set[Int] = {
    var crossedOut: Set[Int] = Set()
    crossedOut += 0

    messuare("Big loop") { _ =>
      allAbundants.foreach(
        currentAbundant => {
          messuare("Current Abundant " + currentAbundant) { _ =>
            // add sums with every existing crossed number

            messuare("For each ADD") { _ =>
              crossedOut.foreach(
                x => if (x + currentAbundant < LIMIT) crossedOut += (x + currentAbundant))
            }
            // add multiples of self
            var multiple = currentAbundant
            while (multiple < LIMIT) {
              crossedOut += multiple
              multiple += currentAbundant
            }
          }
        })
    }

    val allNumbers = Set() ++ (1 to LIMIT)
    return allNumbers -- crossedOut
  }

  /**
   * 1546907ms needed for Big loop
   * (25,781 Minutes)
   */
  def notExpressableAsSumOfAbundantNumbersWithArray: Set[Int] = {
    var crossedOutArray: Array[Boolean] = new Array[Boolean](LIMIT + 1)
    crossedOutArray(0) = true

    messuare("Big loop") { _ =>
      allAbundants.foreach(
        currentAbundant => {
          messuare("Current Abundant " + currentAbundant) { _ =>
            for (i <- 0 to LIMIT) {
              if (crossedOutArray(i) && (currentAbundant + i <= LIMIT)) crossedOutArray(i + currentAbundant) = true
            }
          }
        })
    }
    var notCrossedOut = Set[Int]()
    for (i <- 1 to LIMIT) {
      if (!crossedOutArray(i)) notCrossedOut += i
    }
    return notCrossedOut
  }

  /**
   * 531ms needed for Big loop
   */
  def notExpressableAsSumOfAbundantNumbersWithArrayAndOptimization: Set[Int] = {
    var crossedOutArray: Array[Boolean] = new Array[Boolean](LIMIT + 1)
    crossedOutArray(0) = true

    var filteredAbundants = allAbundants.toList

    var i = 0
    messuare("Big loop") { _ =>
      while (i < filteredAbundants.length) {
        val currentAbundant = filteredAbundants(i)
        messuare("Current Abundant " + currentAbundant) { _ =>
          for (i <- 0 to LIMIT) {
            if (crossedOutArray(i) && (currentAbundant + i <= LIMIT)) {
              crossedOutArray(i + currentAbundant) = true
              // OPTIMIZATION:
              filteredAbundants -= (i + currentAbundant)
            }
          }
        }
      }
    }
    var notCrossedOut = Set[Int]()
    for (i <- 1 to LIMIT) {
      if (!crossedOutArray(i)) notCrossedOut += i
    }
    return notCrossedOut
  }

  /**
   * Square Root (for Integers)
   */
  private def sqrt(i: Int): Int = Math.sqrt(i).round.intValue

  private def messuare(name: String)(function: Unit => Unit): Unit = {
    val startTime = System.currentTimeMillis()
    Console.println("Starting " + name)
    function.apply()
    Console.println(System.currentTimeMillis() - startTime + "ms needed for " + name)
  }
}