package semigroup

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class SemigroupExamplesSpec
  extends AnyFunSpec
  with Matchers {

  describe("Semigroup methods") {
    it("combine can add BigDecimals") {
      import cats.Semigroup
      val s: Semigroup[BigDecimal] = cats.instances.bigDecimal.catsKernelStdGroupForBigDecimal
      s.combine(BigDecimal(1000), BigDecimal("42.3")) mustBe BigDecimal(1042.3)
    }

    it("combineN can concatenate strings") {
      import cats.Semigroup
      import cats.instances.string.catsKernelStdMonoidForString

      Semigroup[String].combineN("foo", 3) mustBe "foofoofoo"
    }
  }

  describe("|+|") {
    it("adds Ints using |+| using semigroup syntax and group for Int instance") {
      import cats.syntax.semigroup._
      import cats.implicits.catsKernelStdGroupForInt

      2 |+| 2 mustBe 4
      1 |+| 41 mustBe 42
    }

    it("join maps containint Ints") {
      import cats.syntax.semigroup._
      import cats.instances.map._
      import cats.implicits.catsKernelStdGroupForInt

      val map1 = Map("hello" -> 8, "world" -> 1)
      val map2 = Map("hello" -> 2, "cats"  -> 3)
      val merged = Map("hello" -> (8 + 2), "world" -> 1, "cats" -> 3)

      map1 |+| map2 mustBe merged
    }

    it("join maps containing List of Ints") {
      import cats.syntax.semigroup._
      import cats.implicits.catsKernelStdMonoidForList
      import cats.implicits.catsKernelStdMonoidForMap

      val map1 = Map("even" -> List(2, 4, 6, 8), "odd" -> List(1, 3, 5, 7))
      val map2 = Map("even" -> List(8, 20))
      val merged = Map("even" -> List(2, 4, 6, 8, 8, 20), "odd" -> List(1, 3, 5, 7))

      map1 |+| map2 mustBe merged
    }

    it("combine can add Ints inside Option") {
      import cats.syntax.semigroup._
      import cats.instances.option._
      import cats.implicits.catsKernelStdGroupForInt

      val o1: Option[Int] = Some(1)
      val o2: Option[Int] = Some(2)
      o1 |+| o2 mustBe Option(3)
      o1 |+| None mustBe o1
    }

    it("merge tuples with doubles and strings") {
      import cats.syntax.semigroup._
      import cats.implicits.catsKernelStdMonoidForTuple2
      import cats.implicits.catsKernelStdGroupForDouble
      import cats.implicits.catsKernelStdMonoidForString

      val s1 = (2.5, "foo")
      val s2 = (2.6, "bar")
      s1 |+| s2 mustBe (5.1, "foobar")
    }

    it("semigroup |+| can be used in List.fold") {
      import cats.syntax.semigroup._
      import cats.implicits.catsKernelStdGroupForInt

      List(1, 2, 3).foldLeft(0)(_ |+| _) mustBe 6
    }
  }

  describe("combineN") {
    it("invokes combine given number of times") {
      import cats.Semigroup
      import cats.syntax.semigroup._
      import cats.instances.option._
      import cats.implicits.catsKernelStdGroupForInt

      val o1: Option[Int] = Some(1)
      o1.combineN(3) mustBe Option(3)

      Semigroup[Option[Int]].combineN(Some(3), 4) mustBe Option(12)
    }
  }

  describe("combineAllOption") {
    it("can wrap Semigroup with Option and combine all elements") {
      import cats.Semigroup
      import cats.implicits._

      Semigroup[Int].combineAllOption(Nil) mustBe None
      Semigroup[Int].combineAllOption(List(1, 2, 3)) mustBe Some(6)
    }
  }
}
