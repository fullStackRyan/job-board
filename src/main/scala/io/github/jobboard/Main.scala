package io.github.jobboard

import cats.data.Kleisli
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import doobie.util.transactor.Transactor
import fs2.Stream
import io.github.jobboard.config.{Config, LoadConfig, ServerConfig}
import io.github.jobboard.database.{Database, JobPostRepoImpl, JobQueries}
import io.github.jobboard.routing.JobPostRoutes
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.{Request, Response}
import pureconfig.generic.auto._
import scala.concurrent.ExecutionContext.global

object Main extends IOApp {
  implicit val loadConfig: LoadConfig[IO, Config] = LoadConfig.apply

  def makeRouter(transactor: Transactor[IO]): Kleisli[IO, Request[IO], Response[IO]] = {
    Router[IO](
      "/api/v1" -> JobPostRoutes.routes(new JobPostRepoImpl(transactor))
    ).orNotFound
  }

  def serveStream(transactor: Transactor[IO], serverConfig: ServerConfig): Stream[IO, ExitCode] = {
    BlazeServerBuilder[IO](global)
      .bindHttp(serverConfig.port, serverConfig.host)
      .withHttpApp(makeRouter(transactor))
      .serve
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val stream = for {
      config <- Stream.eval(LoadConfig.load[IO, Config])
      xa <- Stream.resource(Database.transactor(config.dbConfig))
      _ <- Stream.eval(Database.bootstrap(xa))
      exitCode <- serveStream(xa, config.serverConfig)
    } yield exitCode

    stream.compile.drain.as(ExitCode.Success)
  }


}
