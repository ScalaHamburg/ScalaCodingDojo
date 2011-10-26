package com.logica.scaladojo.kata1

/*
 *  If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
 *  Find the sum of all the multiples of 3 or 5 below 1000.
 */

/**
 * Einfach nur die Methoden ausimplementieren und den Test starten (sbt test-only dojo.kata3.Fibonacci) 
 */
object F extends App{ 
print(((1 to 999):\0)((x,y)=>if(x%3==0||x%5==0)x+y else y))
}