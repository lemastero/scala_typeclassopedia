name := "scala_typeclassopedia"

version := "0.0.1"

lazy val scala212 = "2.12.20"
lazy val scala213 = "2.13.15"

scalaVersion := scala213

crossScalaVersions := List(scala212, scala213)

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val catsVersion = "2.12.0"
lazy val catsMtlVersion = "1.5.0"
lazy val scalaTestPlusVersion = "3.1.0.0-RC2"
lazy val scalacheckVersion = "1.18.1"
libraryDependencies ++= Seq(
  // cats
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "cats-free" % catsVersion,
  "org.typelevel" %% "cats-laws" % catsVersion,
  "org.typelevel" %% "alleycats-core" % catsVersion,
  "org.typelevel" %% "cats-mtl" % catsMtlVersion,
  "org.typelevel" %% "cats-mtl-laws" % catsMtlVersion,
  "org.typelevel" %% "cats-effect" % "3.5.6",

  "io.monix" %% "monix" % "3.4.1",

  // Scalaz
  "org.scalaz"    %% "scalaz-core" % "7.3.8",

  // ZIO
  "dev.zio" %% "zio" % "2.1.11",
  "dev.zio" %% "zio-prelude"  % "1.0.0-RC31",

  // algebra
  "org.typelevel" %% "algebra" % "2.12.0",
  "com.twitter" %% "algebird-core" % "0.13.10",

  // type level
  "com.codecommit" %% "skolems" % "0.2.1",
  "com.chuusai" %% "shapeless" % "2.3.12",

  // tofu
  "tf.tofu" %% "tofu" % "0.13.6",

  // izumi
  "io.7mind.izumi" %% "fundamentals-bio" % "1.2.15",

  // HoTT in Scala
  "io.github.siddhartha-gadgil" %% "provingground-core-jvm" % "0.1.1",

  // test
  "org.scalacheck" %% "scalacheck" % scalacheckVersion % Test,
  "org.scalatestplus" %% "scalatestplus-scalacheck" % scalaTestPlusVersion % Test,
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.15" % "1.3.0" % Test,

  "org.scalatest" %% "scalatest" % "3.2.19" % Test,

  "org.typelevel" %% "discipline-core" % "1.7.0" % Test,
  "org.typelevel" %% "discipline-scalatest" % "2.3.0" % Test
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.3" cross CrossVersion.full)