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
    val timeout = 100

    val socket = mock[Socket]

    // 'injecten' der openSocket-dummy-methode
    val myPing = new SimplePing {
      override def openSocket(hostname: String, port: Int): Socket = {
        println("Opening Mock Socket")
        Thread.sleep(timeout)
        socket
      }
    }
    
    val result = myPing.ping("anyHost", 0).get.toInt

    // überprüfen, daß die 'close'-Methode auch aufgerufen wurde.
    verify(socket).close()
    
    result should (be >= 100 and be <= 150)
  }


  test("All Exceptions") {
  	val exceptions =  Table("exception", new UnknownHostException(), new ConnectException(), new NoRouteToHostException())
    forAll(exceptions) { ex: Exception =>

      val myPing = new SimplePing {
        override def openSocket(hostname: String, port: Int): Socket = throw ex
      }

      val result = myPing.ping("anyHost", 0)

      result should be(None)
    }
  }

  test("Unknown Exception") {
  	
  	val myPing = new SimplePing {
  		override def openSocket(hostname: String, port: Int): Socket = throw new IndexOutOfBoundsException()
  	}
  	
  	val x = intercept[IndexOutOfBoundsException] {
  		val result = myPing.ping("anyHost", 0)
  		result should be(None)
  	}
  	x.getMessage()//...
  }

  
}