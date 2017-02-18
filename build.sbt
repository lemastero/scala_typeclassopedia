name := "learn_scala_cats"

version := "0.0.1"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.9.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)
