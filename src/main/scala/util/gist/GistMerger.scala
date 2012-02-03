package util.gist
import scala.io.Source

/**
 * Dieser GistMerger liest als Eingabe eine Html-Seite mit eingebetteten "[gist: 1234567]" Marken.
 * Die Marken werden expandiert und das von Github geladene gist-Html an der Stelle eingebunden.
 * 
 * Die fertige HTML-Seite wird auf der Konsole ausgegeben.
 */
object GistMerger extends App {
  val InputFile = "util/gist/example.html"
 	val gistPattern = """\[gist:( )?([^\]]*)\]""".r.pattern
    
  val input = Source.fromInputStream(this.getClass().getClassLoader().getResourceAsStream(InputFile))
  input.getLines().foreach { line =>
    val matcher = gistPattern.matcher(line)
    if (matcher.matches()) {
      val gist = "https://gist.github.com/" + matcher.group(2) + ".js"
      val conv = new GistConverter(gist)
      val converted = conv.convert
      println(converted.substring(converted.indexOf("<div id=\"gist")))
    } else {
      println(line)
    }
  }
}