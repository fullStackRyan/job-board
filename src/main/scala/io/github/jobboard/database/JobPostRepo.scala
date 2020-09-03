package io.github.jobboard.database

import cats.effect.IO
import io.github.jobboard.model.{JobPost, JobPostDetails}

trait JobPostRepo {
  def createPost(jobPost: JobPost): IO[String]

  def updatePost(jobPost: JobPost): IO[Int]

  def getPost(id: String): IO[Option[JobPost]]

  def getPosts: IO[List[JobPost]]

  def searchPost(): IO[List[JobPost]]

//  def query[F[_], A](attempt: A): F[A]
//
}
