package dojo.kata1

import java.lang.StackOverflowError
import java.lang.System
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import scaladojo.testengine.AbstractTest

class ListLengthTest extends AbstractTest {
  
  @Before def before {
  	printHeading(1)
  }

  @Test def testLength() {
    execute(1){
	    val len = ListLength.lengthOfList(List("1", "2", "3"))
	    len should equal(3)
    }
    
  }

  @Test def testLengthWithTailrecursion() {
    execute(1){
	  	try{
		    val list = 1 to 20000 toList
		    val len = ListLength.lengthOfListWithTailrecursion(list)
		    val isEqual = len.equals(20000)
		    len should equal(20000)
	    }catch{
	      case x:StackOverflowError =>{
	      	printFail("You did'nt use _TAIL_recursion!")
	      }
	    }
    }
  }

  @Test def testLengthWithFoldingRight() {
    execute(1){
	    val tic = System.nanoTime();
	    val len = ListLength.lengthOfListWithFoldingRight(1 to 2000 toList)
	    println(System.nanoTime() - tic);
	
	    len should equal(2000)
    }
    // http://www.parleys.com/#st=5&id=1552&sl=2
    
  }

  @Test def testLengthWithFoldingLeft() {
    execute(1){
	    val tic = System.nanoTime();
	    val len = ListLength.lengthOfListWithFoldingLeft(1 to 200000 toList)
	    println(System.nanoTime() - tic);
	    assertEquals(200000, len)
    }
  }

}

