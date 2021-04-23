name := "scala_typeclassopedia"

version := "0.0.1"

lazy val scala212 = "2.12.13"
lazy val scala213 = "2.13.3"

scalaVersion := scala213

crossScalaVersions := List(scala212, scala213)

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val catsVersion = "2.5.0"
lazy val catsMtlVersion = "1.1.3"
lazy val scalaTestPlusVersion = "3.1.0.0-RC2"
lazy val scalacheckVersion = "1.15.3"
lazy val silencerVersion = "1.7.1"
libraryDependencies ++= Seq(
  // cats
  "org.typelevel" %% "cats-core" % catsVersion withSources(),
  "org.typelevel" %% "cats-free" % catsVersion withSources(),
  "org.typelevel" %% "cats-laws" % catsVersion withSources(),
  "org.typelevel" %% "alleycats-core" % catsVersion withSources(),
  "org.typelevel" %% "cats-mtl" % catsMtlVersion withSources(),
  "org.typelevel" %% "cats-mtl-laws" % catsMtlVersion withSources(),
  "org.typelevel" %% "cats-effect" % "3.0.2" withSources(),

  // Scalaz
  "org.scalaz"    %% "scalaz-core" % "7.3.3" withSources(),

  // ZIO
  "dev.zio" %% "zio-prelude"  % "1.0.0-RC3" withSources(),
  "dev.zio" %% "zio" % "1.0.7" withSources(),

  // algebra
  "org.typelevel" %% "algebra" % "2.2.2" withSources(),
  "com.twitter" %% "algebird-core" % "0.13.7" withSources(),

  // type level
  "com.codecommit" %% "skolems" % "0.2.1" withSources(),
  "com.chuusai" %% "shapeless" % "2.3.4" withSources(),

  // tofu
  "ru.tinkoff" %% "tofu" % "0.9.2" withSources(),

  // izumi
  "io.7mind.izumi" %% "fundamentals-bio" % "1.0.5" withSources(),

  // HoTT in Scala
  "io.github.siddhartha-gadgil" %% "provingground-core-jvm" % "0.1.1" withSources(),

  compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
  "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full,

  // test
  "org.scalacheck" %% "scalacheck" % scalacheckVersion % Test withSources(),
  "org.scalatestplus" %% "scalatestplus-scalacheck" % scalaTestPlusVersion % Test withSources(),
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.5" % Test withSources(),

  "org.scalatest" %% "scalatest" % "3.2.7" % Test withSources(),

  "org.typelevel" %% "discipline-core" % "1.1.4" % Test,
  "org.typelevel" %% "discipline-scalatest" % "2.1.3" % Test
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.3" cross CrossVersion.full)