package dojo.kata9
import sun.applet.Main

class Validation(val success: Boolean) {

  def and(validation: Validation): Validation = {
    new Validation(validation.success && success)
  }
  def onSuccess(fun: => Unit) {
    if (success) {
      fun
    }
  }
}

object Validation {
  def main(args: Array[String]) {
    val s = "ja"
    val t = "name"
    val result = validate(s == "ja") { println("s war nicht 'ja'") } and
      validate(t == "name") { println("t war nicht 'name'") } onSuccess {
        println("Ein voller Erfolg!")
      }

  }

  def validate(f: => Boolean)(onFailure: => Unit): Validation = {
    if (!f) {
      onFailure
      new Validation(false)
    } else {
      new Validation(true)
    }
  }
}