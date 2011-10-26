package com.logica.scaladojo.kata1

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

  @Test def testLength() {

    val lengthOfNumbers = NumberWordCount.letters(1, 1000)
    val hex = encrypt(lengthOfNumbers.toString())

    val isEqual = hex.toString().equals(result)
    assertTrue("It is wrong! (don't expect me to tell you the right number)", isEqual)
    printSuccess(getCurrentMethodName)
  }
  
}


