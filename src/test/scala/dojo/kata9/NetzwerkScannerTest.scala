package dojo.kata9
import java.io.BufferedWriter
import java.io.Writer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.junit.JUnitSuite
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import java.io.StringWriter

/**
 * Diesmal ist der Test für eine gegebene Klasse zu erstellen.
 * Bitte nicht von der Abstrakten Testklasse ableiten.
 */
@RunWith(classOf[JUnitRunner])
class NetzwerkScannerTest extends FunSuite with ShouldMatchers {
  val Timeout = 100

  test("Network available") {

    val scanner = new NetzwerkScanner {
      override def ping(host: String, port: Integer) = Some(0L)
    }

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
    println("took: " + time + "ms")
  }

  test("Network offline") {
    val scanner = new NetzwerkScanner {
      override def ping(host: String, port: Integer) = None
    }
    val str = new StringWriter()
    val writer = new BufferedWriter(str)

    scanner.startQueries(0, writer);
    Thread.sleep(Timeout)

    val outString = str.getBuffer().toString()
    outString.contains("Netzzugang") should be(false)
    outString.length() should be (33) // Nachgezählt (für genau eine Message!)
  }

  test("Network off/on/off") {
    var online = false
    val scanner = new NetzwerkScanner {
      override def ping(host: String, port: Integer) = {
        if(online)Some(0L) else None
      }
    }
    val str = new StringWriter()
    val writer = new BufferedWriter(str)

    scanner.startQueries(0, writer);

    Thread.sleep(10)
    online = true
    Thread.sleep(10)
    online = false
    Thread.sleep(10)
    
    val outString = str.getBuffer().toString()
    outString.contains("kein Zugang") should be(true)
    outString.contains("Netzzugang") should be(true)
    outString.length() should be (33 + 32 +33) // Nachgezählt (für genau 3 Messages!)
  }

  def withTimer(timedCode: => Unit) = {
    val start = System.currentTimeMillis()
    timedCode
    val stop = System.currentTimeMillis()
    stop - start
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