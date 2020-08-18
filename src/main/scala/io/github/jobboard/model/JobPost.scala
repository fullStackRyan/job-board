package io.github.jobboard.model

import cats.Bifunctor.ops.toAllBifunctorOps
import cats.Bitraverse.ops.toAllBitraverseOps
import cats.implicits.{catsStdBitraverseForEither, toBifunctorOps}
import doobie._
import doobie.postgres.circe.json.implicits._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import doobie.postgres.circe.json.implicits._
import doobie._
import io.circe.syntax._

case class JobPost(id: String, details: JobPostDetails)

case class JobPostDetails(title: String, description: String, salary: Double, employmentType: String, employer: String)



object JobPostDetails {

  //for decoding json into JobPostDetails
  implicit val circeDecoder: Decoder[JobPostDetails] =
    deriveDecoder[JobPostDetails]

  //for encoding JobPostDetails into json
  implicit val circeEncoder: Encoder[JobPostDetails] =
    deriveEncoder[JobPostDetails]

  //tells doobie to put JobPostDetails as json to details column
  implicit val put: Put[JobPostDetails] =
    Put[Json].contramap(_.asJson)

  //tells doobie how to read JobPostDetails from json column
//    implicit val get: Get[JobPostDetails] =
//      Get[Json].temap(_.as[JobPostDetails].leftMap(_.show))
}