name := """scala-pools"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-pool2" % "2.4.2",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

initialCommands in console := """
  import bz.syntax.pool._
"""
