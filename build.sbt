name := "scala_typeclassopedia"

version := "0.0.1"

scalaVersion := "2.12.6"

mainClass := Some("coyoneda.SimpleImpl")

val catsVersion = "1.6.1"
libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion withSources() withJavadoc(),
  "org.typelevel" %% "cats-free" % catsVersion withSources() withJavadoc(),
  "org.typelevel" %% "cats-laws" % catsVersion withSources() withJavadoc(),
  "org.typelevel" %% "alleycats-core" % catsVersion withSources() withJavadoc(),

  "org.typelevel" %% "cats-mtl-core" % "0.6.0" withSources() withJavadoc(),
  "org.typelevel" %% "cats-mtl-laws" % "0.6.0" withSources() withJavadoc(),

  "org.typelevel" %% "cats-effect" % "1.4.0" withSources() withJavadoc(),

  "org.scalaz"    %% "scalaz-core" % "7.3.0-M31" withSources() withJavadoc(),

  "com.slamdata" %% "matryoshka-core" % "0.21.3" withSources() withJavadoc(),

  "org.scalatest" %% "scalatest" % "3.0.8" % Test
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)

addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.1" cross CrossVersion.full)
addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.0")