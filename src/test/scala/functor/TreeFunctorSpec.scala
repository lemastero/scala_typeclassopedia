package functor

import functor.TreeFunctor.treeFunctor.map
import helper_implementations.{Branch, Leaf}
import org.scalatest.{FunSpec, MustMatchers}

class TreeFunctorSpec
  extends FunSpec
    with MustMatchers {

  describe("map over Tree") {
    it("execute function for value stored inside Left") {
      map(Leaf("foo"))(stringLength) mustBe Leaf(3)
      map(Leaf("foo"))(toUpperCase) mustBe Leaf("FOO")
    }

    it("called with Branch execute function for every subtree") {
      map(Branch(Leaf("foo"), Leaf("bar")))(stringLength) mustBe Branch(Leaf(3), Leaf(3))

      map(Branch(Leaf("foo"), Branch(Leaf("quux"), Leaf("foobar"))))(toUpperCase) mustBe
        Branch(Leaf("FOO"), Branch(Leaf("QUUX"), Leaf("FOOBAR")))
    }

    it("called with complicated Tree execute function for every subtree") {
      val tree = Branch(
        Leaf("a"),
        Branch(
          Leaf("b"),
          Leaf("c")))

      val expected = Branch(
        Leaf("A"),
        Branch(
          Leaf("B"),
          Leaf("C")))

      map(tree)(toUpperCase) mustBe expected
    }
  }

  private def stringLength(s: String) = s.length
  private def toUpperCase(s: String) = s.toUpperCase
}
