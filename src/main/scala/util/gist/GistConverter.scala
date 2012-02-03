package util.gist

import java.net.URL
import java.io.InputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import scala.annotation.tailrec
import scala.io.Source

/**
 * Der GistConverter nimmt eine Github-Gist-embed-Url entgegen und läd die dortige *.js Datei zur weiteren Verarbeitung.
 * Die *.js schreibt normalerweise per document write-Anweisungen das HTML des Gist in eine Div der aufrufenden Seite.
 * Da unsere Blog-policy dies verbietet, expandieren wir das HTML in eine (ugly) Form, die von unseren Blogs akzeptiert wird.
 */
class GistConverter(url: String) {
  def convert: String = {
    val strb = new StringBuilder()
    val site = new URL(url)
    val stream = null.asInstanceOf[InputStream]

    using(Source.fromInputStream(site.openStream())) {
      (
        source =>
          source.getLines().foreach {
            (
              line =>
                // remove all the document-write-stuff and keep the pure HTML:
                strb.append(replaceClasses(removeDocWriteStuff(line))))
          })

    }
    strb.toString()
  }

  /**
   * gist.js kommt mit 'seltsamen' escape-Sonderzeichen, die hier
   * zusammen mit den docWrite-Anweisungen entfernt werden
   */
  private def removeDocWriteStuff(line: String): java.lang.String = {
    line.replace("document.write('", "").
      replace("')", "").
      replace("\\n", "").
      replace("\\\"", "\"").
      replace("\\/", "/").
      replace("\\'", "'").
      replace("\\\\", "\\")
  }

  /**
   * Das erzeugte HTML referenziert ein entfernt geladenes Stylesheet, was
   * (und dreimal dürft ihr raten) auch nicht akzeptiert wird.
   * An dieser Stelle werden die Class-definitionen hemdsärmelig gegen
   * Style-Deklarationen ausgetauscht.
   * Definitiv verbesserungswürdig!
   */
  @tailrec
  private def replaceClasses(line: String): String = {
    val start = line.indexOf("class=\"")
    if (start >= 0) {
      val end = line.indexOf("\"", start + 8)
      val css = line.substring(start + 7, end).split(" ")
      
      val cssStyle = css.map(Css.getCss(_)).mkString("")

      val newline = line.substring(0, start) + "style=\"" + cssStyle + line.substring(end)
      replaceClasses(newline)
    } else {
      line
    }
  }

  /**
   * Auto-Close Pattern from http://stackoverflow.com/questions/2207425/what-automatic-resource-management-alternatives-exists-for-scala
   */
  def using[T <: { def close() }](resource: T)(block: T => Unit) {
    try {
      block(resource)
    } finally {
      if (resource != null) resource.close()
    }
  }

}

/**
 * Test-Main mit Beispiel-Gist
 */
object GistConverter {
  def main(args: Array[String]) {
    //  	val conv = new GistConverter("https://gist.github.com/1678168.js?file=euler001b.scala")
    val conv = new GistConverter("https://gist.github.com/1648410.js")
    println(conv.convert)
  }
}

