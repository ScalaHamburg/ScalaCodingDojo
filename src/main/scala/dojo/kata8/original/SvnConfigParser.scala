package dojo.kata6.original

import java.io.FileReader
//import scala.dbc.result.Tuple
import scala.util.parsing.combinator.JavaTokenParsers
import java.io.File
import java.io.FileWriter

object Permission extends Enumeration {
  val R = Value("r")
  val W = Value("w")
  val RW = Value("rw")
  val All = Value("*")
}

case class Alias(name: String)
case class AliasDefinition(alias: Alias, ldapDn: String)
case class Group(name: String)
case class GroupWildcard() extends Group("*")
case class GroupDefinition(group: Group, aliases: List[Alias])
case class GroupAuth(group: Group, permission: Permission.Value)
case class Repository(name: String, groupAuths: List[GroupAuth])
case class Header(comments: List[String])
case class SvnAuthz(header: Header, aliasesDefinitions: List[AliasDefinition], groupDefinitions: List[GroupDefinition], repositories: List[Repository])

class SvnConfigParser extends JavaTokenParsers {
  override val whiteSpace = """[ \t]+""".r

  def svnAuthz: Parser[SvnAuthz] = header ~ aliasdefs ~groupdefs ~ repositories ^^ { case h ~ a ~g~r => SvnAuthz(h, a, g, r) } //~groupdefs ~repositories

  def header: Parser[Header] = opt(eol) ~> rep(comment) <~opt(eol) ^^ (p => Header(p))
  def comment: Parser[String] = "#" ~> """.*""".r <~ eol

  def aliasdefs: Parser[List[AliasDefinition]] = "[aliases]" ~> eol ~> rep(aliasdef) <~ opt(eol)
  def aliasdef: Parser[AliasDefinition] = alias ~ "=" ~ ldapDn ~ eol ^^ { case a ~ "=" ~ l ~ e => AliasDefinition(a, l) }
  def alias: Parser[Alias] = nameToken ^^ (Alias(_))
  def ldapDn: Parser[String] = """.+""".r

  def groupdefs : Parser[List[GroupDefinition]] = opt(eol) ~> "[groups]" ~> eol ~> rep(groupdef) <~opt(eol)
  def groupdef : Parser[GroupDefinition] = group ~"=" ~ repsep(alias, ",") ~  eol ^^ { case n~"="~a ~e => GroupDefinition(n, a) }
  def group : Parser[Group] = nameToken ^^ ( Group(_) )

  def repositories : Parser[List[Repository]] = rep(repository) <~ opt(eol)
  def repository : Parser[Repository] = repodef ~ groupAuths ^^ { case r~g => Repository(r,g)}
  def repodef : Parser[String] = "[" ~> nameToken <~":/]" <~ eol
  def groupAuths : Parser[List[GroupAuth]] = rep(groupAuth)
  def groupAuth : Parser[GroupAuth] = (groupWildcard | ("@" ~> group)) ~ "=" ~ permission ~ eol ^^{ case g ~"=" ~ p ~ _ => GroupAuth(g, p)}
  def groupWildcard : Parser[Group] = "*" ^^( s => GroupWildcard())
  def permission : Parser[Permission.Value] = """rw|w|r|\*""".r ^^ ( token => Permission.withName(token) )

  def eol: Parser[Any] = """(\r?\n)+""".r
  def nameToken : Parser[String] = """[^=\[,\n:]+""".r
}

object SvnConfigParser extends SvnConfigParser {
  def main(args: Array[String]) {
    val reader = new FileReader("svn.authz")
    val pa = parseAll(svnAuthz, reader)
    println(pa)
    //println(parseAll(svnAuthz, input))


    val s1 = SvnAuthzSerializer.serializeSvnAuth(pa.get)
    println (s1)
    val pa2 = parseAll(svnAuthz, s1)
    val s2 = SvnAuthzSerializer.serializeSvnAuth(pa2.get)
    if (s1 == s2) println ("IDENTISCH")

  }

