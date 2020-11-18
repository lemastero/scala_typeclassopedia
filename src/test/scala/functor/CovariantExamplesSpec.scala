package functor

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class CovariantExamplesSpec extends AnyFunSpec with Matchers {

  private def isOdd(i: Int): Boolean = i % 2 == 1

  describe("Functor") {
    describe("map") {
      it("apply given function for each element of List") {
        import zio.prelude.Covariant
        import zio.prelude.Id

        Covariant[List].map(isOdd)(List(2, 3, 4)) mustBe List(false, true, false)
        Covariant[Option].map(isOdd)(Option(42)) mustBe Option(false)

        val myId: Id[Int] = Id(42)
        Covariant[Id].map(isOdd)(myId) mustBe false
      }
    }

    describe("derived methods") {
      it("can be called directly when Functor syntax is imported") {
        import zio.prelude._

        List(2, 3, 4).unit mustBe List((), (), ())
        List(2, 3, 4).as("foo") mustBe List("foo", "foo", "foo")
        // List(2, 3, 4).fproduct(isOdd) mustBe List((2, false), (3, true), (4, false))
        // no fproduct in zio-prelude def fproduct[A, B](fa: F[A])(f: A => B): F[(A, B)] = map(fa)(a => a -> f(a))
      }

      it("for Vector") {
        import zio.prelude._

        Vector(2, 3, 4).unit mustBe Vector((), (), ())
        Vector(2, 3, 4).as("foo") mustBe Vector("foo", "foo", "foo")
        //Vector(2, 3, 4).fproduct(isOdd) mustBe Vector((2, false), (3, true), (4, false))
      }

      it("for Option") {
        import zio.prelude._

        Option(42).unit mustBe Option(())
        Option(42).as("foo") mustBe Option("foo")
        //Option(42).fproduct(isOdd) mustBe Option((42, false))
      }
    }

    describe("compose") {
      it("can chain multiple map") {
//        import zio.prelude.Covariant
//
//        val listOptionCovariant = Covariant[List] compose Covariant[Option]
//        listOptionCovariant.map(List(Some(42), Some(15), None))(isOdd) mustBe List(
//          Some(false),
//          Some(true),
//          None
//        )
      }

      it("can chain multiple map 2") {
        import cats.Functor

        val listOption = Functor[List] compose Functor[Option]
        import listOption.map
        map(List(Some(42), Some(15), None))(isOdd) mustBe List(
          Some(false),
          Some(true),
          None
        )
      }
    }
  }
}
