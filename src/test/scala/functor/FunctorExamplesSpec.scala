package functor

import cats.Id
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class FunctorExamplesSpec
  extends AnyFunSpec
  with Matchers {

  private def isOdd(i: Int): Boolean = i % 2 == 1

  describe("Functor") {
    describe("map") {
      it("apply given function for each element of List") {
        import cats.Functor
        import cats.implicits._

        Functor[List].map(List(2, 3, 4))(isOdd) mustBe List(false, true, false)
        Functor[Option].map(Option(42))(isOdd) mustBe Option(false)

        val myId: Id[Int] = 42
        Functor[Id].map(myId)(isOdd) mustBe false
      }
    }

    describe("derived methods") {
      it("can be called directly when Functor syntax is imported") {
        import cats.syntax.functor._
        import cats.implicits.catsStdInstancesForList

        List(2, 3, 4).void mustBe List((), (), ())
        List(2, 3, 4).as("foo") mustBe List("foo", "foo", "foo")
        List(2, 3, 4).fproduct(isOdd) mustBe List((2, false), (3, true), (4, false))
      }

      it("for Vector") {
        import cats.syntax.functor._
        import cats.implicits.catsStdInstancesForVector

        Vector(2, 3, 4).void mustBe Vector((), (), ())
        Vector(2, 3, 4).as("foo") mustBe Vector("foo", "foo", "foo")
        Vector(2, 3, 4).fproduct(isOdd) mustBe Vector((2, false), (3, true), (4, false))
      }

      it("for Option") {
        import cats.syntax.functor._
        import cats.implicits.catsStdInstancesForOption

        Option(42).void mustBe Option(())
        Option(42).as("foo") mustBe Option("foo")
        Option(42).fproduct(isOdd) mustBe Option((42, false))
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

      it("can chain multiple map 2") {
        import cats.Functor
        import cats.implicits.catsStdInstancesForList
        import cats.implicits.catsStdInstancesForOption

        val listOption = Functor[List] compose Functor[Option]
        import listOption.map
        map(List(Some(42), Some(15), None))(isOdd) mustBe List(Some(false), Some(true), None)
      }
    }
  }
}
