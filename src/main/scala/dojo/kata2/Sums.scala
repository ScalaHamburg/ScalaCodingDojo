package dojo.kata2

/*
 *  If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
 *  Find the sum of all the multiples of 3 or 5 below 1000.
 */

/**
 * Einfach nur die Methoden ausimplementieren und den Test starten (sbt test-only dojo.kata3.sumsnacci) 
 */
object Sums{
  def sums1 = {	// HS
  		((0/:(for(i<-1 until 1000;if i%3==0||i%5==0)yield i))(_+_))
  }
  def sums2 = {	// PT
  		(((1 to 999):\0)((x,y)=>if(x%3==0||x%5==0)x+y else y))
  }
  def sums3 = {	// FS 
  		(1 to 999).filter(n=>(n%3==0||n%5==0)).reduce(_+_) 
  }
  def sums4 = {  // PT
  		(0/:(1 to 999 filter(x=>x%3==0||x%5==0)))(_+_) 
  }
  def sums5 = {  // HS
  		(1 to 999 filter(x=>x%3*x%5<1)).sum  
  }
}