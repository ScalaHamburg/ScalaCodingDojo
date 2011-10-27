package dojo.kata3

import scaladojo.testengine.AbstractTest
import org.junit.Before
import org.junit.Test

class SumsTest extends AbstractTest {
  @Before def before {
    printHeading(3)
  }

  @Test def testSums1() {
    execute {
      Sums.sums1 should be(233168)
    }
  }
  @Test def testSums2() {
    execute {
      Sums.sums2 should be(233168)
    }
  }
  @Test def testSums3() {
    execute {
      Sums.sums3 should be(233168)
    }
  }
  @Test def testSums4() {
    execute {
      Sums.sums4 should be(233168)
    }
  }
  @Test def testSums5() {
    execute {
      Sums.sums5 should be(233168)
    }
  }
}