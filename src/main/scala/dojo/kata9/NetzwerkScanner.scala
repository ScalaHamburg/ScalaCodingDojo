package dojo.kata9

import scala.actors.Actor
import Actor._
import java.net.InetAddress
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.io.Writer
import java.io.PrintWriter

/**
 * Utility zum überprüfen der Internetverbindung
 */
object NetzwerkScanner {
  val LogFile = "c:/tmp/netzabdeckung.txt"
  val Second = 1000 // milliseconds
  val DefaultInterval = 3
  val TestedHost = "www.google.de"
  val TestedPort = 80

  def main(args: Array[String]) { // java.net.UnknownHostException
    val scanner = new NetzwerkScanner()
    println("Logging network access to " + LogFile)
    scanner.startQueries(DefaultInterval, new FileWriter(LogFile))
  }
}

// Refactoring1: Objekt in Klasse gewandelt
class NetzwerkScanner {
  import NetzwerkScanner._

  // Refactoring2: Import durch Delegate ersetzen.
  // import SimplePing.ping
  def ping(host: String, port: Integer) = { new SimplePing().ping(host, port) }

  def startQueries(interval: Int, out: Writer) = {
    logNetworkStatus(false)

    def logNetworkStatus(netAccess: Boolean): Boolean = {
      val format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
      if (netAccess) {
        out.write(format.format(new Date()) + ":" + "Netzzugang\r\n");
      } else {
        out.write(format.format(new Date()) + ":" + "kein Zugang\r\n");
      }
      netAccess
    }

    val timer = actor {
      var netAccess = false
      (
        while (true) {
          val reachable = ping(TestedHost, TestedPort).isDefined;
          if (reachable) {
            if (!netAccess) {
              netAccess = logNetworkStatus(true)
            }
          } else {
            if (netAccess) {
              netAccess = logNetworkStatus(false)
            }
          }

          out.flush()
          Thread.sleep(interval * Second) // interval seconds
        })
    }

  }
}