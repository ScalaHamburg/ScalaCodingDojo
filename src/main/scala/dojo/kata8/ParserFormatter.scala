package dojo.kata8

import scala.util.parsing.combinator.JavaTokenParsers

trait ParserFormatter extends JavaTokenParsers{
	def root:Parser[Any]
	
	def prettyPrint(p: Any, level: Int): String
}