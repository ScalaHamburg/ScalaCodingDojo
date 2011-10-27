package dojo.kata2

import java.security.MessageDigest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import org.junit.BeforeClass
import org.junit.Before
import scaladojo.testengine.AbstractTest

/**
 * :))
 */
class NumberWordCountTest extends AbstractTest {

  val result = "1a455b216c6e916943acf3fa4c7e57a7a5cac66d97cc51befca810c223ef9c23"

  @Before def before {
    printHeading(2)
  }

  @Test def testCount() {
    execute {
      val lengthOfNumbers = NumberWordCount.count(1, 1000)
      val hash = encrypt(lengthOfNumbers.toString())

      val isEqual = hash.toString().equals(result)
      assertTrue("It is wrong! (don't expect me to tell you the right number)", isEqual)
    }
  }
  
  @Test def testCountShort() {
  	execute {
  		val lengthOfNumbers = NumberWordCount.countShort
  				val hash = encrypt(lengthOfNumbers.toString())
  				
  				val isEqual = hash.toString().equals(result)
  				assertTrue("It is wrong! (don't expect me to tell you the right number)", isEqual)
  	}
  }

}


