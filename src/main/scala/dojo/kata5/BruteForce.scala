package dojo.kata5
import java.security.MessageDigest
import scala.actors.Actor._
import scala.math._

/**
 * There is a SHA256 Hash and you need to find the corresponding
 * PIN code that is a 7/8 digits number.
 * See Abstract Test for a hint how to calculate the Hash.
 */
object BruteForce {

  /**
   * Implementation for the 7 digits number
   */
  def findNumberForHash(targetHash: String, maxDigits: Long): Option[Long] = {
    None
  }

  /**
   * Implementation for the 8 digits number with Actors && Future
   */
  def findNumberForHashFaster(targetHash: String, maxDigits: Int): Option[Long] = {
    None
  }

  /**
   * the hash-algorithm to be used 
   */
  private def hash(text: String): String = {
    val md = MessageDigest.getInstance("SHA-256");
    val bytes = md.digest(text.getBytes());

    val hash = new StringBuffer();
    bytes.foreach(b => hash.append(Integer.toHexString(0xFF & b)))
    hash.toString()
  }
}