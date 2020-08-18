package io.github.jobboard.database

import cats.effect.{Blocker, ContextShift, IO, Resource}
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import doobie.hikari.HikariTransactor
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import io.github.jobboard.config.DbConfig

object Database {

  def transactor(dbConfig: DbConfig)(implicit csContextShift: ContextShift[IO]): Resource[IO, HikariTransactor[IO]] = {
    val config = new HikariConfig()
    config.setDriverClassName("org.postgresql.Driver")

    config.setJdbcUrl(dbConfig.url)
    config.setUsername(dbConfig.username)
    config.setPassword(dbConfig.password)
    config.setMaximumPoolSize(dbConfig.poolSize)

    val resources: Resource[IO, HikariTransactor[IO]] = for {
      cachedThreadPool <- ExecutionContexts.cachedThreadPool[IO]
      connectionThreadPool <- ExecutionContexts.fixedThreadPool[IO](10)
      transactor <- Resource.liftF(IO(HikariTransactor.apply[IO](new HikariDataSource(config), connectionThreadPool, Blocker.liftExecutionContext(cachedThreadPool))))
    } yield transactor

    resources
  }

  def bootstrap(xa: Transactor[IO]): IO[Int] = {
    JobQueries.createTable.run.transact(xa)
  }

}
