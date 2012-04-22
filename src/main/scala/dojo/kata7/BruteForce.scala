package dojo.kata7
import java.security.MessageDigest
import scala.actors.Actor._
import scala.math._
import scala.actors.Futures._
import scala.actors.remote.RemoteActor._

/**
 * There is a SHA256 Hash and you need to find the corresponding
 * PIN code that is a 7/8 digits number.
 * See Abstract Test for a hint how to calculate the Hash.
 */
object BruteForce {
  val Cores = 16
 
  /**
   * klein, einfach, langsam!
   */
  def findNumberForHash(targetHash: String, maxDigits: Long): Option[Long] = {
    (1L to upperBound(maxDigits)) find {
      number => targetHash.equals(hash(String.valueOf(number)))
    }
  }

  /**
   * http://www.scala-lang.org/docu/files/actors-api/actors_api_guide.html#
   * http://dirkmeister.blogspot.com/2008/12/remote-actors-in-scala.html
   * http://youshottheinvisibleswordsman.co.uk/2009/04/01/remoteactor-in-scala/
   * http://akka.io/docs/akka/1.2/scala/actors.html
   */
  def findNumberForHashFaster(targetHash: String, maxDigits: Int): Option[Long] = {

    // Zahlenraum in etwa gleichgroße Teile zerschnippeln
    val ranges = createRanges(upperBound(maxDigits), Cores)

    println("ranges: " + ranges.size)
    /**
     * Die Antworten der einzelnen Teiljobs kommen irgendwann später...
     */
    val results = ranges.map {
      range =>
        future {
          val result = range find {
            number =>
              targetHash.equals(hash(String.valueOf(number)))
          }
          println("found: " + result + "("+ranges.indexOf(range)+")")
          result;
        }
    }

    // hier wird auf die Antworten gewartet
    val options = results.map {
      future => future.apply()
    }
    
      while(true){
        val result = results.find(f=>f.isSet && f.apply().isDefined)
        if(result.isDefined){
          return result.get()
        }
      }
      None
  }

  private def upperBound(maxDigits: Long) = {
    (pow(10L, maxDigits) - 1L).toLong
  }

  /**
   * Zerteile in etwa gleichgroße Stücke
   */
  def createRanges(maxNumber: Long, chunks: Int) = {
    val rangeWidth = maxNumber / chunks
    val rest = maxNumber % chunks
    var start = 0L
    var end = 0L
    for (r <- 0L until chunks) yield {
      end = start + rangeWidth + (if (r < rest) 1 else 0) // ... + korrektur, um den Rest gerecht auf die Chunks aufzuteilen
      val range = longRange(start, end)
      start = end
      range
    }
  }

  /**
   * Iterator für große Zahlenbereiche
   * start until end
   */
  def longRange(start: Long, end: Long) = new Iterator[Long] {
    private[this] var i = start
    def hasNext = i < end
    def next = { val j = i; i += 1; j }
  }

  /**
   * vorgegebene Hash-Methode
   */
  def hash(text: String): String = {
    val md = MessageDigest.getInstance("SHA-256");
    val bytes = md.digest(text.getBytes());

    val hash = new StringBuffer();
    bytes.foreach(b => hash.append(Integer.toHexString(0xFF & b)))
    hash.toString()
  }
}
