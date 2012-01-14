package dojo.kata9


class Validation(val success: Boolean) {
  def and(validation: Validation): Validation = {
    new Validation(success && validation.success )
  }
  def onSuccess(fun: => Unit) {
    if (success) {
      fun
    }
  }
}
object Validation {
  def main(args: Array[String]) {
    val s = "jaX"
    val t = "nameX"
    val result = 
      validate(s == "ja") { 
      	println("s war nicht 'ja'") 
      } and validate(t == "name") { 
        println("t war nicht 'name'") 
      } onSuccess {
        println("Ein voller Erfolg!")
      }
      
      val part = validate(s=="jaX")_
      val applied = part.apply{
        println("geht nicht!")
      }
      applied.onSuccess{
        println("sonstwas")
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