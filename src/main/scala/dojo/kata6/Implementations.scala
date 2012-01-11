package dojo.kata6

import org.scalatest.prop.TableDrivenPropertyChecks._

object Implementations {
  def variants = {
    Table("euler23", ProjectEuler23PTR, ProjectEuler23 /*comma separated List of Implementations's*/)
  }
}