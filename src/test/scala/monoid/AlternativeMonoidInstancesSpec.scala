package monoid

import org.scalatest.{FunSpec, MustMatchers}

class AlternativeMonoidInstancesSpec
  extends FunSpec
  with MustMatchers {

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
