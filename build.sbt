name := "ScalaCodingDojo"

version := "1.0"

scalaVersion := "2.9.1"

libraryDependencies += "org.scala-tools" % "scala-stm_2.9.1" % "0.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.7.1" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "com.novocode" % "junit-interface" % "0.7" % "test->default"

logBuffered in Test := false

publishArtifact in Test := true

parallelExecution in Test := false

seq(ScctPlugin.scctSettings: _*)

scalacOptions ++= Seq("-deprecation")

// Wenn als "langsam" getaggte Tests nie ausgeführt werden sollen, bitte die nachfolgende Zeile aktivieren

//testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-l", "scaladojo.SlowTest")

// Wenn als "langsam" getaggte Tests für die coverage nie ausgeführt werden sollen, bitte die nachfolgende Zeile auskommentieren

testOptions in CoverageTest += Tests.Argument(TestFrameworks.ScalaTest, "-l", "scaladojo.SlowTest")

