name := "scala_typeclassopedia"

version := "0.0.1"

scalaVersion := "2.12.6"

mainClass := Some("coyoneda.SimpleImpl")

val catsVersion = "1.6.0"
libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion withSources() withJavadoc(),
  "org.typelevel" %% "cats-free" % catsVersion withSources() withJavadoc(),
  "org.typelevel" %% "cats-laws" % catsVersion withSources() withJavadoc(),
  "org.typelevel" %% "alleycats-core" % catsVersion withSources() withJavadoc(),

  "org.typelevel" %% "cats-mtl-core" % "0.5.0" withSources() withJavadoc(),
  "org.typelevel" %% "cats-mtl-laws" % "0.5.0" withSources() withJavadoc(),

  "org.typelevel" %% "cats-effect" % "1.2.0" withSources() withJavadoc(),

  "org.scalaz"    %% "scalaz-core" % "7.3.0-M27" withSources() withJavadoc(),

  "com.slamdata" %% "matryoshka-core" % "0.21.3" withSources() withJavadoc(),

  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)

addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.1" cross CrossVersion.full)
addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.9")