name := "job-board"

version := "1.0"

scalaVersion := "2.13.2"

libraryDependencies ++= {

  lazy val doobieVersion = "0.9.0"
  lazy val http4sVersion = "0.21.6"
  lazy val circeVersion  = "0.13.0"

  Seq(
    "org.tpolecat"          %% "doobie-postgres"       % doobieVersion,
    "org.tpolecat"          %% "doobie-postgres-circe" % doobieVersion,
    "org.tpolecat"          %% "doobie-core"           % doobieVersion,
    "org.tpolecat"          %% "doobie-h2"             % doobieVersion,
    "org.tpolecat"          %% "doobie-hikari"         % doobieVersion,
    "org.tpolecat"          %% "doobie-specs2"         % doobieVersion,
    "org.http4s"            %% "http4s-blaze-server"   % http4sVersion,
    "org.http4s"            %% "http4s-circe"          % http4sVersion,
    "org.http4s"            %% "http4s-dsl"            % http4sVersion,
    "io.circe"              %% "circe-core"            % circeVersion,
    "io.circe"              %% "circe-generic"         % circeVersion,
    "io.circe"              %% "circe-parser"          % circeVersion,
    "io.circe"              %% "circe-literal"         % circeVersion,
    "com.github.pureconfig" %% "pureconfig"            % "0.13.0",
    "org.slf4j"              % "slf4j-api"             % "1.7.5",
    "ch.qos.logback"         % "logback-classic"       % "1.0.9",
    "org.flywaydb"           % "flyway-core"           % "6.5.5",
    "org.typelevel"         %% "cats-mtl-core"         % "0.7.0",
    "org.scalactic"         %% "scalactic"             % "3.2.0",
    "org.scalatest"         %% "scalatest"             % "3.2.0" % "test"
  )

}

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)
