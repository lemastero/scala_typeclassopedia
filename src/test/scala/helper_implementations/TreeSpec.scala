package helper_implementations

import educational.collections.{Branch, Leaf, Tree}
import examples.Tree
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class TreeSpec
  extends AnyFunSpec
    with Matchers {

  describe("Tree.number1") {

    it("replace vale when there is only one Leave") {
      testNumberTree(Leaf("foo"), Leaf(1))
    }

    it("replace value when there is Branch with 2 leaves") {
      val tree = Branch(Leaf("foo"), Leaf("bar"))
      val expected = Branch(Leaf(1), Leaf(2))
      testNumberTree(tree, expected)
    }

    it("replace values in Leave's by numbers - in depth first order") {
      val tree = Branch(
        Leaf("a"),
        Branch(
          Leaf("b"),
          Leaf("c")))

      val expected = Branch(
        Leaf(1),
        Branch(
          Leaf(2),
          Leaf(3)))

      testNumberTree(tree, expected)
    }

    def testNumberTree(tree: Tree[String], expected: Tree[Int]): Unit = Tree.number1(tree, 1)._1 mustBe expected
  }

  describe("Tree.number") {

    it("replace vale when there is only one Leave") {
      testNumberTree(Leaf("foo"), Leaf(1))
    }

    it("replace value when there is Branch with 2 leaves") {
      val tree = Branch(Leaf("foo"), Leaf("bar"))
      val expected = Branch(Leaf(1), Leaf(2))
      testNumberTree(tree, expected)
    }

    it("replace values in Leave's by numbers - in depth first order") {
      val tree = Branch(
        Leaf("a"),
        Branch(
          Leaf("b"),
          Leaf("c")))

      val expected = Branch(
        Leaf(1),
        Branch(
          Leaf(2),
          Leaf(3)))

      testNumberTree(tree, expected)
    }

    def testNumberTree(tree: Tree[String], expected: Tree[Int]): Unit = Tree.number(tree)(1)._1 mustBe expected
  }

  describe("Tree.zip1") {
    import cats.syntax.option._

    it("combine values from two trees with single Leave") {
      testZip(Leaf("foo"), Leaf(42), Leaf("foo", 42).some)
    }

    it("combine values when there is Branch with 2 leaves") {
      val tree1 = Branch(Leaf(0), Leaf(1))
      val tree2 = Branch(Leaf("foo"), Leaf("bar"))

      val expected = Branch(Leaf((0, "foo")), Leaf((1, "bar"))).some
      testZip(tree1, tree2, expected)
    }

    it("combine values in Leave's by numbers - in depth first order") {
      val tree1 = Branch(
        Leaf(41),
        Branch(
          Leaf(43),
          Leaf(47)))

      val tree2 = Branch(
        Leaf("Euler"),
        Branch(
          Leaf("Stanislaw Ulam"),
          Leaf("Arthur C. Clarke")))

      val expected: Option[Tree[(Int, String)]] = Branch(
        Leaf(41, "Euler"),
        Branch(
          Leaf(43, "Stanislaw Ulam"),
          Leaf(47, "Arthur C. Clarke"))).some

      testZip(tree1, tree2, expected)
    }

    it("return None for trees with different structure") {
      val tree1 = Branch( Leaf(41), Leaf(43) )
      val tree2 = Leaf("Euler")

      val expected: Option[Tree[(Int, String)]] = None

      testZip(tree1, tree2, expected)
    }

    def testZip[T1, T2](tree1: Tree[T1], tree2: Tree[T2], expected: Option[Tree[(T1, T2)]]) =
      Tree.zip1(tree1, tree2) mustBe expected
  }

  describe("Tree.zip") {
    import cats.syntax.option._

    it("combine values from two trees with single Leave") {
      testZip(Leaf("foo"), Leaf(42), Leaf("foo", 42).some)
    }

    it("combine values when there is Branch with 2 leaves") {
      val tree1 = Branch(Leaf(0), Leaf(1))
      val tree2 = Branch(Leaf("foo"), Leaf("bar"))

      val expected = Branch(Leaf((0, "foo")), Leaf((1, "bar"))).some
      testZip(tree1, tree2, expected)
    }

    it("combine values in Leave's by numbers - in depth first order") {
      val tree1 = Branch(
        Leaf(41),
        Branch(
          Leaf(43),
          Leaf(47)))

      val tree2 = Branch(
        Leaf("Euler"),
        Branch(
          Leaf("Stanislaw Ulam"),
          Leaf("Arthur C. Clarke")))

      val expected: Option[Tree[(Int, String)]] = Branch(
        Leaf(41, "Euler"),
        Branch(
          Leaf(43, "Stanislaw Ulam"),
          Leaf(47, "Arthur C. Clarke"))).some

      testZip(tree1, tree2, expected)
    }

    it("return None for trees with different structure") {
      val tree1 = Branch( Leaf(41), Leaf(43) )
      val tree2 = Leaf("Euler")

      val expected: Option[Tree[(Int, String)]] = None

      testZip(tree1, tree2, expected)
    }

    def testZip[T1, T2](tree1: Tree[T1], tree2: Tree[T2], expected: Option[Tree[(T1, T2)]]) =
      Tree.zip(tree1, tree2) mustBe expected
  }
}