  val input = """# h

  [aliases]
ulli=uid=ulli,ou=People,dc=company-development,dc=com
heinz=uid=heinz,OU=People,DC=company-development,DC=com

[groups]
Autoflug-dev=berndi,heinz,kurt,schmiddi,horst,hubert
Autoflug-dev2=heinz,bernd,werner,saschi,todde,ulf
Autoflug-read=gitte,lars

[Autoflug:/]
@Autoflug-dev=rw
@Autoflug-read=r

[Sales:/]
@Sales-dev=rw
@Sales-read=r

"""

}


object SvnAuthzSerializer {
  val nl = "\n"
  def serializeSvnAuth(s : SvnAuthz) : String = {
    // done in the old functional style due to usage of mutable StringBuilder
    var sb : StringBuilder = new StringBuilder
    // header
    s.header.comments.foreach ( sb append "#" append _ append nl)
    sb append nl
    // aliases
    sb append "[aliases]" append nl
    s.aliasesDefinitions.foreach ( ad => sb append ad.alias.name append "=" append ad.ldapDn append nl)
    sb append nl
    // groups
    sb append "[groups]" append nl
    s.groupDefinitions.foreach( gd => sb append gd.group.name append "=" append gd.aliases.map( _.name).mkString(",") append nl)
    sb append nl
    // repositories
    s.repositories.foreach { r =>
      sb append "[" append r.name append ":/]" append nl
      r.groupAuths.foreach { ga =>
        ga.group match {
          case GroupWildcard() => sb append "*="
          case _ => sb append "@" append ga.group.name append "="
        }
        sb append ga.permission append nl
      }
      sb append nl
    }
    // return value
    sb.toString
  }

  def writeToFile(f : File, s : SvnAuthz) {
     val writer = new FileWriter(f)

     writer.write(serializeSvnAuth(s))
     writer.flush()
     writer.close()
  }
}

class JSONParser extends JavaTokenParsers {

  def obj: Parser[Map[String, Any]] = "{" ~> repsep(member, ",") <~ "}" ^^ (Map() ++ _)

  def arr: Parser[List[Any]] = "[" ~> repsep(value, ",") <~ "]"

  def member: Parser[(String, Any)] = stringLiteral ~ ":" ~ value ^^ { case name ~ ":" ~ value => (name, value) }

  def value: Parser[Any] = (
    obj | arr | stringLiteral | floatingPointNumber ^^ (_.toDouble) |
    "null" ^^ (x => null) |
    "true" ^^ (x => true) |
    "false" ^^ (x => false))

}

object JSONParser extends JSONParser {

  val inputString = """
	{
		"address book": {
			"name" : "John Smith",
			"address" : {
				"street" : "Aghate-Lasch-Weg",
				"city" : "Hamburg",
				"zip" : 22864
			},
			"phone numbers" : [
				"0176 1234567",
				"0402345678"
			]
		}
	}
	  """

  def main(args: Array[String]) {

    //val reader = new FileReader(args(0))
    val parseResult = parseAll(value, inputString)

    val sParseResult = parseResult.toString
    println(sParseResult)
    val pp = prettyPrint(parseResult, 0)
    println(pp)

    // and again
    val pp2 = prettyPrint(parseAll(value, pp), 0)
    println(pp2)
    if (pp2 == pp) { println("IDENTISCH") }

  }

  val space = "  "
  def mkSpace(ident: Int) = Array.make(ident, space) mkString

  def prettyPrint(p: Any, level: Int): String = {
    p match {
      case Success(value, _) => prettyPrint(value, level)
      case m: Map[String, Any] => // type erasure, so we are sure that this is a map, but not of this types
        val stringList = for (t <- m) yield {
          mkSpace(level) + t._1 + ":" + prettyPrint(t._2, level + 1)
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