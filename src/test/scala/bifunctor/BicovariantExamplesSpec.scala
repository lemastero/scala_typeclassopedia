package bifunctor

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers
import zio.prelude._

class BicovariantExamplesSpec extends AnyFunSpec with Matchers {

  describe("Bicovariant") {
    describe("bimap") {
      it("bimap can transform both sides of Either or Tuple") {
        val product: (Int, List[String]) = (42, List("foo", "bar"))
        product.bimap(_ - 1, _.headOption) mustBe(41, Some("foo"))

        val either: Either[Int, List[String]] = Right(List("foo", "bar"))
        either.bimap(_ - 1, _.headOption) mustBe Right(Some("foo"))
      }
    }

    describe("leftmap") {
      it("apply given functions for first and second element of tuple") {
        val product: (Int, List[String]) = (42, List("foo", "bar"))
        product.bimap(_ - 1, _.headOption) mustBe (41, Some("foo"))

        val either: Either[Int, List[String]] = Right(List("foo", "bar"))
        either.bimap(_ - 1, _.headOption) mustBe Right(Some("foo"))
      }
    }
  }
}
