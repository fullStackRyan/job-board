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

    def postAJob(req: Request[IO]): IO[Response[IO]] =
      req
        .decode[JobPost] { post =>
          jobPostRepo.createPost(post).flatMap(id => Created(id))
        }
        .handleErrorWith(e => BadRequest(e.getMessage))

    def updateJobPost(req: Request[IO]): IO[Response[IO]] =
      req
        .decode[JobPost] { post =>
          jobPostRepo.updatePost(post).flatMap(_ => Accepted())
        }
        .handleErrorWith(e => BadRequest(e.getMessage))

    def getAllJobPosts: IO[Response[IO]] =
      jobPostRepo.searchPost().flatMap { posts =>
        Ok(posts)
      }

    def getPostById(id: String): IO[Response[IO]] =
      jobPostRepo.getPost(id) flatMap {
        case None       => NotFound()
        case Some(post) => Ok(post)
      }

    HttpRoutes.of[IO] {
      case req@ POST -> Root / "posts"  => postAJob(req)
      case req@ PUT -> Root / "posts"   => updateJobPost(req)
      case _@GET -> Root / "posts"      => getAllJobPosts
      case _@GET -> Root / "posts" / id => getPostById(id)
      case _@GET -> Root / "ping"     => Ok("ping")
    }

  }

}
