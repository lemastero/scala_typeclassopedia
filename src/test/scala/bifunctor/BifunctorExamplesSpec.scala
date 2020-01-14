package bifunctor

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class BifunctorExamplesSpec
  extends AnyFunSpec
  with Matchers {

  describe("Bifunctor") {
    describe("bimap") {
      it("apply given functions for first and second element of tuple") {
        import cats.implicits._
        (List("foo", "bar"), 42).bimap(_.headOption, _ - 1) mustBe(Some("foo"), 41)
      }
    }

    describe("leftmap") {
      it("apply given functions for first and second element of tuple") {
        import cats.implicits._
        (List("foo", "bar"), 42).leftMap(_.headOption) mustBe (Some("foo"), 42)
      }
    }
  }
}
