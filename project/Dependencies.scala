import sbt._

object Dependencies {

  lazy val doobieVersion = "0.9.0"
  lazy val http4sVersion = "0.21.6"
  lazy val circeVersion  = "0.13.0"
  lazy val pureconfig = "0.13.0"
  lazy val slf4j = "1.7.5"
  lazy val logback = "1.0.9"
  lazy val flywayCore = "6.5.5"
  lazy val catsMTL = "0.7.0"
  lazy val scalactic = "3.2.0"

  val compileDependencies: Seq[ModuleID] = Seq(
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
    "com.github.pureconfig" %% "pureconfig"            % pureconfig,
    "org.slf4j"              % "slf4j-api"             % slf4j,
    "ch.qos.logback"         % "logback-classic"       % logback,
    "org.flywaydb"           % "flyway-core"           % flywayCore,
    "org.typelevel"         %% "cats-mtl-core"         % catsMTL
  )

  val testDependencies: Seq[ModuleID] = Seq(
    "org.scalactic"         %% "scalactic"             % scalactic,
    "org.scalatest"         %% "scalatest"             % scalactic % "test"
  )

  def apply(): Seq[ModuleID] = compileDependencies ++ testDependencies
}
