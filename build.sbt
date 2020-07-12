name := "scala_typeclassopedia"

version := "0.0.1"

lazy val scala212 = "2.12.10"
lazy val scala213 = "2.13.2"

scalaVersion := scala213

crossScalaVersions := List(scala212, scala213)

lazy val catsVersion = "2.1.1"
lazy val catsMtlVersion = "0.7.1"
lazy val scalaTestPlusVersion = "3.1.0.0-RC2"
lazy val scalacheckVersion = "1.14.3"
libraryDependencies ++= Seq(
  // cats
  "org.typelevel" %% "cats-core" % catsVersion withSources(),
  "org.typelevel" %% "cats-free" % catsVersion withSources(),
  "org.typelevel" %% "cats-laws" % catsVersion withSources(),
  "org.typelevel" %% "alleycats-core" % catsVersion withSources(),
  "org.typelevel" %% "cats-mtl-core" % catsMtlVersion withSources(),
  "org.typelevel" %% "cats-mtl-laws" % catsMtlVersion withSources(),
  "org.typelevel" %% "cats-effect" % "2.1.4" withSources(),

  // Scalaz
  "org.scalaz"    %% "scalaz-core" % "7.3.2" withSources(),

  // ZIO
  "dev.zio" %% "zio" % "1.0.0-RC21-2" withSources(),

  // algebra
  "org.typelevel" %% "algebra" % "2.0.1" withSources(),
  "com.twitter" %% "algebird-core" % "0.13.7" withSources(),

  // type level
  "com.codecommit" %% "skolems" % "0.2.1" withSources(),
  "com.chuusai" %% "shapeless" % "2.3.3" withSources(),

  // test
  "org.scalacheck" %% "scalacheck" % scalacheckVersion % Test withSources(),
  "org.scalatestplus" %% "scalatestplus-scalacheck" % scalaTestPlusVersion % Test withSources(),
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.5" % Test withSources(),

  "org.scalatest" %% "scalatest" % "3.2.0" % Test withSources(),

  "org.typelevel" %% "discipline-core" % "1.0.2" % Test,
  "org.typelevel" %% "discipline-scalatest" % "1.0.1" % Test
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)