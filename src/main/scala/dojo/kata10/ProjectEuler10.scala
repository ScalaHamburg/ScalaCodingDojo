/*
 * Copyright 2011 by Nils Schmidt.
 */
package dojo.kata10
import scala.annotation.tailrec

/**
 * The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
 * Find the sum of all the primes below two million.
 * (142913828922)
 *
 * @author Nils Schmidt
 */
object ProjectEuler10 {

  def sumOfAllPrimesBelowTwoMillion = sumOfPrimes(2000000)

  def sumOfPrimes(n: Long): Long = sumOfPrimes(n, 0)

  @tailrec
  def sumOfPrimes(n: Long, sumSoFar: Long): Long = {
    if (n == 2)
      sumSoFar + 2
    else {
      val currentPrime = previousPrime(n)
      sumOfPrimes(previousPrime(currentPrime - 1), sumSoFar + currentPrime)
    }
  }

  private def previousPrime(n: Long): Long = {
    if (isPrime(n))
      n
    else
      previousPrime(n - 1)
  }

  private def isPrime(n: Long): Boolean = {
    if (BigInt(n).isProbablePrime(999)) {
      if (n == 2)
        return true
      else if (n % 2 == 0)
        return false
      else {
        def sqrt = { Math.sqrt(n.toDouble).toLong }
        for (i <- 3L to sqrt) {
          if (n % i == 0) {
            return false
          }
        }
        return true
      }
    } else {
      return false
    }
  }
}