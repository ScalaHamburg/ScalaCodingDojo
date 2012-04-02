package scaladojo.testengine
import org.scalatest.junit.AssertionsForJUnit
import java.security.MessageDigest
import org.scalatest.junit.ShouldMatchersForJUnit
import org.scalatest.Tag

class AbstractTest extends AssertionsForJUnit with ShouldMatchersForJUnit with AsciiArt {

  def encrypt(text: String): String = {
    val md = MessageDigest.getInstance("SHA-256");
    val bytes = md.digest(text.getBytes());

    val hex = new StringBuffer();
    bytes.foreach(b => hex.append(Integer.toHexString(0xFF & b)))
    hex.toString()
  }

  def getCurrentMethodName = {
    Thread.currentThread().getStackTrace()(3).getMethodName();
  }

  /**
   * Führt den Testcode aus und gibt im Fehlerfall eine Meldung aus.
   */
  def execute(kataNr:Int)(testCode: => Unit) {
    try {
      printHeading(kataNr)
      testCode
      printSuccess(getCurrentMethodName)
    } catch {
      case x => {
        printFail(getCurrentMethodName +":"+x.getMessage())
        throw x
      }
    }
  }
  
  def withTimer(timedCode: => Unit) = {
    val start = System.currentTimeMillis()
    timedCode
    val stop = System.currentTimeMillis()
    stop - start
  }
  
   override def printFail(text: String): Unit = {
     super.printFail(text)
     fail(text)
   }
   
   object SlowTest extends Tag("scaladojo.SlowTest")
   
}