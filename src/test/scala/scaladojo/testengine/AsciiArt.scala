package scaladojo.testengine

trait AsciiArt {

  def printHeading(number: Int) {
    var heading = splitIn5Lines(kata)
    splitNumber(number).foreach {
      n =>
        heading = appendLines(heading, splitIn5Lines(numbers(n)))
    }
    println("/****************************************************************************/")
    heading.foreach(println)
  }

  def splitNumber(number: Int) = {
    var numbers = List[Int]()
    var rest: Int = number
    do {
      var ziffer = rest % 10
      rest = rest / 10
      numbers = ziffer :: numbers
    } while (rest > 0)
    numbers
  }

  def printSuccess(text: String): Unit = {
    println(text)
    println(""" 
        __
       / /
__    / /
\ \  / /   SUCCESSFUL!
 \ \/ /
  \__/""")
  }

  def printFail(text: String): Unit = {
    println(text)
    println("""
__    __
\ \  / /
 \ \/ /
  >  <     ! FAILED !
 / /\ \
/_/  \_\""")
  }

  def splitIn5Lines(text: String) = {
    splitInLines(text, text.length / 5)
  }

  def splitInLines(text: String, lineLength: Int) = {
    assert(text.length % lineLength == 0)
    var lines = List[String]()
    val lineCount = text.length / lineLength
    for (i <- 0 until lineCount) {
      val line = text.substring(i * lineLength, i * lineLength + lineLength)
      lines = line :: lines
    }
    lines.reverse
  }

  def appendLines(textA: List[String], textB: List[String]) = {
    assert(textA.length == textB.length)
    textA.zip(textB).map(x => x._1 + x._2)
  }
  
  val kata = "    __ __       __        " +
    					 "   / //_/____ _/ /_____ _ " +
    					 "  / ,<  / __ `/ __/ __ `/ " +
    					 " / /| |/ /_/ / /_/ /_/ /  " +
    				 """/_/ |_|\__,_/\__/\__,_/   """
    
    val numbers = Array[String](
    		"   ____ " +
 				"  / __ \\" +
 				" / / / /" +
 				"/ /_/ / " +
    		"\\____/  ",
        "   ___ " +
    		"  <  / " +
    		"  / /  " +
    		" / /   " +
    		"/_/    ", 
    		"   ___ " +
    		"  |__ \\" +
    		"  __/ /" +
    		" / __/ " +
    		"/____/ ",
    		"   _____" +
    		"  |__  /" +
    		"   /_ < " +
    		" ___/ / " +
    		"/____/  ",
    		"   __ __" +
    		"  / // /" +
    		" / // /_" +
    		"/__  __/" +
    		"  /_/   ", 
    		"    ______" +
    		"   / ____/" +
    		"  /___ \\  " +
    		" ____/ /  " +
    		"/_____/   ",
    		"   _____" +
    		"  / ___/" +
    		" / __ \\ " +
    		"/ /_/ / " +
    		"\\____/  ",
    		" _____" +
    		"/__  /" +
    		"  / / " +
    		" / /  " +
    		"/_/   ",
    		"   ____ " +
    		"  ( __ )" +
    		" / __  |" +
    		"/ /_/ / " +
    		"\\____/  ", 
    		"   ____ " +
    		"  / __ \\" +
    		" / /_/ /" +
    		" \\__, / " +
    		"/____/  ")

}