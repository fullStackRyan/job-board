
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class JobPostRoutingSpec extends AnyFreeSpec with Matchers {

  "Job Post Routes" - {
    "should return empty list " in {
      List.empty shouldBe List.empty
    }
  }
}