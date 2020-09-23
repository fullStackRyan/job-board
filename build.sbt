name := "job-board"

version := "1.0"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

enablePlugins(JavaAppPackaging)

mainClass in Compile := Some("io.github.jobboard.Main")

scalaVersion := "2.13.2"

libraryDependencies ++= Dependencies()

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)
