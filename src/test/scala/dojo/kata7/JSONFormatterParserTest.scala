package dojo.kata7

import org.junit.Test
import scaladojo.testengine.AbstractTest
import org.scalatest.prop.TableDrivenPropertyChecks._

class JSONFormatterParserTest extends AbstractTest {

  @Test
  def testParsers {
    forAll(Implementations.parsers) { parser: ParserFormatter =>
      {
        execute(7) {
          // parse
          val parser = new JSONParser()
          val parseResult = parser.parseAll(parser.root, inputString)
          println("parseResult: " + parseResult.toString())
val res = parseResult.get
          // format
          val formatter = parser //new JSONFormatter()
          val output = formatter.prettyPrint(parseResult, 0)
          println("JSON-Document: " + output)
          output should include("John Smith")

          // parse formatted again and compare
          val parseResult2 = parser.parseAll(parser.root, output)
          println("parseResult: " + parseResult2.toString())
          val output2 = formatter.prettyPrint(parseResult2, 0)
          output2 should equal(output)

          println("JSON-Document: " + output2)
        }
      }
    }
  }

  val inputString = """
	{
		"address book": {
			"name" : "John Smith",
			"address" : {
				"street" : "Louise-Schroeder-Weg",
				"city" : "L�beck",
				"zip" : 23558
			},
			"phone numbers" : [
				"0178 8866017",
				"04514795768"
			]
		}
	}
	  """
}