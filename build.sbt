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
  "org.typelevel" %% "cats-core" % catsVersion withSources(),
  "org.typelevel" %% "cats-free" % catsVersion withSources(),
  "org.typelevel" %% "cats-laws" % catsVersion withSources(),
  "org.typelevel" %% "alleycats-core" % catsVersion withSources(),
  "org.typelevel" %% "cats-mtl" % catsMtlVersion withSources(),
  "org.typelevel" %% "cats-mtl-laws" % catsMtlVersion withSources(),
  "org.typelevel" %% "cats-effect" % "3.1.1" withSources(),

  // Scalaz
  "org.scalaz"    %% "scalaz-core" % "7.3.4" withSources(),

  // ZIO
  "dev.zio" %% "zio-prelude"  % "1.0.0-RC5" withSources(),
  "dev.zio" %% "zio" % "1.0.9" withSources(),

  // algebra
  "org.typelevel" %% "algebra" % "2.2.3" withSources(),
  "com.twitter" %% "algebird-core" % "0.13.8" withSources(),

  // type level
  "com.codecommit" %% "skolems" % "0.2.1" withSources(),
  "com.chuusai" %% "shapeless" % "2.3.7" withSources(),

  // tofu
  "ru.tinkoff" %% "tofu" % "0.9.2" withSources(),

  // izumi
  "io.7mind.izumi" %% "fundamentals-bio" % "1.0.8" withSources(),

  // HoTT in Scala
  "io.github.siddhartha-gadgil" %% "provingground-core-jvm" % "0.1.1" withSources(),

  // test
  "org.scalacheck" %% "scalacheck" % scalacheckVersion % Test withSources(),
  "org.scalatestplus" %% "scalatestplus-scalacheck" % scalaTestPlusVersion % Test withSources(),
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.5" % Test withSources(),

  "org.scalatest" %% "scalatest" % "3.2.9" % Test withSources(),

  "org.typelevel" %% "discipline-core" % "1.1.5" % Test,
  "org.typelevel" %% "discipline-scalatest" % "2.1.5" % Test
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.0" cross CrossVersion.full)