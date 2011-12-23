package dojo.kata7

import scala.util.parsing.combinator.JavaTokenParsers

class JSONParser extends JavaTokenParsers with ParserFormatter{
 
  private def obj: Parser[Map[String, Any]] = "{" ~> repsep(member, ",") <~ "}" ^^ (Map() ++ _)

  private def arr: Parser[List[Any]] = "[" ~> repsep(value, ",") <~ "]"

  private def member: Parser[(String, Any)] = stringLiteral ~ ":" ~ value ^^ { case name ~ ":" ~ value => (name, value) }

  def value: Parser[Any] = (
    obj | arr | stringLiteral | floatingPointNumber ^^ (_.toDouble) |
    "null" ^^ (x => null) |
    "true" ^^ (x => true) |
    "false" ^^ (x => false))

  def root = value
  private val space = "  "
  private def mkSpace(ident: Int) = Array.make(ident, space) mkString

  def prettyPrint(p: Any, level: Int): String = {
    p match {
      case Success(value, _) => prettyPrint(value, level)
      case m: Map[String, Any] => // type erasure, so we are sure that this is a map, but not of this types
        val stringList = for ((key, value) <- m) yield {
          mkSpace(level) + key + ":" + prettyPrint(value, level + 1)
        }
        "{\n" + stringList.mkString(",\n") + "\n" + mkSpace(level) + "}"
      case l: List[Any] => "[\n" + l.map(mkSpace(level) + prettyPrint(_, level + 1)).mkString(",\n") + "\n" + mkSpace(level) + "]"
      case (k, v) => mkSpace(level) + k + " : " + prettyPrint(v, level + 1)
      case s: String => s
      case d: Double => d.toString
      case null => "null"
      case true => "true"
      case false => "false"
      case _ => ""
    }
  }
}