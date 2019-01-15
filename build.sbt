import Dependencies._

name := "tradesys-engine-analytics"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= spark

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}