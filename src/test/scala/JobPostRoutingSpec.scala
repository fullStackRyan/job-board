import io.circe.literal._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class JobPostRoutingSpec extends AnyFreeSpec with Matchers {

  "Job Post Routes" - {
    "should return empty list " in {
      List.empty shouldBe List.empty
    }

    "PUT endpoint should update a particular post" in {
      val json =
        json"""
                   {
        "id": "2254",
        "details": {
            "title": "Scala Unicorn",
            "description": "Must be a rockstar at coding!",
            "salary": 90.0,
            "employmentType": "permanent",
            "employer": "ITV"
        }
    }
            """

      val expected =


    }
  }
}
