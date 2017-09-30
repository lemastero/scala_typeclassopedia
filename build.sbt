name := "learn_scala_cats"

version := "0.0.1"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "0.9.0"  withSources() withJavadoc(),
  "org.typelevel" %% "cats-free" % "0.9.0"  withSources() withJavadoc(),
  "org.scalatest" %% "scalatest" % "3.0.4" % Test
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)