package dojo.kata5
import org.junit.Test
import scaladojo.testengine.AbstractTest

/**
 * a)testBruteForce
 * You are really annoyed about the way the result is concealed in NumberWordCountTest.
 * You make a rough guess and estimate that the resulting numbers maximum length would be 7.
 * You write a brute force hash code breaker where you iterate all hashes from 1 to 9999999
 * and return the clear text number for the matching hash.
 * 
 * b) testBruteForceFaster
 * You decide to write a faster algorithm with Actors to utilize all of your 16 Cores!
 */
class BruteForceTest extends AbstractTest {

	val sevenDigitsHash = "1ad8422f80c229bd39d5c68f74263b803d7fc24992f1abef88c7e29741e40"
  val eightDigitsHash = "9e23539d7ce6b3788b4ae2c58bb91815b8a4532e1d134a17baab733853e62824"
    
  @Test
  def testBruteForce {
    execute(5) {
      val millis = withTimer {
        val result = BruteForce.findNumberForHash(sevenDigitsHash, 7).getOrElse(-1L)
        encrypt(result toString) should be(sevenDigitsHash)
      }
      println("Dauer: " + millis + " ms");
    }
  } 

  @Test
  def testBruteForceFaster {
    execute(5) {
      val millis = withTimer {
        val result = BruteForce.findNumberForHashFaster(eightDigitsHash, 8).getOrElse(-1L)
        encrypt(result toString) should be(eightDigitsHash)
      }
      println("Dauer: " + millis + " ms");
    }
  }
}