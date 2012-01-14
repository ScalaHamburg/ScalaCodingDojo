package dojo.kata9
import java.net.InetAddress
import java.net.Socket

object SimplePing {
  def main(args: Array[String]) {
      val host = "www.google.de"
      val tm = ping(host, 80);
      tm match {
        case Some(t) => println("Connection ok (port " + 80 + ", time = " + tm + " ms). \n" + "Host Name    = " + host)
        case _ => println("Connection lost")
      }
  }

  /**
   * kein richtiges Ping, vielmehr ein Test einer Socketverbindung eines beliebigen Ports.
   */
  def ping(hostName: String, port: Integer): Option[Long] = {
    import java.net._
    try {
      val host = InetAddress.getByName(hostName);
      var tm = System.nanoTime();
      // blockierender Aufbau einer Socketverbindung
      val so = openSocket(host, port);
      so.close();
      Some((System.nanoTime() - tm) / 1000000L)
    } catch {
      case ex @(_: UnknownHostException | _:ConnectException | _:NoRouteToHostException) => {
        None
      }
    }
  }
  protected def openSocket(host: InetAddress, port: Integer): Socket = {
    new Socket(host, port);
  }
}
