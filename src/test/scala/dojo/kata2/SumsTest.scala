package dojo.kata3

import scaladojo.testengine.AbstractTest
import org.junit.Before
import org.junit.Test

class SumsTest extends AbstractTest {

  @Test def testSums1() {
    execute(3) {
      Sums.sums1 should be(233168)
    }
  }
  @Test def testSums2() {
    execute(3) {
      Sums.sums2 should be(233168)
    }
  }
  @Test def testSums3() {
    execute(3) {
      Sums.sums3 should be(233168)
    }
  }
  @Test def testSums4() {
    execute(3) {
      Sums.sums4 should be(233168)
    }
  }
  @Test def testSums5() {
    execute(3) {
      Sums.sums5 should be(233168)
    }
  }
}