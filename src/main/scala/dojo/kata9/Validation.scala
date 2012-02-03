package dojo.kata9

class Validation(var success: Boolean) {
  def and(validation: Validation): Validation = {
    new Validation(success && validation.success)
  }
  def onSuccess(fun: => Unit) {
    if (success) {
      fun
    }
  }
}
object Validation {
  
  def validate(f: => Boolean)(onFailure: => Unit): Validation = {
    if (!f) {
      onFailure
      new Validation(false)
    } else {
      new Validation(true)
    }
  }
}

object ValidatorMain{
  import Validation._
  
	def main(args: Array[String]) {
		val s = "ja"
			val t = "name"
				
				validate(s == "ja") {
			println("s war nicht 'ja'")
		} and validate(t == "name") {
			println("t war nicht 'name'")
		} onSuccess {
			println("Ein voller Erfolg!")
		}
		
//    val part = validate(s == "jaX")_
//    val applied = part.apply {
//      println("geht nicht!")
//    }
//    applied.onSuccess {
//      println("sonstwas")
//    }
	}
}