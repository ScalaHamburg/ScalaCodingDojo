package dojo.kata2

/*
 * If the numbers 1 to 5 are written out in words: one, two, three, four, five, then there 
 * are 3 + 3 + 5 + 4 + 4 = 19 letters used in total.
 * If all the numbers from 1 to 1000 (one thousand) inclusive were written out in words, 
 * how many letters would be used?
 * 
 * NOTE: Do not count spaces or hyphens. For example, 342 (three hundred and forty-two) 
 * contains 23 letters and 115 (one hundred and fifteen) contains 20 letters. The use of "and" when writing out numbers is in compliance with British usage.
 */

/**
 * Einfach nur die Methoden ausimplementieren und den Test starten (sbt test-only com.logica.scaladojo.kata1.NumberWordCountTest)
 */
object NumberWordCount {

  val first = Array("", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty")
  val second = Array("", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")

  def count(start: Int, end: Int) = {
    val numbers = start to end toList

    def sumLetters(numbers: List[Int], count: Int): Int = {
      var localLength = count
      if (numbers.isEmpty) {
        count
      } else {
        val current = numbers.head
        val others = numbers.tail
        if (current == 1000) {
          localLength += "onethousand".length()
          print(" onethousand")
        } else {
          val (hunderter, zehner, einer) = split(current)
          if (hunderter > 0) {
            localLength += (first(hunderter) + "hundred").length()
            print(" " + (first(hunderter) + " hundred"))
          }
          if (zehner > 1) {
            if (hunderter > 0) {
              localLength += "and".length()
              print(" and")
            }
            localLength += second(zehner).length()
            print(" " + second(zehner))
          } else {
            if (hunderter > 0 && (einer > 0 || zehner > 0)) {
              localLength += "and".length()
              print(" and")
            }
          }
          if (zehner == 1) {
            localLength += first(einer + 10).length
            print(" " + first(einer + 10))
          } else {
            localLength += first(einer).length
            print(" " + first(einer))
          }
        }
//        println
        sumLetters(numbers.tail, localLength)
      }
    }

    def split(number: Int) = {
      val hunderter = number / 100
      val zehner = (number - (hunderter * 100)) / 10
      val einer = (number - (hunderter * 100) - (zehner * 10))
      (hunderter, zehner, einer)
    }
    
   sumLetters(numbers, 0)
  }

  def countShort = {
    val OneToNine = Array("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val TenToNineteen = Array("ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")
    val TwentyToNinety = Array("twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")
    val Hundred = "hundred"
    val Thousand = "onethousand"
    val And = "and"

    // 1 - 9
    val length1to9 = (0 /: OneToNine)(_ + _.length)
    // 10 - 19
    val length10to19 = (0 /: TenToNineteen)(_ + _.length)
    // 20 - 99
    val length20to99 = (0 /: TwentyToNinety)(_ + _.length * 10 + length1to9)
    // 100 - 999
    val length100to999 = (0 /: OneToNine)((s, n) => s + (n.length + Hundred.length) * 100 + (99 * And.length) + length1to9 + length10to19 + length20to99)

    length1to9 + length10to19 + length20to99 + length100to999 + Thousand.length
  }
}

