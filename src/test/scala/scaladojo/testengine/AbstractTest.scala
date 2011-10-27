package scaladojo.testengine
import org.scalatest.junit.AssertionsForJUnit
import java.security.MessageDigest
import org.scalatest.junit.ShouldMatchersForJUnit

class AbstractTest extends AssertionsForJUnit with ShouldMatchersForJUnit with AsciiArt {

  def encrypt(text: String): java.lang.StringBuffer = {
    val md = MessageDigest.getInstance("SHA-256");
    val bytes = md.digest(text.getBytes());

    val hex = new StringBuffer();
    bytes.foreach(b => hex.append(Integer.toHexString(0xFF & b)))
    hex
  }

  def getCurrentMethodName = {
    Thread.currentThread().getStackTrace()(3).getMethodName();
  }

  /**
   * Führt den Testcode aus und gibt im Fehlerfall eine Meldung aus.
   */
  def execute(testCode: => Unit) {
    try {
      testCode
      printSuccess(getCurrentMethodName)
    } catch {
      case x => {
        printFail(getCurrentMethodName +":"+x.getMessage())
        throw x
      }
    }
  }
  
   override def printFail(text: String): Unit = {
     super.printFail(text)
     fail(text)
   }
}