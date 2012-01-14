package dojo.kata9
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

/**
 * Diesmal ist der Test für eine gegebene Klasse zu erstellen.
 * Bitte nicht von der Abstrakten Testklasse ableiten.
 */
class NetzwerkScannerTest extends FunSuite with ShouldMatchers{

  test("erster Versuch"){
    new NetzwerkScanner{
      override def ping(host:String, port:Integer)={Some(123)}
    }
    
  }
}