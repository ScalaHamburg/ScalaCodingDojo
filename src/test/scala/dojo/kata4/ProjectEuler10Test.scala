package dojo.kata4
import scaladojo.testengine.AbstractTest
import org.junit.Test
import org.junit.Ignore

/**
 * Tests for ProjectEuler10 
 */
class ProjectEuler10Test extends AbstractTest {
  
  @Test
  def sumOfTen {
    execute(10) {
      val sum = ProjectEuler10.sumOfPrimes(10)
      sum should be(17)
    }
  }
  
  @Test
  @Ignore
  def sumOfTwoMillionen {
    execute(10) {
      val sum = ProjectEuler10.sumOfPrimes(2000000)
      sum should be (142913828922L)
    }
  }
}