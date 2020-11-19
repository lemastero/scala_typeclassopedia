package semigroup

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers
import zio.prelude._
import zio.prelude.newtypes.{Sum, _}

class SemigroupExamplesSpec extends AnyFunSpec with Matchers {

  describe("Semigroup methods") {
    it("combine can add BigDecimals") {
      Sum(1000L).combine(Sum(42L)) mustBe Sum(1042L)
    }

    it("join maps containint Ints") {
      val map1 = Map("hello" -> Sum(8), "world" -> Sum(1))
      val map2 = Map("hello" -> Sum(2), "cats" -> Sum(3))
      val merged = Map("hello" -> Sum(8 + 2), "world" -> Sum(1), "cats" -> Sum(3))

      map1.combine(map2) mustBe merged
    }

    it("join maps containing List of Ints") {
      val map1 = Map("even" -> List(2, 4, 6, 8), "odd" -> List(1, 3, 5, 7))
      val map2 = Map("even" -> List(8, 20))
      val merged =
        Map("even" -> List(2, 4, 6, 8, 8, 20), "odd" -> List(1, 3, 5, 7))

      map1 combine map2 mustBe merged
    }

    it("combine can add Ints inside Option") {
      val o1: Option[Sum[Int]] = Some(Sum(1))
      val o2: Option[Sum[Int]] = Some(Sum(2))
      o1 combine o2 mustBe Option(3)
      o1 combine None mustBe o1
    }

    it("merge tuples with doubles and strings") {
      val s1 = (Sum(2.5), "foo")
      val s2 = (Sum(2.6), "bar")
      s1 combine s2 mustBe (5.1, "foobar")
    }

    it("semigroup |+| can be used in List.fold") {
      List(1, 2, 3)
        .map(Sum(_))
        .foldLeft(Sum(0))(_ combine _) mustBe 6
    }

    it("combineN can concatenate strings") {
      import cats.syntax.semigroup._
      "foo".combineN(3) mustBe "foofoofoo"
    }
  }

  describe("|+|") {
    it(
      "adds Ints using |+| using semigroup syntax and group for Int instance"
    ) {
      import cats.syntax.semigroup._

      2 |+| 2 mustBe 4
      1 |+| 41 mustBe 42
    }
  }

  describe("combineN") {
    it("invokes combine given number of times") {
      import cats.syntax.semigroup._
      import cats.instances.option._

      val o1: Option[Int] = Some(1)
      o1.combineN(3) mustBe Option(3)

      Option(3).combineN(4) mustBe Option(12)
    }
  }

  describe("combineAllOption") {
    it("can wrap Semigroup with Option and combine all elements") {
      import cats.implicits._

      (Nil: List[Int]).combineAllOption mustBe None
      List(1, 2, 3).combineAllOption mustBe Some(6)
    }
  }
}
