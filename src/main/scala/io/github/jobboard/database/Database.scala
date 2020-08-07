package io.github.jobboard.database

import cats.effect.IO
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import doobie.hikari.HikariTransactor
import doobie.implicits._
import doobie.util.transactor.Transactor
import io.github.jobboard.config.DbConfig

object Database {

  def transactor(dbConfig: DbConfig): IO[HikariTransactor[IO]] = {
    val config = new HikariConfig()

    config.setJdbcUrl(dbConfig.url)
    config.setUsername(dbConfig.username)
    config.setPassword(dbConfig.password)
    config.setMaximumPoolSize(dbConfig.poolSize)

    val transactor: IO[HikariTransactor[IO]] = IO.pure(HikariTransactor.apply[IO](new HikariDataSource(config)))
    transactor
  }

  def bootstrap(xa:Transactor[IO]): IO[Int] = {
    JobQueries.createTable.run.transact(xa)
  }

}
