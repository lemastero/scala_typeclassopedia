package functor.bifunctor

import org.scalatest.{FunSpec, MustMatchers}

class BifunctorExamplesSpec
  extends FunSpec
  with MustMatchers {

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

    // why there is no fightMap in cats :(
  }
}
