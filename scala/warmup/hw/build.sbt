lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.13.0"
    )),
    name := "hw"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test

//libraryDependencies += "org.scalaj" % "scalaj-http_2.13" % "2.4.2"
// https://www.scala-sbt.org/1.x/docs/Library-Dependencies.html#Getting+the+right+Scala+version+with

libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.2"