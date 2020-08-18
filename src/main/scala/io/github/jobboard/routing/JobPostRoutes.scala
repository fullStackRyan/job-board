package io.github.jobboard.routing

import cats.effect.IO
import io.circe.generic.auto._
import io.github.jobboard.database.JobPostRepo
import io.github.jobboard.model.{JobPost, JobPostDetails}
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

object JobPostRoutes {

  def routes(jobPostRepo: JobPostRepo): HttpRoutes[IO] = {
    val dsl = new Http4sDsl[IO] {}
    import dsl._

    HttpRoutes.of[IO] {
      case _@GET -> Root / "health" =>
        Ok("ok")
      case req@POST -> Root / "posts" =>
        req.decode[JobPost] { post =>
          jobPostRepo.createPost(post).flatMap(id => Created(id))
        }.handleErrorWith(e => BadRequest(e.getMessage))
      case req@PUT -> Root / "posts" / id =>
        req.decode[JobPostDetails] { post =>
          jobPostRepo.updatePost(id, post).flatMap(_ => Accepted())
        }.handleErrorWith(e => BadRequest(e.getMessage))
      case _@GET -> Root / "posts" =>
        jobPostRepo.getPosts.flatMap(posts => Ok(posts))
      case _@GET -> Root / "posts" / id =>
        jobPostRepo.getPost(id) flatMap {
          case None => NotFound()
          case Some(post) => Ok(post)
        }
    }

  }
}
