package io.github.jobboard.database

import cats.effect.IO
import io.github.jobboard.model.{JobPost, JobPostDetails}

trait JobPostRepo {
  def createPost(jobPost: JobPost): IO[String]

  def updatePost(id: String, details: JobPostDetails): IO[Int]

  def getPost(id: String): IO[Option[JobPost]]

  //  def getPosts: IO[List[JobPost]]
}
