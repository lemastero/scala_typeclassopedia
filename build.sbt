name := "scala_typeclassopedia"

version := "0.0.1"

scalaVersion := "2.12.6"

val catsVersion = "1.1.0"

mainClass := Some("coyoneda.SimpleImpl")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion withSources() withJavadoc(),
  "org.typelevel" %% "cats-free" % catsVersion withSources() withJavadoc(),
  "org.typelevel" %% "cats-mtl-core" % "0.3.0" withSources() withJavadoc(),
  "org.scalaz" %% "scalaz-core" % "7.3.0-M25",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)

addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.1" cross CrossVersion.full)
addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.7")