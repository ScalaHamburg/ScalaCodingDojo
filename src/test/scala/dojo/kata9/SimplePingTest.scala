package dojo.kata9
import java.net.InetAddress
import java.net.Socket
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import javax.management.BetweenQueryExp
import java.net.UnknownHostException
import java.net.ConnectException
//import scala.dbc.statement.Table
//import scala.dbc.statement.Table
import org.scalatest.prop.TableDrivenPropertyChecks._
import java.net.NoRouteToHostException
import org.mockito.Mockito._

/**
 * Diesmal ist der Test für eine gegebene Klasse zu erstellen.
 * Bitte nicht von der Abstrakten Testklasse ableiten.
 */
@RunWith(classOf[JUnitRunner])
class SimplePingTest extends FunSuite with ShouldMatchers with MockitoSugar {

  test("timeout") {
    val timeout = 1000

    val socket = mock[Socket]

    val myPing = new SimplePing {
      override def openSocket(hostname: String, port: Integer): Socket = {
        println("Opening Mock Socket")
        Thread.sleep(timeout)
        socket
      }
    }
    
    val result = myPing.ping("anyHost", 0).get.toInt

    verify(socket).close()
    result should (be >= 1000 and be <= 2000)
  }

  val exceptions =  Table("exceptions", new UnknownHostException(), new ConnectException(), new NoRouteToHostException())

  test("All Exceptions") {
    forAll(exceptions) { ex: Exception =>

      val myPing = new SimplePing {
        override def openSocket(hostname: String, port: Integer): Socket = throw ex
      }

      val result = myPing.ping("anyHost", 0)

      result should be(None)
    }
  }

  
}