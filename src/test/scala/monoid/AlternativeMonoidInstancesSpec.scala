package monoid

import cats.Monoid
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class AlternativeMonoidInstancesSpec
  extends AnyFunSpec
  with Matchers {

  /**
    * Create Monoid for tuple from two Monoids
    */
  implicit def createMonoidTuple[L, R](left: Monoid[L], right: Monoid[R]): Monoid[(L, R)] = new Monoid[(L, R)] {
    def empty: (L, R) = (left.empty, right.empty)
    def combine(x: (L, R), y: (L, R)): (L, R) =
      ( left.combine(x._1, y._1), right.combine(x._2, y._2) )
  }

  describe("custom Monoid for multiply Int") {
    it("can use |+| syntax and combineAll method") {
      import cats.Monoid
      import cats.syntax.semigroup._
      import monoid.AlternativeMonoidInstances.multiplyIntMonoid

      Monoid[Int].combineAll(Nil) mustBe 1
      Monoid[Int].combineAll(List(1, 2, 3, 4)) mustBe 24

      2 |+| 3 mustBe 6
    }
  }

  describe("custom Monoid instance for first non empty Option") {
    it("get first non empty option") {
      import cats.Monoid
      import cats.syntax.semigroup._
      import cats.syntax.option._
      import monoid.AlternativeMonoidInstances.optionFirstNonEmpty

      case class Foo(v: Int)

      Monoid[Option[Foo]].combineAll(List(None, Foo(2).some, Foo(3).some)) mustBe Some(Foo(2))
      Foo(2).some |+| Foo(3).some mustBe Foo(2).some
    }
  }
}

object AlternativeMonoidInstances {
  def createMonoid[A](mempty: A, mapply: (A, A) => A): Monoid[A] = new Monoid[A] {
    def empty: A = mempty
    def combine(x: A, y: A) = mapply(x, y)
  }

  implicit val multiplyIntMonoid: Monoid[Int] = createMonoid(1, _ * _)

  implicit def optionFirstNonEmpty[A]: Monoid[Option[A]] = createMonoid(None, _ orElse _)
}
