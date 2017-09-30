package functor

import org.scalatest.{FunSpec, MustMatchers}

class FunctorExamplesSpec
  extends FunSpec
  with MustMatchers {

  private def isOdd(i: Int): Boolean = i % 2 == 1

  describe("Functor") {
    describe("map") {
      it("apply given function for each element of List") {
        import cats.Functor
        import cats.implicits.catsStdInstancesForList

        Functor[List].map(List(2, 3, 4))(isOdd) mustBe List(false, true, false)
      }
    }

    describe("derived methods") {
      it("can be called directly when Functor syntax is imported") {
        import cats.syntax.functor._
        import cats.implicits.catsStdInstancesForList

        List(2, 3, 4).void mustBe List((), (), ())
        List(2, 3, 4).as("foo") mustBe List.fill(3)("foo")
        List(2, 3, 4).fproduct(isOdd) mustBe List((2, false), (3, true), (4, false))
      }
    }

    describe("compose") {
      it("can chain multiple map") {
        import cats.Functor
        import cats.implicits.catsStdInstancesForList
        import cats.implicits.catsStdInstancesForOption

        val functor = Functor[List] compose Functor[Option]
        functor.map(List(Some(42), Some(15), None))(isOdd) mustBe List(Some(false), Some(true), None)
      }
    }
  }
}
