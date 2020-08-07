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

  override def updatePost(id: String, jobPostDetails: JobPostDetails): IO[Int] = {
    JobQueries.update(id, jobPostDetails).run.transact(xa)
  }

  override def getPost(id: String): IO[Option[JobPost]] = {
    JobQueries.searchWithId(id).option.transact(xa)
  }


}
