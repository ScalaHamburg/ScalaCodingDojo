package dojo.kata9
import java.io.BufferedWriter
import java.io.StringWriter
import java.io.Writer
import org.hamcrest.Description
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.mockito.ArgumentMatcher
import org.scalatest.junit.JUnitRunner
import org.scalatest.junit.JUnitSuite
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.FunSuite
import org.scalatest.Suite

/**
 * Fixture für unser SUT
 */
trait DummyScanner extends BeforeAndAfterEach { this: Suite =>

	var scanner:NetzwerkScanner = null//.asInstanceOf[NetzwerkScanner]

	// Schalter für das dummy-Netzwerk
  var online = false

  override def beforeEach() {
  	// 'injecten' des ping-dummys in unser SUT
    scanner = new NetzwerkScanner {
      override def ping(host: String, port: Int) = {
        if (online) Some(0L) else None
      }
    }
  }

  override def afterEach() {
    scanner.stopQueries()
  }

}

/**
 * Diesmal ist der Test für eine gegebene Klasse zu erstellen.
 * Bitte nicht von der Abstrakten Testklasse ableiten.
 */
@RunWith(classOf[JUnitRunner])
class NetzwerkScannerTest extends FunSuite with ShouldMatchers with MockitoSugar with DummyScanner {
  val Timeout = 100

  test("Network available") {

    online = true

    val str = new StringWriter()
    val writer = new BufferedWriter(str)

    val time = withTimer {
      scanner.startQueries(0, writer);

      // Da die Netzabfrage asynchron ausgeführt wird, müssen wir hier (nicht zu lange) warten!
      val result = eagerSleep(Timeout) {
        str.getBuffer().toString().contains("Netzzugang")
      }
      result should be(true)
    }

  }

  test("Network offline") {

    online = false

    val writer = mock[Writer]

    scanner.startQueries(0, writer);
    Thread.sleep(Timeout) // Hier gibt es leider nicht die Möglichkeit, eager zu warten, 
    // da sich der getestete Zustand nicht ändert

    verify(writer).write(endsWith("kein Zugang\r\n"))
    
  }

  test("Network off/on/off") {
    val writer = mock[Writer]

    online = false

    scanner.startQueries(0, writer);

    Thread.sleep(10)
    online = true
    Thread.sleep(10)
    online = false
    Thread.sleep(10)

    verify(writer, times(2)) write endsWith("kein Zugang\r\n")
    verify(writer) write endsWith("Netzzugang\r\n")
  }

  /**
   * Führt den übergebenen Code aus - und gibt die gemessene Zeit in ms zurück
   */
  def withTimer(timedCode: => Unit) = {
    val start = System.currentTimeMillis()
    timedCode
    val stop = System.currentTimeMillis()
    val time = stop - start
    println("took: " + time + "ms")
    time
  }

  /**
   * Wertet den Ausdruck 'check' wiederholt aus, bis er entweder 'true'
   * ergibt oder der 'timeout' ausgelaufen ist.
   * Das vorgegebene 'timeout' wird in 50 Intervalle geteilt.
   */
  def eagerSleep(timeout: Int)(check: => Boolean): Boolean = {
    val interval = timeout / 50
    val end = System.currentTimeMillis + timeout

    var success = check
    while (System.currentTimeMillis < end && !success) {
      Thread.sleep(interval)
      success = check
    }
    success
  }

  /**
   * Match't auf ein String Suffix
   * Siehe auch CustomArgumentMatchers unter
   * http://mockito.googlecode.com/svn/branches/1.7/javadoc/org/mockito/Matchers.html
   */
  class EndStringMatcher(suffix: String) extends ArgumentMatcher[String] {
    override def describeTo(desc: Description) {
      desc.appendText("The String does not end with '" + suffix + "'")
    }

    def matches(left: Any): Boolean = {
      left match {
        case left: String => left.endsWith(suffix)
        case _ => false
      }
    }
  }
  // erstellen eines Custom Argument Matchers
  def endsWith(suffix: String): String = argThat(new EndStringMatcher(suffix))
}


/**
 * Using generated mocks requires the following steps:
 * 1) Run the compiler with the -P:scalamock:generatemocks:<path> and -P:scalamock:generatetest:<path> arguments. This will search for @mock annotations and generate two different directories containing source code for each mocked class.
 * -P:scalamock:generatemocks:d:\persdat\workspacePTRscala\ScalaCodingDojo\src\main\mocks -P:scalamock:generatetest:d:\persdat\workspacePTRscala\ScalaCodingDojo\src\main\tests
 * 2) Run the compiler again to compile the contents of the generated mocks directory into a separate .jar file.
 * 3) Compile your test code together with the contents of the generated tests directory.
 * 4) When you run your tests (assuming that you're using ScalaTest) pass the "-Dmock.classes" argument, passing the path of the .jar file you created in step 2.
 * You can see how this is achieved for sbt here:
 * https://github.com/paulbutcher/scalamock-sbt-plugin/blob/master/src/main/scala/ScalaMockPlugin.scala
 */
//import org.scalamock.generated.GeneratedMockFactory
//    def mPing = mockFunction[String, Integer, Option[Long]]
//    mPing expects(*, *) returning Some(0L) anyNumberOfTimes
//      override def ping(host: String, port: Integer) = mPing(host, port) 