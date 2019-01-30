import Dependencies._

name := "tradesys-engine-analytics"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= spark :+ scallop :+ typeSafeConfig

scalaSource in Compile := baseDirectory.value / "spark/batch/src/main/scala"
unmanagedSourceDirectories in Compile += baseDirectory.value / "spark/common/src/main/scala"
unmanagedSourceDirectories in Compile += baseDirectory.value / "spark/streaming/src/main/scala"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}