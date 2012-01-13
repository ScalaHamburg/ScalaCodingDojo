package dojo.kata9
import sun.applet.Main

class Validation(val success:Boolean) {
  
 def and(validation:Validation):Validation={
   new Validation(validation.success && success)
 }
}

object Validation{
    def main(args: Array[String]) {
    	val s = "nö"
    		val t = "namen"
      val result = validate(s == "ja") { println("s war nicht 'ja'") } and
      validate(t=="name"){ println("t war nicht 'name'") }
      println(result.success)
  	}
	
	def validate(f: => Boolean)(onFailure: => Unit):Validation = {
	  if(!f){
	    onFailure
	    new Validation(false)
	  }else{
	  	new Validation(true)
	  }
	}
}