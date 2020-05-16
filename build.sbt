import sbt.librarymanagement

name := "scala_typeclassopedia"

version := "0.0.1"

lazy val scala212 = "2.12.10"
lazy val scala213 = "2.13.1"

scalaVersion := scala213

crossScalaVersions := List(scala212, scala213)

lazy val catsVersion = "2.1.1"
lazy val catsMtlVersion = "0.7.1"
libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion withSources(),
  "org.typelevel" %% "cats-free" % catsVersion withSources(),
  "org.typelevel" %% "cats-laws" % catsVersion withSources(),
  "org.typelevel" %% "alleycats-core" % catsVersion withSources(),
  "org.typelevel" %% "cats-mtl-core" % catsMtlVersion withSources(),
  "org.typelevel" %% "cats-mtl-laws" % catsMtlVersion withSources(),
  "org.typelevel" %% "cats-effect" % "2.1.3" withSources(),
  "org.scalaz"    %% "scalaz-core" % "7.3.0" withSources(),
  "com.codecommit" %% "skolems" % "0.2.0",
  "dev.zio" %% "zio" % "1.0.0-RC19" withSources(),
  "org.scalatest" %% "scalatest" % "3.1.1" % Test
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)