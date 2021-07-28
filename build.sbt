name := "scala_typeclassopedia"

version := "0.0.1"

lazy val scala212 = "2.12.14"
lazy val scala213 = "2.13.6"

scalaVersion := scala213

crossScalaVersions := List(scala212, scala213)

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val catsVersion = "2.6.1"
lazy val catsMtlVersion = "1.2.1"
lazy val scalaTestPlusVersion = "3.1.0.0-RC2"
lazy val scalacheckVersion = "1.15.4"
libraryDependencies ++= Seq(
  // cats
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "cats-free" % catsVersion,
  "org.typelevel" %% "cats-laws" % catsVersion,
  "org.typelevel" %% "alleycats-core" % catsVersion,
  "org.typelevel" %% "cats-mtl" % catsMtlVersion,
  "org.typelevel" %% "cats-mtl-laws" % catsMtlVersion,
  "org.typelevel" %% "cats-effect" % "3.2.0",

  "io.monix" %% "monix" % "3.4.0",

  // Scalaz
  "org.scalaz"    %% "scalaz-core" % "7.3.4",

  // ZIO
  "dev.zio" %% "zio-prelude"  % "1.0.0-RC5",
  "dev.zio" %% "zio" % "1.0.10",

  // algebra
  "org.typelevel" %% "algebra" % "2.2.3",
  "com.twitter" %% "algebird-core" % "0.13.8",

  // type level
  "com.codecommit" %% "skolems" % "0.2.1",
  "com.chuusai" %% "shapeless" % "2.3.7",

  // tofu
  "ru.tinkoff" %% "tofu" % "0.9.2",

  // izumi
  "io.7mind.izumi" %% "fundamentals-bio" % "1.0.8",

  // HoTT in Scala
  "io.github.siddhartha-gadgil" %% "provingground-core-jvm" % "0.1.1",

  // test
  "org.scalacheck" %% "scalacheck" % scalacheckVersion % Test,
  "org.scalatestplus" %% "scalatestplus-scalacheck" % scalaTestPlusVersion % Test,
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.5" % Test,

  "org.scalatest" %% "scalatest" % "3.2.9" % Test,

  "org.typelevel" %% "discipline-core" % "1.1.5" % Test,
  "org.typelevel" %% "discipline-scalatest" % "2.1.5" % Test
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.0" cross CrossVersion.full)