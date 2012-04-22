
class Node(val value: Symbol) {

  import scala.collection.mutable._

  val neighbours = Set[Node]()
  val weights = HashMap[Node, Float]()

  def name = value.name
  override def toString() = "[Node: " + name + "]"

  def hasNeighbour(n: Node) = {
    neighbours contains n
  }

  def addNeighbour(n: Node) {
    neighbours += n
    if (!n.hasNeighbour(this)) {
      n.addNeighbour(this)
    }
  }

  def addNeighbour(n: Node, d: Float) {
    addNeighbour(n)
    weights += n -> d
  }

}

object SearchExample {
def main(args: Array[String]) {
  
  import scala.collection.mutable._
  val nodes = Map[Symbol, Node]()

  add('Oradea, 'Zerind, 71)
  add('Oradea, 'Sibiu, 151)
  add('Zerind, 'Arad, 75)
  add('Arad, 'Timisoara, 118)
  add('Arad, 'Sibiu, 140)
  add('Timisoara, 'Logoj, 111)
  add('Logoj, 'Mehadia, 70)
  add('Mehadia, 'Drobeta, 75)
  add('Drobeta, 'Craiova, 120)
  add('Sibiu, 'Rimnicu_Vilcea, 80)
  add('Sibiu, 'Fagaras, 99)
  add('Rimnicu_Vilcea, 'Pitesti, 97)
  add('Rimnicu_Vilcea, 'Craiova, 146)
  add('Craiova, 'Pitesti, 138)
  add('Fagaras, 'Bucharest, 211)
  add('Pitesti, 'Bucharest, 101)
  add('Bucharest, 'Giurgiu, 90)
  add('Bucharest, 'Urzizeni, 85)
  add('Urzizeni, 'Hirsova, 98)
  add('Urzizeni, 'Vaslui, 142)
  add('Hirsova, 'Eforie, 86)
  add('Vaslui, 'Iasi, 92)
  add('Iasi, 'Neamt, 87)

  def add(a: Symbol, b: Symbol, d: Float) {
    val n1 = if (!nodes.contains(a)) {
      val n = new Node(a)
      nodes += a -> n
      n
    } else {
      nodes(a)
    }
    val n2 = if (!nodes.contains(b)) {
      val n = new Node(b)
      nodes += b -> n
      n
    } else {
      nodes(b)
    }
    n1.addNeighbour(n2, d)
    n2.addNeighbour(n1, d)
  }

  (new TreeSearch(nodes('Arad), nodes('Bucharest)) with SimpleSearchStrategy).search
  println("\n\n Any better?\n")
  (new TreeSearch(nodes('Arad), nodes('Bucharest)) with BetterSearchStrategy).search
  }
}

trait SearchStrategy {

  def remove_choice: (List[Node], Float)

}

trait SimpleSearchStrategy {

  import scala.collection.mutable._
  val frontier: Map[List[Node], Float]

  def remove_choice = {
    val p = frontier.first
    frontier.remove(p._1)
    p
  }

}

//Uniform-Cost-Search aka Cheapest First
trait BetterSearchStrategy {

  import scala.collection.mutable._
  val frontier: Map[List[Node], Float]

  def remove_choice = {
    var p = frontier.first
    var cost = p._2
    frontier.iterator.foreach { pp =>
      if (pp._2 < cost) {
        p = pp
        cost = p._2
      }
    }
    frontier.remove(p._1)
    p
  }

}

abstract class TreeSearch(start: Node, stop: Node) extends SearchStrategy {

  import scala.collection.mutable._
  val known = Set[Symbol]()
  val frontier = new HashMap[List[Node], Float]()

  def search {
    frontier += List(start) -> 0.0f
    loop(stop)
  }

  def loop(stop: Node) {
    if (frontier.size > 0) {
      val p = remove_choice
      if (p._1.last == stop) {
        println("Found path:")
        println(p)
      } else {
        val place = p._1.last
        val cost = p._2
        known += place.value
        place.neighbours.foreach { n: Node =>
          if (known contains n.value) {
            //
          } else {
            println("adding " + n.value)
            frontier += (p._1 ++ List(n)) -> (cost + place.weights(n))
          }
        }
        loop(stop)
      }
    } else {
      println("No Path")
    }
  }

}
