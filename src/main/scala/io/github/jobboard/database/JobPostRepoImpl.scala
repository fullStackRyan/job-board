package io.github.jobboard.database

import cats.effect.IO
import doobie.implicits._
import doobie.util.transactor.Transactor
import io.github.jobboard.model.{JobPost, JobPostDetails}

class JobPostRepoImpl(xa: Transactor[IO]) extends JobPostRepo {
  override def createPost(jobPost: JobPost): IO[String] = {
    for {
      i <- JobQueries.insert(jobPost).run.transact(xa)
      s <- IO {
        if (i == 1) jobPost.id else "-1"
      }
    } yield s
  }

  override def updatePost(jobPost: JobPost): IO[Int] = {
    JobQueries.update(jobPost).run.transact(xa)
  }

  override def getPost(id: String): IO[Option[JobPost]] = {
    JobQueries.searchWithId(id).option.transact(xa)
  }

  def getPosts: IO[List[JobPost]] = {
    JobQueries.getAll.to[List].transact(xa)
  }

  def searchPost(): IO[List[JobPost]] = JobQueries.search().to[List].transact(xa)


}
