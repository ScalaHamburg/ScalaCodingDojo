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
 * Einfach nur die Methoden ausimplementieren und den Test starten (sbt test-only dojo.kata1.Kata) 
 */
object ListLength {
  
  /**
   * Die Länge der übergebenen Liste soll berechnet werden
   */
  def lengthOfList(list: List[Any]) = {
    None
  }

  /**
   * In dieser Implementierung soll die Länge der Liste per Recursion ermittelt werden
   * Damit es performant auch mit längeren Listen funktioniert, sollte es tailrecursiv
   * umgesetzt werden Siehe http://www.artima.com/pins1ed/functions-and-closures.html#8.9
   * Dazu bitte die Kommentarzeichen vor '@tailrec' entfernen.
   */
  //@tailrec
  def lengthOfListWithTailrecursion(list: List[Any]) = {
  	None
  }
  
  /**
   * Hier bitte mittels folding umsetzen
   * Siehe Kap. 16.7 Higher order methods on Lists http://www.artima.com/pins1ed/working-with-lists.html#16.7
   */
  def lengthOfListWithFoldingRight(list: List[Any]) = {
   None
  }

  /**
   * Hier bitte mittels folding umsetzen
   * Siehe Kap. 16.7 Higher order methods on Lists http://www.artima.com/pins1ed/working-with-lists.html#16.7
   */
  def lengthOfListWithFoldingLeft(list: List[Any]) = {
  	None
  }
}
