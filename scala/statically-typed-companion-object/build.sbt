ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "statically-typed-companion-object",
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")
  ).dependsOn(macros)

lazy val macros = Project("macros", file("macros")).settings(
  name := "statically-typed-companion-object-macro",
  libraryDependencies := Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value % "compile"
  ),
  scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")
)
