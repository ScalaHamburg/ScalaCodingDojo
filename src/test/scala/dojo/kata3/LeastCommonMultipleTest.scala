package dojo.kata3

import scaladojo.testengine.AbstractTest
import org.junit.Before
import org.junit.Test

class LeastCommonMultipleTest extends AbstractTest {

  @Test def testLeastCommonMultiple12and8() {
    execute(4) {
      LeastCommonMultiple.lcm(List(12,8)) should be (24)
    }
  }
  
  @Test def testLeastCommonMultiple1to20() {
    execute(4) {
      LeastCommonMultiple.lcmRecursive(1 to 20) should be (232792560L)
    }
  }
} 