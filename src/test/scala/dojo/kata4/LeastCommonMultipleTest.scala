package dojo.kata4

import scaladojo.testengine.AbstractTest
import org.junit.Before
import org.junit.Test

class LeastCommonMultipleTest extends AbstractTest {
  @Before def before {
    printHeading(4)
  }

  @Test def testLeastCommonMultiple12and8() {
    execute {
      LeastCommonMultiple.lcmRecursive(List(12,8)) should be (24)
    }
  }
  
  @Test def testLeastCommonMultiple1to20() {
    execute {
      LeastCommonMultiple.lcmRecursive (1 to 20) should be (232792560)
    }
  }
  
  @Test def testLeastCommonMultiple1to20Short() {
    execute {
      LeastCommonMultiple.lcmShort (1 to 20) should be (232792560)
    }
  }
}