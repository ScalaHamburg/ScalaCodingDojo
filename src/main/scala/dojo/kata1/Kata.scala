package dojo.kata1
import scala.annotation.tailrec

/*
 *  a) Find the number of elements of a list.
     Example:
     scala> length(List(1, 1, 2, 3, 5, 8))
     res0: Int = 6
    b) und da das zu einfach ist, noch einmal als tailrecursive Lösung und
    einmal mittels ‚fold‘… 
 */

/**
 * Einfach nur die Methoden ausimplementieren und den Test starten (sbt test-only com.logica.scaladojo.kata1.ListLengthTest) 
 */
object ListLength {
  
  def lengthOfList(list: List[Any]) = {
    list.length
  }

  /**
   * Siehe Kap. 8.9 Tail Recursion (S. 155)
   */
  def lengthOfListWithTailrecursion(list: List[Any]) = {
  	len(list, 0)
  }

  @tailrec
  private def len(list: List[Any], count:Int):Int = {
    if(list.isEmpty){
      count
    }else{
    	val rest = list.tail
    	len(rest, count+1)
    }
  }
  
  /**
   * Siehe Kap. 16.7 Higher order methods on Lists (S.319)
   */
  def lengthOfListWithFoldingRight(list: List[Any]) = {
    (list foldRight 0)((x:Any, y:Int) => 1+y)
  }

  /**
   * Siehe Kap. 16.7 Higher order methods on Lists (S.319)
   */
  def lengthOfListWithFoldingLeft(list: List[Any]) = {
  	(0 /: list)((x:Int, y:Any) => 1+x)
  }
}