name := "scala-debugger-samples"

version := "1.0"

// Default version when not cross-compiling
scalaVersion := "2.10.5"

crossScalaVersions := Seq("2.10.5", "2.11.6")

libraryDependencies += "org.scala-debugger" %% "scala-debugger-api" % "1.0.0-SNAPSHOT"
