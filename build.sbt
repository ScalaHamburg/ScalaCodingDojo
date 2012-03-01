name := "ScalaCodingDojo"

version := "1.0"

scalaVersion := "2.9.1"

libraryDependencies += "org.scala-tools" % "scala-stm_2.9.1" % "0.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.6.1" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "com.novocode" % "junit-interface" % "0.7" % "test->default"

logBuffered in Test := false

parallelExecution in Test := false

seq(ScctPlugin.scctSettings: _*)

