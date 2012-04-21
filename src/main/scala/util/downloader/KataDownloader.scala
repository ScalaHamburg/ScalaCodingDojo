package util.downloader
import java.net.URL
import scala.io.Source
import java.io.File
import java.io.FileWriter

object KataDownloader extends App {

  override def main(args: Array[String]) = {
    if (args.length < 1) {
      println("Bitte die Nummer der gewünschten Kata eingeben.")
    } else {
      val kataNumber = args(0).toInt
      val githubRoot = "https://raw.github.com/ScalaHamburg/ScalaCodingDojo/master/"

      val kataFile = "src/main/scala/dojo/kata" + kataNumber + "/Kata.scala"
      val testFile = "src/test/scala/dojo/kata" + kataNumber + "/KataTest.scala"
      val readmeFile = "src/main/scala/dojo/kata" + kataNumber + "/README"

      downloadFile(githubRoot, kataFile)
      downloadFile(githubRoot, testFile)
      showReadme(githubRoot, readmeFile, kataNumber)

    }
  }

  def showReadme(githubRoote: String, sourceFile: String, kataNumber:Int) {
    val url = new URL(githubRoote + sourceFile);
    val source = Source.fromInputStream(url.openStream())
    source.getLines().foreach(println)
    println
    println("Nachdem die Methoden ausprogrammiert sind, kann mit folgendem Befehl getestet werden ob alles richtig war:")
    println("testKata "+kataNumber)
  }
  
  def downloadFile(githubRoote: String, sourceFile: String) {
  	val Newline = System.getProperty("line.separator")
  			val url = new URL(githubRoote + sourceFile);
  	val source = Source.fromInputStream(url.openStream())
  			
  			new File(sourceFile).getParentFile().mkdirs()
  			val outFile = new FileWriter(sourceFile)
  	source.getLines().foreach {
  		line =>
  		outFile.write(line)
  		outFile.write(Newline)
  	}
  	
  	outFile.close()
  }

}