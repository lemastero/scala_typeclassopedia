import sbt.librarymanagement

name := "scala_typeclassopedia"

version := "0.0.1"

lazy val scala212 = "2.12.10"
lazy val scala213 = "2.13.1"

scalaVersion := scala213

crossScalaVersions := List(scala212, scala213)

lazy val catsVersion = "2.0.0"
lazy val catsMtlVersion = "0.7.0"
libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion withSources(),
  "org.typelevel" %% "cats-free" % catsVersion withSources(),
  "org.typelevel" %% "cats-laws" % catsVersion withSources(),
  "org.typelevel" %% "alleycats-core" % catsVersion withSources(),
  "org.typelevel" %% "cats-mtl-core" % catsMtlVersion withSources(),
  "org.typelevel" %% "cats-mtl-laws" % catsMtlVersion withSources(),
  "org.typelevel" %% "cats-effect" % "2.0.0" withSources(),
  "org.scalaz"    %% "scalaz-core" % "7.3.0-M31" withSources(),
  "com.codecommit" %% "skolems" % "0.1.2",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)