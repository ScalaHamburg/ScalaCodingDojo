package dojo.kata7

import org.scalatest.prop.TableDrivenPropertyChecks._

object Implementations {
  def parsers = {
    Table("parser", new JSONParser()/*comma separated List of ParserFormatter's*/)
  }
}