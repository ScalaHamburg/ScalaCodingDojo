package dojo.kata9
import java.net.InetAddress
import java.net.Socket

class SimplePing() {
  /**
   * kein richtiges Ping, vielmehr ein Test einer Socketverbindung eines beliebigen Ports.
   */
  def ping(hostName: String, port: Integer): Option[Long] = {
    import java.net._
    try {
      var tm = System.nanoTime();
      // blockierender Aufbau einer Socketverbindung mit Timeout
      val so = openSocket(hostName, port);
      so.close();
      Some((System.nanoTime() - tm) / 1000000L)
    } catch {
      case ex @ (_: UnknownHostException | _: ConnectException | _: NoRouteToHostException) => {
        println(ex)
        None
      }
    }
  }
  protected def openSocket(hostName: String, port: Integer): Socket = {
    val host = InetAddress.getByName(hostName);
    println("Opening socket to " + host)
    new Socket(host, port);
  }
}

object SimplePing {
  def main(args: Array[String]) {
    val host = "www.google.de"

    val ping = new SimplePing()
    val tm = ping.ping(host, 80);
    tm match {
      case Some(t) => println("Connection ok (port " + 80 + ", time = " + tm + " ms). \n" + "Host Name    = " + host)
      case _ => println("Connection lost")
    }
  }
}
